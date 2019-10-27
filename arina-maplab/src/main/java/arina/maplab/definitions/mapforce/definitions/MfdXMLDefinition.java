package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.model.Component;
import arina.maplab.definitions.mapforce.model.Entry;
import arina.maplab.processors.IMapComponentProcessor;
import arina.maplab.processors.MapXMLProcessor;
import arina.utils.Reflection;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.util.*;

public class MfdXMLDefinition extends MfdVariableDefinition
{
    String namespace = "";
    String xmlRoot = "";
    String rootClass = "";

    // todo  use-generic-elements and show-schema-children

    @Override
    public void load(String globalPrefix, Component component, Map<String, IMapComponentDefinition> connectorsMap) throws Exception
    {
        if(component.getData() != null)
        {
            String ns = "";

            if(component.getData().getParameter() != null && component.getData().getParameter().getRoot() != null)
            {
                for(Entry e : component.getData().getParameter().getRoot().getEntry())
                {
                    xmlRoot = e.getName();
                    rootClass += (rootClass.length() > 0 ? "$" : "") + Reflection.normalizeName(e.getName());
                    if(ns.length() == 0)
                        ns = e.getNs();
                }
            }

            if(component.getData().getDocument() != null && StringUtils.isEmpty(xmlRoot))
            {
                xmlRoot = component.getData().getDocument().getInstanceroot().replaceAll("\\{.*\\}(.*)", "$1");
                String el = "";
                ArrayList<String> parts = new ArrayList();
                for(String part : component.getData().getDocument().getInstanceroot().split("/"))
                {
                    if(part.startsWith("{"))
                        el += part;
                    else
                        el += "/" + part;

                    if(part.contains("}"))
                    {
                        parts.add(el);
                        el = "";
                    }
                }
                for(String part : parts)
                {
                    rootClass += (rootClass.length() > 0 ? "$" : "") + Reflection.normalizeName(part.replaceAll("\\{.*\\}(.*)", "$1"));

                    if(ns.length() == 0)
                        ns = part;
                }
            }
            for(String v : Arrays.asList(ns.replaceAll("http://|https://", "").replaceAll("\\{(.*)\\}.*", "$1").split("/")))
            {
                List<String> t = Arrays.asList(v.split("\\."));
                Collections.reverse(t);
                for(String s : t)
                    namespace += (namespace.length() > 0 ? "." : "") + s;
            }
        }

        super.load(globalPrefix, component, connectorsMap);
    }

    @Override
    protected String getEntryName(String path, Entry entry)
    {
        if("/".equals(path))
        {
            if("FileInstance".equals(entry.getName()) || "document".equals(entry.getName()) || xmlRoot.equals(entry.getName()))
                return "/";
            else
                return path + entry.getName();
        }
        return path + "/" + entry.getName();
    }

    @Override
    protected String addPath(String path, Entry entry)
    {
        return ("/".equals(path) ? "" : path) + "/" + entry.getName();
    }

    protected boolean addFieldDef(String path, Entry entry) throws Exception
    {
        if("/".equals(path))
        {
            fields.put(path, new FieldDef(Reflection.forName((StringUtils.isEmpty(namespace) ? "" : namespace + ".") + Reflection.normalizeName(rootClass)), false, false));
        }
        else
        {
            Class objectClass = fields.get(FilenameUtils.getFullPathNoEndSeparator(path)).clazz;
            Method m = Reflection.getMethod(objectClass, "get" + Reflection.normalizeName(FilenameUtils.getName(path)));
            Class fieldClass = Reflection.forName(Reflection.normalizeType(m.getGenericReturnType().getTypeName()));
            fields.put(path, new FieldDef(fieldClass, m.getReturnType().isAssignableFrom(List.class), "attribute".equals(entry.getType())));
        }
        return true;
    }

    @Override
    public IMapComponentProcessor getProcessor() throws Exception
    {
        return new MapXMLProcessor(this, connectors, fields, usageKind, componentInput, componentOutput);
    }
}

