package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.model.Component;
import arina.maplab.processors.IMapComponentProcessor;
import arina.maplab.processors.MapInputProcessor;
import arina.utils.TypesUtils;

import java.util.Map;

public class MfdInputDefinition extends MfdSTDefinition
{
    IMapComponentProcessor processor;

    @Override
    public void load(String globalPrefix, Component component, Map<String, IMapComponentDefinition> connectorsMap) throws Exception
    {
        super.load(globalPrefix, component, connectorsMap);

        connectorsMap.put(id + "|" + component.getData().getParameter().getName(), this);
        connectorsMap.put(globalPrefix + component.getData().getParameter().getName(), this);

        String datatype;
        Object previewvalue = null;

        datatype = component.getData().getInput().getDatatype().value();
        if("1".equals(component.getData().getInput().getUsepreviewvalue()))
            previewvalue = TypesUtils.getValue(datatype, component.getData().getInput().getPreviewvalue());

        processor = new MapInputProcessor(this, datatype, previewvalue, component.getData().getParameter().getUsageKind());
    }

    @Override
    public IMapComponentProcessor getProcessor() throws Exception
    {
        return processor;
    }

    @Override
    public boolean isInput()
    {
        return true;
    }
}