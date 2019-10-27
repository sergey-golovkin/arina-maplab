package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.model.*;
import arina.utils.TypesUtils;
import javax.xml.bind.annotation.XmlValue;
import java.lang.reflect.Field;
import java.util.*;

public abstract class MfdVariableDefinition extends MfdComponentDefinition
{
    public class FieldDef
    {
        final public Class clazz;
        final public boolean isArray;
        final public Class ifValueClazz;
        final public boolean isAttribute;
        final public boolean isObject;

        public FieldDef(Class clazz, boolean isArray, boolean isAttribute)
        {
            this.clazz = clazz;
            this.isArray = isArray;
            this.isAttribute = isAttribute;
            this.isObject = clazz.isAssignableFrom(Map.class) || ! TypesUtils.isSimpleType(clazz);

            Class ifvt = null;
            for(Field f : clazz.getDeclaredFields())
            {
                if(f.getAnnotation(XmlValue.class) != null)
                {
                    ifvt = f.getType();
                    break;
                }
            }
            this.ifValueClazz = ifvt;
        }
    }

    public class ConnectorDef
    {
        public boolean isInput; // input = true; output = false;
        public Object defaultValue;
        public String nodeFunction;
        public String path;
        public String pathsInstanceId; // идентификатор экземпляра (для клонов > 0, для основных = 0)
    }

    protected Map<String, ConnectorDef> connectors = new HashMap<>(); // input/output index, connector definition
    protected Map<String, FieldDef> fields = new HashMap<>(); // structure path, field definition
    protected String usageKind; // stringserialize, stringparse, variable, input, output
    protected String componentInput = null;
    protected String componentOutput = null;

    private ConnectorDef getConnectorDef(String index)
    {
        ConnectorDef cf = connectors.get(index);
        if(cf == null)
        {
            cf = new ConnectorDef();
            connectors.put(index, cf);
        }
        return cf;
    }

    public void load(String globalPrefix, Component component, Map<String, IMapComponentDefinition> connectorsMap) throws Exception
    {

        super.load(globalPrefix, component, connectorsMap);
        if(component.getData() != null)
        {
            if(component.getData().getParameter() != null)
                usageKind = component.getData().getParameter().getUsageKind();

            for(Root r : component.getData().getRoot())
            {
                Entry root;
                int instanceId = 0;

                if("variable".equals(usageKind))
                {
                    root = r.getEntry().get(0).getEntry().get(0);
                }
                else if("input".equals(usageKind) || "output".equals(usageKind))
                {
                    root = r.getEntry().get(0);
                }
                else
                {
                    Entry fileInstance = r.getEntry().get(0);
                    addConnectors(globalPrefix, fileInstance, "/", connectorsMap, "/" + instanceId);
                    if(inputList.size() > 0)
                        componentInput = inputList.get(0);
                    if(outputList.size() > 0)
                        componentOutput = outputList.get(0);
                    root = fileInstance.getEntry().get(0).getEntry().get(0);
                }
                addConnectors(globalPrefix, root, "/", connectorsMap, "/" + instanceId);
                addFieldDef("/", root);

                for (Entry e : root.getEntry())
                {
                    instanceId += (e.getClone() != null ? 1 : 0);
                    processEntries(globalPrefix, e, "/", connectorsMap, "", instanceId);
                }
            }
        }
    }

    private void addConnectors(String globalPrefix, Entry entry, String path, Map<String, IMapComponentDefinition> connectorsMap, String instancePath)
    {
        if(entry.getInpkey() != null)
        {
            String index = globalPrefix + entry.getInpkey();
            ConnectorDef cd = getConnectorDef(index);
            cd.path = getEntryName(path, entry);
            cd.pathsInstanceId = correctInstancePath(instancePath);
            cd.isInput = true;
            inputList.add(index);
            connectorsMap.put(index, this);
            processRules(index, entry);
        }

        if(entry.getOutkey() != null)
        {
            String index = globalPrefix + entry.getOutkey();
            ConnectorDef cd = getConnectorDef(index);
            cd.path = getEntryName(path, entry);
            cd.pathsInstanceId = correctInstancePath(instancePath);
            cd.isInput = false;
            outputList.add(index);
            connectorsMap.put(index, this);
            processRules(index, entry);
        }

        if("input".equals(usageKind) || "output".equals(usageKind))
        {
            String index = globalPrefix + getEntryName(path, entry);
            ConnectorDef cd = getConnectorDef(index);
            cd.path = getEntryName(path, entry);
            cd.pathsInstanceId = correctInstancePath(instancePath);
            cd.isInput = "input".equals(usageKind);
            if( ! cd.isInput)
                outputList.add(index);
//            connectorsMap.put(index, this);
        }
    }

    private void processEntries(String globalPrefix, Entry entry, String path, Map<String, IMapComponentDefinition> connectorsMap, String instancePath, int instanceId) throws Exception
    {
        boolean isField = addFieldDef(addPath(path, entry), entry);
        addConnectors(globalPrefix, entry, path, connectorsMap, instancePath + "/" + instanceId);

        if(isField)
        {
            instancePath += "/" + instanceId;
            instanceId = 0;
        }

        for(Entry e : entry.getEntry())
        {
            instanceId += (e.getClone() != null ? 1 : 0);
            processEntries(globalPrefix, e, addPath(path, entry), connectorsMap, instancePath, instanceId);
        }
    }

    private void processRules(String index, Entry entry)
    {
        // todo applyto & inherit & filter

        ConnectorDef cd = getConnectorDef(index);

        if(entry.getInputnodefunctions() != null)
        {
            for (Nodefunctions.Rule rule : entry.getInputnodefunctions().getRule())
            {
                if(rule.getDefault() != null)
                    cd.defaultValue = rule.getDefault().getValue();
                if(rule.getFunction() != null)
                    cd.nodeFunction = MfdModel.getPrefix("mapforce_nodefunction", rule.getFunction().getName()) + "result";
            }
        }
        if(entry.getOutputnodefunctions() != null)
        {
            for (Nodefunctions.Rule rule : entry.getOutputnodefunctions().getRule())
            {
                if(rule.getDefault() != null)
                    cd.defaultValue = rule.getDefault().getValue();
                if(rule.getFunction() != null)
                    cd.nodeFunction = MfdModel.getPrefix("mapforce_nodefunction", rule.getFunction().getName()) + "result";
            }
        }
    }

    protected String correctInstancePath(String path)
    {
        return path;
    }

    protected abstract String getEntryName(String path, Entry entry);

    protected abstract String addPath(String path, Entry entry) throws Exception;

    protected abstract boolean addFieldDef(String path, Entry entry) throws Exception;
}