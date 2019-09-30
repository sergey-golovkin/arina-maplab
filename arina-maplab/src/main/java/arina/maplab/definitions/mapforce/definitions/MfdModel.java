package arina.maplab.definitions.mapforce.definitions;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.MapModel;
import arina.maplab.definitions.mapforce.loaders.IMfdComponentDefinitionLoader;
import arina.maplab.definitions.mapforce.model.*;
import org.apache.commons.lang.StringUtils;
import java.util.*;

public class MfdModel extends MapModel
{
    Map<String, IMapComponentDefinition> definitionsMap = new HashMap<>(); // ид. компоненты, описание компоненты
    List<IMapComponentDefinition> outputsList = new ArrayList<>(); // список компонент, результатов
    List<IMapComponentDefinition> inputsList = new ArrayList<>(); // список компонент, входных параметров

    public static boolean isEmptyIndex(String index)
    {
        return index.startsWith("_$_EMPTY_VALUE_$_");
    }

    public static String setEmptyIndex(String index)
    {
        return "_$_EMPTY_VALUE_$_" + index;
    }

    public static String getPrefix(String library, String name)
    {
        return library + "|" + name + "|";
    }

    public MfdModel(List<Mapping> mappings) throws IllegalArgumentException
    {
        try
        {
            Map<Integer, IMfdComponentDefinitionLoader> loaders = new HashMap<>(); // kind, loader
            Map<String, IMapComponentDefinition> connectorsMap = new HashMap<>(); // индекс точки связывания, описание компоненты
            Map<String, Map.Entry<String, Integer>> linksMap = new HashMap<>(); // индекс входящей точки связывания, индекс исходящей точки связывания, тип связи

            for(Mapping mapping : mappings) // загрузка описаний компонент и точек связывания
            {
                loadMapping(loaders, mapping, connectorsMap, linksMap);
            }

            for (Map.Entry<String, Map.Entry<String, Integer>> entry : linksMap.entrySet()) // линковка всех внутрениих точек связывания
            {
                link(entry.getKey(), entry.getValue().getKey(), entry.getValue().getValue(), connectorsMap);
            }
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("invalid mfd structure", e);
        }
    }

    private void loadMapping(Map<Integer, IMfdComponentDefinitionLoader> loaders, Mapping mapping, Map<String, IMapComponentDefinition> connectorsMap, Map<String, Map.Entry<String, Integer>> linksMap) throws Exception
    {
        for (Mapping.Component mc : mapping.getComponent())
        {
            boolean isRoot = false;
            if (StringUtils.isEmpty(mc.getLibrary()))
            {
                mc.setLibrary(UUID.randomUUID().toString().toUpperCase());
                isRoot = true;
            }

            String globalPrefix = getPrefix(mc.getLibrary(), mc.getName());

            // загрузка описания компонент
            for (Component c : mc.getStructure().getChildren().getComponent())
            {
                loadComponent(loaders, isRoot, globalPrefix, c, connectorsMap);
            }

            // загрузка описания точек связывания, формат 1
            if(mc.getStructure() != null && mc.getStructure().getGraph() != null && mc.getStructure().getGraph().getVertices() != null && mc.getStructure().getGraph().getVertices().getVertex() != null)
            {
                for (Vertex v : mc.getStructure().getGraph().getVertices().getVertex())
                {
                    loadVertex(linksMap, mc, v);
                }
            }

            // загрузка описания точек связывания, формат 2
            if(mc.getConnections() != null)
            {
                for(Connections.Edge e : mc.getConnections().getEdge())
                {
                    loadConnection(linksMap, mc, e);
                }
            }
        }
    }

    private void loadComponent(Map<Integer, IMfdComponentDefinitionLoader> loaders, boolean isRoot, String globalPrefix, Component component, Map<String, IMapComponentDefinition> connectorsMap) throws Exception
    {
        IMfdComponentDefinitionLoader loader = loaders.get(component.getKind());
        if(loader == null)
        {
            Class clazz = Class.forName("arina.maplab.definitions.mapforce.loaders.MfdLoaderKind" + component.getKind());
            loader = (IMfdComponentDefinitionLoader) clazz.newInstance();
            loaders.put(component.getKind(), loader);
        }

        IMfdComponentDefinition def = loader.newInstance(component);
        def.load(globalPrefix, component, connectorsMap);
        definitionsMap.put(def.getId(), def);
        if(isRoot)
        {
            if(def.isInput())
                inputsList.add(def);
            if(def.isOutput())
                outputsList.add(def);
        }
    }


    private void loadVertex(Map<String, Map.Entry<String, Integer>> linksMap, Mapping.Component mc, Vertex vertex)
    {
        String outputIndex = getPrefix(mc.getLibrary(), mc.getName()) + vertex.getVertexkey();
        for (Edge e : vertex.getEdges().getEdge())
        {
            String inputIndex = getPrefix(mc.getLibrary(), mc.getName()) + e.getVertexkey();
            linksMap.put(inputIndex, new AbstractMap.SimpleEntry<>(outputIndex, getLinkType(mc, e)));
        }
    }

    private Integer getLinkType(Mapping.Component mc, Edge edge)
    {
        if(mc.getStructure().getGraph() != null && mc.getStructure().getGraph().getEdges() != null && edge.getEdgekey() != null)
        {
            for (Edge e : mc.getStructure().getGraph().getEdges().getEdge())
            {
                if (e.getData() != null && e.getData().getDataconnection() != null && edge.getEdgekey().equals(e.getEdgekey()))
                    return e.getData().getDataconnection().getType();
            }
        }
        return null;
    }

    private void loadConnection(Map<String, Map.Entry<String, Integer>> linksMap, Mapping.Component mc, Connections.Edge edge)
    {
        String outputIndex = getPrefix(mc.getLibrary(), mc.getName()) + edge.getFrom();
        String inputIndex = getPrefix(mc.getLibrary(), mc.getName()) + edge.getTo();
        linksMap.put(inputIndex, new AbstractMap.SimpleEntry<>(outputIndex, null));
    }

    private void link(String inputIndex, String outputIndex, Integer linkType, Map<String, IMapComponentDefinition> connectorsMap) throws Exception
    {
        IMapComponentDefinition output = connectorsMap.get(outputIndex);
        if(output == null)
            throw new IllegalArgumentException("Definition of connection point " + outputIndex + " not found!");

        IMapComponentDefinition input = connectorsMap.get(inputIndex);
        if(input == null)
            throw new IllegalArgumentException("Definition of connection point " + inputIndex + " not found!");

        inputsList.remove(input);
        outputsList.remove(output);
        input.link(outputIndex, inputIndex, linkType);
    }

    @Override
    public Collection<IMapComponentDefinition> getDefinitionsList()
    {
        return definitionsMap.values();
    }

    @Override
    public List<IMapComponentDefinition> getInputsList()
    {
        return inputsList;
    }

    @Override
    public List<IMapComponentDefinition> getOutputsList()
    {
        return outputsList;
    }
}
