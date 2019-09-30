package arina.maplab.processors.functions.number;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.functions.MapLibraryFunctionProcessor;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/*  FORMATS:
0	A digit - always displayed, even if number has less digits (then 0 is displayed)
#	A digit, leading zeroes are omitted.
.	Marks decimal separator
,	Marks grouping separator (e.g. thousand separator)
E	Marks separation of mantissa and exponent for exponential formats.
;	Separates formats
-	Marks the negative number prefix
%	Multiplies by 100 and shows number as percentage
?	Multiplies by 1000 and shows number as per mille
¤	Currency sign - replaced by the currency sign for the Locale. Also makes formatting use the monetary decimal separator instead of normal decimal separator. ¤¤ makes formatting use international monetary symbols.
X	Marks a character to be used in number prefix or suffix
'	Marks a quote around special characters in prefix or suffix of formatted number.
 */

public class format extends MapLibraryFunctionProcessor
{
    public format(IMapComponentDefinition definition, Integer growable)
    {
        super(definition, growable);
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        IMapValue value = computeInputParameter(0, context);
        IMapValue format = computeInputParameter(1, context);
        IMapValue decimalSeparator = computeInputParameter(2, context);
        IMapValue groupingSeparator = computeInputParameter(3, context);

        if(value.isNotNull() && format.isNotNull())
        {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            if(decimalSeparator.isNotNull() && decimalSeparator.getValue(String.class).length() > 0)
                symbols.setDecimalSeparator(decimalSeparator.getValue(String.class).charAt(0));

            if(groupingSeparator.isNotNull() && groupingSeparator.getValue(String.class).length() > 0)
                symbols.setGroupingSeparator(groupingSeparator.getValue(String.class).charAt(0));

            DecimalFormat decimalFormat = new DecimalFormat(format.getValue(String.class), symbols);
            return new MapValue(this, decimalFormat.format(value.getValue(BigDecimal.class)));
        }
        return MapValue.NULL;
    }
}
