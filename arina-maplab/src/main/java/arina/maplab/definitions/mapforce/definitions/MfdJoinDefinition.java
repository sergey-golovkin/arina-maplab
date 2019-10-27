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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MfdJoinDefinition extends MfdVariableDefinition
{
    IMapComponentProcessor processor = null;

    @Override
    public void load(String globalPrefix, Component component, Map<String, IMapComponentDefinition> connectorsMap) throws Exception
    {
        super.load(globalPrefix, component, connectorsMap);
    }

    @Override
    protected String getEntryName(String path, Entry entry)
    {
//        if("/".equals(path))
//        {
//            if("FileInstance".equals(entry.getName()) || "document".equals(entry.getName()) || xmlRoot.equals(entry.getName()))
//                return "/";
//            else
//                return path + entry.getName();
//        }
        return path + "/" + entry.getName();
    }

    @Override
    protected String addPath(String path, Entry entry)
    {
        return ("/".equals(path) ? "" : path) + "/" + entry.getName();
    }

    protected boolean addFieldDef(String path, Entry entry) throws Exception
    {
//        if("/".equals(path))
//        {
//            fields.put(path, new FieldDef(Reflection.forName((StringUtils.isEmpty(namespace) ? "" : namespace + ".") + Reflection.normalizeName(rootClass)), false, false));
//        }
//        else
//        {
//            Class objectClass = fields.get(FilenameUtils.getFullPathNoEndSeparator(path)).clazz;
//            Method m = Reflection.getMethod(objectClass, "get" + Reflection.normalizeName(FilenameUtils.getName(path)));
//            Class fieldClass = Reflection.forName(Reflection.normalizeType(m.getGenericReturnType().getTypeName()));
//            fields.put(path, new FieldDef(fieldClass, m.getReturnType().isAssignableFrom(List.class), "attribute".equals(entry.getType())));
//        }
        return false;
    }

    @Override
    public IMapComponentProcessor getProcessor() throws Exception
    {
        return processor;
    }
}

