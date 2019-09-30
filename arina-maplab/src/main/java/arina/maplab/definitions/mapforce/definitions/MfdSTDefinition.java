package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.model.Component;
import arina.maplab.definitions.mapforce.model.Datapoint;
import java.util.Map;

abstract public class MfdSTDefinition extends MfdComponentDefinition
{
    @Override
    public void load(String globalPrefix, Component component, Map<String, IMapComponentDefinition> connectorsMap) throws Exception
    {
        super.load(globalPrefix, component, connectorsMap);

        if(component.getSources() != null)
        {
            for (Datapoint dp : component.getSources().getDatapoint())
            {
                String index = dp.getKey();
                if(index == null)
                    index = MfdModel.setEmptyIndex(globalPrefix + connectorsMap.size());
                else
                    index = globalPrefix + index;

                inputList.add(index);
                connectorsMap.put(index, this);
            }
        }

        if(component.getTargets() != null)
        {
            for (Datapoint dp : component.getTargets().getDatapoint())
            {
                String index = dp.getKey();
                if(index == null)
                    index = MfdModel.setEmptyIndex(globalPrefix + connectorsMap.size());
                else
                    index = globalPrefix + index;
                outputList.add(index);
                connectorsMap.put(index, this);
            }
        }
    }
}