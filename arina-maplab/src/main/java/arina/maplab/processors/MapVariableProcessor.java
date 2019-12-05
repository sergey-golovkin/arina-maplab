package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.definitions.MfdVariableDefinition;
import arina.maplab.processors.contexts.*;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.*;

import java.io.IOException;
import java.util.*;

public abstract class MapVariableProcessor extends MapComponentProcessor
{
    final private Map<Object, String> instances = new IdentityHashMap<>();
    final private Map<String, MfdVariableDefinition.ConnectorDef> connectors;
    final protected Map<String, FieldDef> fields;
    final private String usageKind;
    final private String componentInput;
    final private String componentOutput;
    final Integer two = new Integer(2);
    protected Object root = null;
    protected boolean isLoaded = false;
    protected long scn = 0;

    public MapVariableProcessor(IMapComponentDefinition definition, Map<String, MfdVariableDefinition.ConnectorDef> connectors, Map<String, FieldDef> fields, String usageKind, String componentInput, String componentOutput)
    {
        super(definition);

        this.connectors = connectors;
        this.fields = fields;
        this.usageKind = usageKind;
        this.componentInput = componentInput;
        this.componentOutput = componentOutput;
    }

    protected abstract IMapValue marshal(Object value) throws Exception;

    protected abstract Object unmarshal(Object value) throws Exception;

    @Override
    public Map<String, IMapValue> getOutputs(IMapContext context) throws Exception
    {
        Map<String, IMapValue> resultValues = new HashMap<>();
        for(String index : definition.getOutputList())
            resultValues.put(connectors.get(index).path, getValue(index, context));
        return resultValues;
    }

    @Override
    public IMapValue getValue(String index, IMapContext context) throws Exception
    {
        if("input".equals(usageKind) && definition.getLink(index) == null)
            return context.getContext(InputValueContext.class).getValue(connectors.get(index).path);

        computeSelf(context);

        if(index != null)
        {
            String path = connectors.get(index).path;
            Object object = root;
            IValueContext valueContext = context.getContext(IValueContext.class);

            if(valueContext != null && valueContext.getValue().getProcessor() == this) // нам прислали контекст, проверим - наш ли ?
            {
                Object contextObject = valueContext.getValue().getValue();
                String contextPath = valueContext.getValue().getContext();
                if (path.startsWith(contextPath))
                {
                    if(path.equals(contextPath))
                    {
                        if(context.getContext(IParentValueContext.class) != null)
                            return processNodeRules(index, new MapValue(this, findParent(contextObject, root, path.substring(1).split("/"), 0), path, scn), context);
                        else
                        {
                            if (fields.get(path).ifValueClazz != null)
                                return processNodeRules(index, new MapValue(this, ((Map) contextObject).get("value"), path, scn), context);
                            else
                                return processNodeRules(index, new MapValue(this, contextObject, path, scn), context);
                        }
                    }
                    else
                    {
                        path = path.replaceFirst(contextPath, "");
                        object = contextObject;
                    }
                }
            }

            if ("/".equals(path))
            {
                if(index.equals(componentOutput) && "stringserialize".equals(usageKind))
                    return processNodeRules(index, marshal(object), context);
                else
                    return processNodeRules(index, new MapValue(this, root, "/", scn), context);
            }
            else
            {
                return processNodeRules(index, new MapValue(this, getValue(object, path.substring(1).split("/"), 0), connectors.get(index).path, scn), context);
            }
        }

        return MapValue.NULL;
    }

    private IMapValue processNodeRules(String index, IMapValue value, IMapContext context) throws Exception
    {
        MfdVariableDefinition.ConnectorDef cd = connectors.get(index);
        if(cd.nodeFunction != null)
        {
            IMapProcessorContext mpc = context.getContext(IMapProcessorContext.class);
            Map<String, IMapValue> resultValues = mpc.getProcessors().get(cd.nodeFunction).getOutputs(new InputValueContext(context, "raw_value", value));
            if(resultValues.size() > 0)
                value = resultValues.get("result");
        }

        if(value.isNull() && cd.defaultValue != null)
            value = new MapValue(this, cd.defaultValue); // todo optimization required

        return value;
    }

    private Object getValue(Object object, String[] path, int pathIndex)
    {
        if(object instanceof List) // обработка массива
        {
            ArrayList<Object> array = new ArrayList<>();
            for(Object item : (List) object)
            {
                array.add(getValue(item, path, pathIndex)); // обработаем по одному
            }
            return array;
        }
        else if(object instanceof Map) // обработка объекта или тега с атрибутами
        {
            Object value = ((Map) object).get(path[pathIndex]);

            if (value != null && pathIndex < path.length - 1) // это еще не конечный элемент, идем дальше
            {
                if(value instanceof Map) // если это объект образабываем его
                    return getValue(value, path, pathIndex + 1);
                if(value instanceof List) // а если список, то берем первый (нулевой по индексу) объект и обработаем его
                    return getValue(((List)value).get(0), path, pathIndex + 1);

                throw new IllegalArgumentException(); // тут что то непотребное, такого быть не должно
            }
            else
                return value;
        }

        throw new IllegalArgumentException(); // тут что то непотребное, такого быть не должно
    }

    private Object findParent(Object contextObject, Object object, String[] path, int pathIndex)
    {
        if(contextObject instanceof List)
        {
            for(Object item : (List) object)
            {
                if(findParent(contextObject, item, path, pathIndex) != null)
                    return item;
            }
        }
        else if(contextObject instanceof Map)
        {
            Object value = ((Map) object).get(path[pathIndex]);

            if (value != null && pathIndex < path.length - 1)
            {
                return findParent(contextObject, value, path, pathIndex + 1);
            }
            else
            {
                if (value instanceof List)
                {
                    return ((List) value).contains(contextObject) ? value : null;
                }
                else
                    return (value == contextObject ? value : null);
            }
        }

        return null;
    }

    @Override
    protected IMapValue computeInputValue(String input, IMapContext context) throws Exception
    {
        return processNodeRules(input, super.computeInputValue(input, context), context);
    }

    private void computeSelf(IMapContext context) throws Exception
    {
        if( ! isLoaded)
        {
            root = new LinkedHashMap<>();
            List<String> inputIndexes = new ArrayList<>(definition.getInputList());
            final Map<String, String> inputPaths = new HashMap<>();
            connectors.forEach((k, v) -> inputPaths.put(k, v.path));

            while (inputIndexes.size() > 0)
                computeSelf(root, inputIndexes, inputPaths, context, "");

            Reflection.resetTypes(root, "/", fields);

            isLoaded = true;
            scn = System.currentTimeMillis();
        }
    }

    private void computeSelf(Object object, List<String> inputIndexes, Map<String, String> inputPaths, IMapContext context, String currentPath) throws Exception
    {
        String inputIndex = inputIndexes.remove(0);
        String path = inputPaths.remove(inputIndex);
        IMapValue value = computeInputValue(inputIndex, context);
        if(value.isNull())
        {
            MfdVariableDefinition.ConnectorDef connectorDef = connectors.get(inputIndex);
            removeChildren(inputIndexes, inputPaths, path, connectorDef.pathsInstanceId);
            return;
        }

        if(path.equals("/"))
        {
            computeRoot(inputIndex, value, inputIndexes, inputPaths, context, currentPath);
        }
        else
        {
            MfdVariableDefinition.ConnectorDef connectorDef = connectors.get(inputIndex);

            if(object instanceof List)
            {
                List list = (List) object;
                for(Object item : list)
                {
                    computeMap((Map)object, inputIndexes, inputPaths, context, currentPath, inputIndex, path, value, item, connectorDef);
                }
            }
            else if(object instanceof Map)
            {
                computeMap((Map)object, inputIndexes, inputPaths, context, currentPath, inputIndex, path, value, value.getValue(), connectorDef);
            }
        }
    }

    private void computeMap(Map object, List<String> inputIndexes, Map<String, String> inputPaths, IMapContext context, String currentPath, String inputIndex, String path, IMapValue value, Object valueItem, MfdVariableDefinition.ConnectorDef connectorDef) throws Exception
    {
        int segIndex = 0;
        int segCount = path.split("/").length - 1;

        for(String name : path.substring(1).split("/"))
        {
            currentPath += "/" + name;
            FieldDef field = this.fields.get(currentPath);

            if(segIndex == segCount - 1) // конечное значение
            {
                if(field.isObject) // конечное значение является объектом
                {
                    if(field.isArray) // массив объектов
                    {
                        processObjectArray(object, inputIndexes, inputPaths, context, currentPath, inputIndex, value, valueItem, connectorDef, segIndex, name, field);
                    }
                    else // одиночный объект
                    {
                        object = processSingleObject(object, inputIndexes, inputPaths, context, currentPath, inputIndex, value, valueItem, connectorDef, segIndex, name, field);
                    }
                    removeChildren(inputIndexes, inputPaths, currentPath, connectorDef.pathsInstanceId);
                }
                else // простое конечное значение
                {
                    if(field.isArray) // массив простых значений
                    {
                        throw new IllegalArgumentException();
                    }
                    else // одиночное простое конечное значение
                    {
                        object.put(name, TypesUtils.getValue(field.clazz, valueItem instanceof List ? ((List) valueItem).get(0) : valueItem));
                    }
                }
            }
            else
            {
                object = processTree(object, connectorDef, segIndex, name, field);
            }
            segIndex++;
        }
    }

    private Map processSingleObject(Map object, List<String> inputIndexes, Map<String, String> inputPaths, IMapContext context, String currentPath, String inputIndex, IMapValue value, Object valueItem, MfdVariableDefinition.ConnectorDef connectorDef, int segIndex, String name, FieldDef field) throws Exception
    {
        if (two.equals(definition.getLink(inputIndex).getValue())) // copyAll
        {
            object.put(name, Reflection.clone(valueItem));
        }
        else // just create object template
        {
            object = new LinkedHashMap<>();
            object.put(name, object);
            putTagValue(object, field, valueItem);
            iterateChildren(object, inputIndexes, inputPaths, new ValueContext(context, value.create(valueItem)), currentPath, connectorDef.pathsInstanceId);
        }
        return object;
    }

    private void processObjectArray(Map object, List<String> inputIndexes, Map<String, String> inputPaths, IMapContext context, String currentPath, String inputIndex, IMapValue value, Object valueItem, MfdVariableDefinition.ConnectorDef connectorDef, int segIndex, String name, FieldDef field) throws Exception
    {
        ArrayList<Object> array = (ArrayList<Object>) object.get(name);
        if(array == null)
        {
            array = new ArrayList<>();
            object.put(name, array);
        }

        if(valueItem instanceof List) // обработаем массив
        {
            for (Object item : (List) valueItem)
            {
                array.add(computeArrayItem(inputIndexes, inputPaths, context, currentPath, inputIndex, value, connectorDef, segIndex, field, item));
            }
        }
        else  // если в массиве только один элемент, то приходит не массив, а сам элемент
        {
            array.add(computeArrayItem(inputIndexes, inputPaths, context, currentPath, inputIndex, value, connectorDef, segIndex, field, valueItem));
        }
    }

    private Map processTree(Map object, MfdVariableDefinition.ConnectorDef connectorDef, int segIndex, String name, FieldDef field)
    {
        Object o = object.get(name);
        if(field.isArray) // массив объектов
        {
            List list = (List) o;
            if(list == null)
            {
                list = new ArrayList<>();
                object.put(name, list);
            }
            Object m = null; // обработка клонов
            for(Object item : list)
            {
                if(connectorDef.pathsInstanceId.substring(1).split("/")[segIndex].equals(instances.get(item))) // выбираем какой экземпляр заполняем
                {
                    m = item; // нашли нужный экземпляр
                    break;
                }
            }
            if(m == null)
            {
                m = new LinkedHashMap<>(); // не нашли экземпляр, создадим новый
                instances.put(m, connectorDef.pathsInstanceId.substring(1).split("/")[segIndex]);
                list.add(m);
            }
            o = m;
        }
        else // объект
        {
            if(o == null)
            {
                o = new LinkedHashMap<>();
                object.put(name, o);
            }
        }

        return (Map) o;
    }

    private Object computeArrayItem(List<String> inputIndexes, Map<String, String> inputPaths, IMapContext context, String currentPath, String inputIndex, IMapValue value, MfdVariableDefinition.ConnectorDef connectorDef, int segIndex, FieldDef field, Object valueItem) throws Exception
    {
        if (two.equals(definition.getLink(inputIndex).getValue())) // copyAll
        {
            Object object = Reflection.clone(valueItem);
            instances.put(object, connectorDef.pathsInstanceId.substring(1).split("/")[segIndex]);
            return object;
        }
        else // создадим объект или скопируем только тег, если это тег с атрибутами
        {
            Map object = new LinkedHashMap<>();
            putTagValue(object, field, valueItem);
            instances.put(object, connectorDef.pathsInstanceId.substring(1).split("/")[segIndex]);
            iterateChildren(object, inputIndexes, inputPaths, new ValueContext(context, value.create(valueItem)), currentPath, connectorDef.pathsInstanceId);
            return object;
        }
    }

    private void computeRoot(String inputIndex, IMapValue value, List<String> inputIndexes, Map<String, String> inputPaths, IMapContext context, String currentPath) throws Exception
    {
        if(inputIndex.equals(componentInput)) // если есть соединение на уровне объекта, а не корня данных
        {
            if (inputIndex.equals(componentInput) && "stringparse".equals(usageKind) && value.getValue() instanceof String)
                root = unmarshal(value.getValue());
            else
                root = Reflection.clone(value.getValue());
        }
        else // если есть соединение на уровне корня данных
        {
            if(two.equals(definition.getLink(inputIndex).getValue())) // если тап связи CopyAll
                root = Reflection.clone(value.getValue());
            else
            {
                root = new LinkedHashMap<>();
                MfdVariableDefinition.ConnectorDef connectorDef = connectors.get(inputIndex);
                putTagValue((Map) root, fields.get(connectorDef.path), value.getValue());
                iterateChildren((Map) root, inputIndexes, inputPaths, new ValueContext(context, value), currentPath, connectorDef.pathsInstanceId);
                removeChildren(inputIndexes, inputPaths, currentPath, connectorDef.pathsInstanceId);
            }
        }
    }

    private void putTagValue(Map object, FieldDef field, Object value)
    {
        if(field.ifValueClazz != null) // если это всего лишь тег с атрибутами
        {
            if(value instanceof List)
            {
                putTagValue(object, field, ((List) value).get(0));
                return;
            }
            else if(value instanceof Map)
                value = ((Map) value).get("value");

            if(value != null)
                object.put("value", value);
        }
    }

    private void iterateChildren(Map object, List<String> inputIndexes, Map<String, String> inputPaths, IMapContext context, String currentPath, String currentInstanceId) throws Exception
    {
        List<String> inputNewIndexes = new ArrayList<>();
        Map<String, String> inputNewPaths = new HashMap<>();

        for (String index : inputIndexes)
        {
            String path = inputPaths.get(index);
            if (path.startsWith(currentPath) && connectors.get(index).pathsInstanceId.startsWith(currentInstanceId))
            {
                inputNewIndexes.add(index);
                inputNewPaths.put(index, path.replaceFirst(currentPath, ""));
            }
        }

        while (inputNewIndexes.size() > 0)
            computeSelf(object, inputNewIndexes, inputNewPaths, context, currentPath);
    }

    private void removeChildren(List<String> inputIndexes, Map<String, String> inputPaths, String currentPath, String currentInstanceId)
    {
        List<String> inputIndexesRemover = new ArrayList<>();

        for (String index : inputIndexes)
        {
            String path = inputPaths.get(index);
            if (path.startsWith(currentPath) && connectors.get(index).pathsInstanceId.startsWith(currentInstanceId))
            {
                inputIndexesRemover.add(index);
            }
        }

        inputIndexesRemover.forEach(index ->
        {
            inputIndexes.remove(index);
            inputPaths.remove(index);
        });
    }
}