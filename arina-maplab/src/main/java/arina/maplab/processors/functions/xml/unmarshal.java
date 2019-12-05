package arina.maplab.processors.functions.xml;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.definitions.MfdVariableDefinition;
import arina.maplab.definitions.mapforce.model.Entry;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.FieldDef;
import arina.utils.Reflection;
import arina.utils.Unmarshall;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class unmarshal extends MapLibraryFunctionProcessor
{
    public unmarshal(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue xmlString = computeInputParameter(0, context);
        IMapValue objectType = computeInputParameter(1, context);

        Object obj = Unmarshall.xml(xmlString.isNotNull() ? xmlString.getValue(String.class) : null,null);

        if(objectType.isNotNull())
        {
            Map<String, FieldDef> fields = Reflection.getFieldDefs((Map<String, Object>) obj, objectType.getValue(String.class));
            Reflection.resetTypes(obj, "/", fields);
        }
        return new MapValue(this, obj);
    }
}
