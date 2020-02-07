package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.processors.contexts.IMapContext;
import arina.maplab.processors.contexts.ValueContext;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import org.apache.commons.lang.builder.CompareToBuilder;
import java.util.*;

public class MapSortProcessor extends MapComponentProcessor
{
    final List<Integer> directions;
    final String language;
    final String country;

    public class SortRow
    {
        final public List<IMapValue> keys = new ArrayList<>();
        Object item;

        public SortRow(Object item)
        {
            this.item = item;
        }
    }

    public MapSortProcessor(IMapComponentDefinition definition, String language, String country, List<Integer> directions)
    {
        super(definition);

        this.directions= directions;
        this.language = language;
        this.country = country;
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        IMapValue value = MapValue.NULL;

        if (index != null)
        {
            value = computeInputParameter(0, context);

            if (value.isNotNull() && value.getValue() instanceof List)
            {
                List<SortRow> rows = new ArrayList<>();

                for (Object item : ((List) value.getValue()))
                {
                    SortRow row = new SortRow(item);
                    rows.add(row);
                    for(int i = 1; i < definition.getInputList().size(); i++)
                    {
                        row.keys.add(computeInputParameter(i, new ValueContext(context, value.create(item))));
                    }
                }

                Collections.sort(rows, (r1, r2) ->
                {
                    int c = 0;
                    for(int i = 0; i < r1.keys.size(); i++)
                    {
                        int dir = this.directions.get(i);
                        Object v1 = r1.keys.get(i).getValue();
                        Object v2 = r2.keys.get(i).getValue();

                        if(v1 == null && v2 == null)
                            return 0;
                        if(v1 == null && v2 != null)
                            return -1 * dir;
                        if(v1 != null && v2 == null)
                            return 1 * dir;

                        c = CompareToBuilder.reflectionCompare(v1, v2);
                        if (c != 0)
                            return c * dir;
                    }
                    return c;
                });

                final List list = new ArrayList<>();
                rows.forEach(v -> list.add(v.item));
                return value.create(list);
            }
        }
        return value;
    }
}