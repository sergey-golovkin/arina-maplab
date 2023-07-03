package arina.maplab.processors.functions.string;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import org.apache.commons.lang.StringUtils;

import javax.swing.text.MaskFormatter;

public class mask_format extends MapLibraryFunctionProcessor
{
    public mask_format(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        IMapValue mask = computeInputParameter(1, context);
        if (value.isNotNull() && mask.isNotNull())
        {
            MaskFormatter mf = new MaskFormatter(mask.getValue(String.class));
            mf.setValueContainsLiteralCharacters(false);
            return new MapValue(this, mf.valueToString(value.getValue(String.class)));
        }
        return MapValue.NULL;
    }
}