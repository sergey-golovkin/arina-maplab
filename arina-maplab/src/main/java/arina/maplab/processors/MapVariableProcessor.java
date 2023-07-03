package arina.maplab.processors;

import arina.maplab.definitions.IMapComponentDefinition;
import arina.maplab.definitions.mapforce.definitions.MfdVariableDefinition;
import arina.maplab.processors.contexts.*;
import arina.maplab.value.IMapValue;
import arina.maplab.value.MapValue;
import arina.utils.*;

import java.util.*;

public abstract class MapVariableProcessor extends MapComponentProcessor
{
    final private Map<Object, String> instances = new IdentityHashMap<>();
    final private Map<String, MfdVariableDefinition.ConnectorDef> connectors;
    final protected Map<String, FieldDef> fields = new HashMap<>();
    final private String usageKind;
    final private String componentInput;
    final private String componentOutput;
    final private Integer copyAll = 2;
    protected Object root = null;
    protected boolean isLoaded = false;
    protected long scn = 0;

    public MapVariableProcessor(IMapComponentDefinition definition, Map<String, MfdVariableDefinition.ConnectorDef> connectors, Map<String, FieldDef> fields, String usageKind, String componentInput, String componentOutput)
    {
        super(definition);

        this.connectors = connectors;
        this.fields.putAll(fields); // в самом процессоре описание может изменяется под конкретные значения, поэтому только копирование. Сделано из за абстрактных типов.
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
        for (String index : definition.getOutputList())
            resultValues.put(connectors.get(index).path, getValue(index, context));
        return resultValues;
    }

    @Override
    protected IMapValue getValueInternal(String index, IMapContext context) throws Exception
    {
        if ("input".equals(usageKind) && definition.getLink(index) == null)
        {
            return context.getContext(InputValueContext.class).getValue(connectors.get(index).path);
        }

        computeSelf(context);

        if (index != null)
        {
            String path = connectors.get(index).path;
            Object object = root;
            IValueContext valueContext = context.getContext(IValueContext.class);

            if (valueContext != null && valueContext.getValue().getProcessor() == this) {// нам прислали контекст, проверим - наш ли ?
                Object contextObject = valueContext.getValue().getValue();
                String contextPath = valueContext.getValue().getContext();
                if (path.startsWith(contextPath) && !contextPath.equals("/"))
                {
                    if (path.equals(contextPath))
                    {
                        if (context.getContext(IParentValueContext.class) != null)
                        {
                            return processNodeRules(index, new MapValue(this, findParent(contextObject, root, path.substring(1).split("/"), 0), path, scn), context);
                        }
                        else
                        {
                            if (fields.get(path).ifValueClazz != null)
                            {
                                return processNodeRules(index, new MapValue(this, ((Map) contextObject).get("value"), path, scn), context);
                            }
                            else
                            {
                                return processNodeRules(index, new MapValue(this, contextObject, path, scn), context);
                            }
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
                if (index.equals(componentOutput) && "stringserialize".equals(usageKind))
                {
                    return processNodeRules(index, marshal(object), context);
                }
                else
                {
                    return processNodeRules(index, new MapValue(this, root, "/", scn), context);
                }
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
        if (cd.nodeFunction != null)
        {
            IMapProcessorContext mpc = context.getContext(IMapProcessorContext.class);
            Map<String, IMapValue> resultValues = mpc.getProcessors().get(cd.nodeFunction).getOutputs(new InputValueContext(context, "raw_value", value));
            if (resultValues.size() > 0)
            {
                value = resultValues.get("result");
            }
        }

        if (value.isNull() && cd.defaultValue != null)
        {
            value = new MapValue(this, cd.defaultValue); // todo optimization required
        }
        return value;
    }

    private Object getValue(Object object, String[] path, int pathIndex)
    {
        if (object == null)
        {
            return null;
        }

        if (object instanceof List) // обработка массива
        {
            ArrayList<Object> array = new ArrayList<>();
            for (Object item : (List) object)
            {
                array.add(getValue(item, path, pathIndex)); // обработаем по одному
            }
            return (array.size() == 0 ? null : array);
        }
        else if (object instanceof Map) // обработка объекта или тега с атрибутами
        {
            Object value = ((Map) object).get(path[pathIndex]);

            if (value != null && pathIndex < path.length - 1) // это еще не конечный элемент, идем дальше
            {
                if (value instanceof Map) // если это объект обрабатываем его
                {
                    return getValue(value, path, pathIndex + 1);
                }
                if (value instanceof List)
                {
// а если список, то берем первый (нулевой по индексу) объект и обработаем его
//                    return getValue(((List) value).get(0), path, pathIndex + 1);
                    return getValue((List) value, path, pathIndex + 1); // experimental
                }

                throw new IllegalArgumentException(); // тут что то непотребное, такого быть не должно
            }
            else
            {
                return value;
            }
        }

        throw new IllegalArgumentException(); // тут что то непотребное, такого быть не должно
    }

    private Object findParent(Object contextObject, Object object, String[] path, int pathIndex)
    {
        if (object instanceof List)
        {
            for (Object item : (List) object)
            {
                if (findParent(contextObject, item, path, pathIndex) != null)
                {
                    return item;
                }
            }
        }
        else if (object instanceof Map)
        {
            Object value = ((Map) object).get(path[pathIndex]);

            if (value != null && pathIndex < path.length - 1)
            {
                return findParent(contextObject, value, path, pathIndex + 1);
            }
            else {
                if (value instanceof List)
                {
                    return ((List) value).contains(contextObject) ? value : null;
                }
                else
                {
                    return (value == contextObject ? value : null);
                }
            }
        }

        return null;
    }

    @Override
    protected IMapValue computeInputValue(String input, IMapContext context) throws Exception
    {
        IMapValue value = processNodeRules(input, super.computeInputValue(input, context), context);
        stackTraceValues.put(input, value);
        return value;
    }

    private void computeSelf(IMapContext context) throws Exception
    {
        if( ! isLoaded)
        {
            root = new LinkedHashMap<>();
            List<String> inputIndexes = new ArrayList<>(definition.getInputList());
            final Map<String, Map.Entry<String, String>> inputPaths = new HashMap<>();
            connectors.forEach((k, v) -> inputPaths.put(k, new AbstractMap.SimpleEntry<>("", v.path)));

            while(inputIndexes.size() > 0)
            {
                computeSelf(root, inputIndexes, inputPaths, context, "");
            }
            Reflection.resetTypes(root, "/", fields);

            isLoaded = true;
            scn = System.currentTimeMillis();
        }
    }

    private void computeSelf(Object object, List<String> inputIndexes, Map<String, Map.Entry<String, String>> inputPaths, IMapContext context, String currentPath) throws Exception
    {
        String inputIndex = inputIndexes.remove(0);
        String path = inputPaths.remove(inputIndex).getValue();
        IMapValue value = computeInputValue(inputIndex, context);
        if (value.isNull())
        {
            MfdVariableDefinition.ConnectorDef connectorDef = connectors.get(inputIndex);
            removeChildren(inputIndexes, inputPaths, path, connectorDef.pathsInstanceId);
            return;
        }

        if (path.equals("/"))
        {
            computeRoot(inputIndex, value, inputIndexes, inputPaths, context, currentPath);
        }
        else
        {
            MfdVariableDefinition.ConnectorDef connectorDef = connectors.get(inputIndex);

            if (object instanceof List)
            {
                List list = (List) object;
                for (Object item : list)
                {
                    computeMap((Map) object, inputIndexes, inputPaths, context, currentPath, inputIndex, path, value, item, connectorDef);
                }
            }
            else if (object instanceof Map)
            {
                Map mapObject = (Map) object;
                // при обработке массивов
                if (context instanceof ValueContext && ((ValueContext) context).getValue() != null && ((ValueContext) context).getValue().getContext() == null)
                {
                    ValueContext valueContext = (ValueContext) context;
                    computeMap(mapObject, inputIndexes, inputPaths, context, currentPath, inputIndex, path, valueContext.getValue(), valueContext.getValue().getValue(), connectorDef);
                }
                else
                {
                    computeMap(mapObject, inputIndexes, inputPaths, context, currentPath, inputIndex, path, value, value.getValue(), connectorDef);
                }
            }
        }
    }

    private void computeMap(Map object, List<String> inputIndexes, Map<String, Map.Entry<String, String>> inputPaths, IMapContext context, String currentPath, String inputIndex, String path, IMapValue value, Object valueItem, MfdVariableDefinition.ConnectorDef connectorDef) throws Exception
    {
        int segIndex = 0;
        int segCount = path.split("/").length - 1;

        for (String name : path.substring(1).split("/"))
        {
            currentPath += "/" + name;
            FieldDef field = this.fields.get(currentPath);

            if (segIndex == segCount - 1) // конечное значение
            {
                if (field == null)
                {
                    return;
                    //throw new IllegalArgumentException("Definition of \"" + currentPath + "\" not found.");
                }

                if (field.isObject) // конечное значение является объектом
                {
                    if (field.isArray) // массив объектов
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
                    if (field.isArray) // массив простых значений
                    {
                        processValueArray(object, valueItem, name, field);
                    }
                    else // одиночное простое конечное значение
                    {
                        processSingleValue(object, valueItem, name, field);
                    }
                }
            }
            else
            {
                if (field != null)
                {
                    object = processTree(object, connectorDef, segIndex, name, field);
                }
            }
            segIndex++;
        }
    }

    private void processSingleValue(Map object, Object valueItem, String name, FieldDef field) throws Exception
    {
        if (valueItem instanceof List)
        {
            if (((List) valueItem).size() > 0 && ((List) valueItem).get(0) != null)
            {
                object.put(name, TypesUtils.getValue(field.clazz, ((List) valueItem).get(0)));
            }
        }
        else
        {
            if (valueItem != null)
            {
                object.put(name, TypesUtils.getValue(field.clazz, valueItem));
            }
        }
    }

    private void processValueArray(Map object, Object valueItem, String name, FieldDef field) throws Exception
    {
        ArrayList<Object> array = (ArrayList<Object>) object.get(name);
        if (array == null)
            array = new ArrayList<>();

        if (valueItem instanceof List)
        {
            for (Object item : (List) valueItem)
            {
                if (item != null)
                    array.add(TypesUtils.getValue(field.clazz, item));
            }
        }
        else
        {
            if (valueItem != null)
                array.add(TypesUtils.getValue(field.clazz, valueItem));
        }

        if (array.size() > 0)
            object.put(name, array);
    }


    private Map processSingleObject(Map object, List<String> inputIndexes, Map<String, Map.Entry<String, String>> inputPaths, IMapContext context, String currentPath, String inputIndex, IMapValue value, Object valueItem, MfdVariableDefinition.ConnectorDef connectorDef, int segIndex, String name, FieldDef field) throws Exception
    {
        if (copyAll.equals(definition.getLink(inputIndex).getValue())) // copyAll
        {
            if (valueItem != null)
            {
                object.put(name, Reflection.clone(valueItem));
            }
            return object;
        }
        else // just create object template
        {
            Map map = new LinkedHashMap<>();
            object.put(name, map);
            putTagValue(map, field, valueItem);
            iterateChildren(map, inputIndexes, inputPaths, new ValueContext(context, value.create(valueItem)), currentPath, connectorDef.pathsInstanceId);
            return map;
        }
    }

    private void processObjectArray(Map object, List<String> inputIndexes, Map<String, Map.Entry<String, String>> inputPaths, IMapContext context, String currentPath, String inputIndex, IMapValue value, Object valueItem, MfdVariableDefinition.ConnectorDef connectorDef, int segIndex, String name, FieldDef field) throws Exception
    {
        ArrayList<Object> array = (ArrayList<Object>) object.get(name);
        if (array == null)
        {
            array = new ArrayList<>();
            object.put(name, array);
        }

        if (valueItem instanceof List) // обработаем массив
        {
            for (Object item : (List) valueItem)
            {
                array.add(computeArrayItem(inputIndexes, inputPaths, context, currentPath, inputIndex, value, connectorDef, segIndex, field, item));
            }
        }
        else // если в массиве только один элемент, то приходит не массив, а сам элемент
        {
            array.add(computeArrayItem(inputIndexes, inputPaths, context, currentPath, inputIndex, value, connectorDef, segIndex, field, valueItem));
        }
    }

    private Map processTree(Map object, MfdVariableDefinition.ConnectorDef connectorDef, int segIndex, String name, FieldDef field)
    {
        Object o = object.get(name);
        if (field.isArray) // массив объектов
        {
            List list = (List) o;
            if (list == null)
            {
                list = new ArrayList<>();
                object.put(name, list);
            }
            Object m = null; // обработка клонов
            for (Object item : list)
            {
                if (connectorDef.pathsInstanceId.substring(1).split("/")[segIndex].equals(instances.get(item))) // выбираем какой экземпляр заполняем
                {
                    m = item; // нашли нужный экземпляр
                    break;
                }
            }
            if (m == null)
            {
                m = new LinkedHashMap<>(); // не нашли экземпляр, создадим новый
                instances.put(m, connectorDef.pathsInstanceId.substring(1).split("/")[segIndex]);
                list.add(m);
            }
            o = m;
        }
        else // объект
        {
            if (o == null)
            {
                o = new LinkedHashMap<>();
                object.put(name, o);
            }
        }

        return (Map) o;
    }

    private Object computeArrayItem(List<String> inputIndexes, Map<String, Map.Entry<String, String>> inputPaths, IMapContext context, String currentPath, String inputIndex, IMapValue value, MfdVariableDefinition.ConnectorDef connectorDef, int segIndex, FieldDef field, Object valueItem) throws Exception
    {
        if (copyAll.equals(definition.getLink(inputIndex).getValue())) // copyAll
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

    private void computeRoot(String inputIndex, IMapValue value, List<String> inputIndexes, Map<String, Map.Entry<String, String>> inputPaths, IMapContext context, String currentPath) throws Exception
    {
        if (inputIndex.equals(componentInput)) // если есть соединение на уровне объекта, а не корня данных
        {
            if (inputIndex.equals(componentInput) && "stringparse".equals(usageKind) && value.getValue() instanceof String)
                root = unmarshal(value.getValue());
            else
                root = Reflection.clone(value.getValue());
        }
        else // если есть соединение на уровне корня данных
        {
            if (copyAll.equals(definition.getLink(inputIndex).getValue()) || inputIndexes.size() == 0) // если тип связи CopyAll
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
        if (field.ifValueClazz != null) // если это всего лишь тег с атрибутами
        {
            if (value instanceof List)
            {
                putTagValue(object, field, ((List) value).get(0));
                return;
            }
            else if (value instanceof Map)
                value = ((Map) value).get("value");

            if (value != null)
                object.put("value", value);
        }
    }

    private void iterateChildren(Map object, List<String> inputIndexes, Map<String, Map.Entry<String, String>> inputPaths, IMapContext context, String currentPath, String currentInstanceId) throws Exception
    {
        List<String> inputNewIndexes = new ArrayList<>();
        Map<String, Map.Entry<String, String>> inputNewPaths = new HashMap<>();

        for (String index : inputIndexes)
        {
            Map.Entry<String, String> entry = inputPaths.get(index);
            String path = entry.getValue();
            String prefix = entry.getKey();
            if (isIndexFound(currentPath, currentInstanceId, index, path, prefix))
            {
                inputNewIndexes.add(index);
                inputNewPaths.put(index, new AbstractMap.SimpleEntry<>(currentPath, (prefix + path).replaceFirst(currentPath, "")));
            }
        }

        while (inputNewIndexes.size() > 0)
            computeSelf(object, inputNewIndexes, inputNewPaths, context, currentPath);
    }

    private void removeChildren(List<String> inputIndexes, Map<String, Map.Entry<String, String>> inputPaths, String currentPath, String currentInstanceId)
    {
        List<String> inputIndexesRemover = new ArrayList<>();

        for (String index : inputIndexes)
        {
            Map.Entry<String, String> entry = inputPaths.get(index);
            String path = entry.getValue();
            String prefix = entry.getKey();
            if (isIndexFound(currentPath, currentInstanceId, index, path, prefix))
                inputIndexesRemover.add(index);
        }

        inputIndexesRemover.forEach(index ->
        {
            inputIndexes.remove(index);
            inputPaths.remove(index);
        });
    }

    private boolean isIndexFound(String currentPath, String currentInstanceId, String index, String path, String prefix)
    {
        return (prefix + path).startsWith(currentPath + "/") &&
                (connectors.get(index).pathsInstanceId.startsWith(currentInstanceId + "/") || connectors.get(index).pathsInstanceId.equals(currentInstanceId));
    }

    @Override
    public String getName(String index) throws Exception
    {
        MfdVariableDefinition.ConnectorDef cd = connectors.get(index);

        if (cd != null)
            return "field: " + cd.path;
        else
            return "unknown field";
    }
}
