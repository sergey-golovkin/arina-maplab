package arina.maplab.definitions;

import java.util.Collection;
import java.util.List;

public interface IMapModel
{
    Collection<IMapComponentDefinition> getDefinitionsList();
    List<IMapComponentDefinition> getInputsList();
    List<IMapComponentDefinition> getOutputsList();
}
