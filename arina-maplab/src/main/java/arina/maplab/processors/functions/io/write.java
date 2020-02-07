package arina.maplab.processors.functions.io;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import java.io.File;

public class write extends MapLibraryFunctionProcessor
{
    public write(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue path = computeInputParameter(0, context);
        IMapValue data = computeInputParameter(1, context);
        IMapValue encoding = computeInputParameter(2, context);

        if (path.isNull() || data.isNull())
            return MapValue.FALSE;

        String encodingStr;

        if(encoding.isNull() || StringUtils.isEmpty(encoding.getValue(String.class)))
            encodingStr = "utf8";
        else
            encodingStr = encoding.getValue(String.class);

        FileUtils.writeStringToFile(new File(path.getValue(String.class)), data.getValue(String.class), encodingStr);
        return MapValue.TRUE;
    }
}
