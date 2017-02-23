import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class JsonHelper {
    private static ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public static String toString(Object node){
        try {
            return mapper.writeValueAsString(node);
        } catch (JsonProcessingException ignored) {
        }
        return null;
    }

    public static JsonNode mergeNode(JsonNode node1, JsonNode node2){
        if(node1 == null)
            return node2;

        if(node2 == null)
            return node1;

        if( node1.isArray() && node2.isArray())
            return mergeArray((ArrayNode) node1,(ArrayNode) node2);

        if( node1.isObject() && node2.isObject())
            return mergeObject(node1, node2);

        return node2;
    }

    private static JsonNode mergeObject(JsonNode node1, JsonNode node2){
        Iterator<String> keys = node2.fieldNames();
        while(keys.hasNext()){
            String key =  keys.next();

            JsonNode value1 = null;
            if(!node1.has(key))
                value1 = node1.get(key);

            JsonNode value2 = node2.get(key);
            if(value2 != null){
                if(value1 == null) {
                    ((ObjectNode) node1).set(key, value2);
                }else{
                    if(value1.isObject() && value2.isObject()){
                        ((ObjectNode) node1).set(key, mergeObject(value1, value2));
                    }else if(value1.isArray() && value2.isArray()){
                        ((ObjectNode) node1).set(key, mergeArray(value1, value2));
                    }else{
                        ((ObjectNode) node1).set(key, value2);
                    }
                }
            }
        }
        return node1;
    }

    private static ArrayNode mergeArray(JsonNode node1, JsonNode node2){
        return mergeArray((ArrayNode) node1, (ArrayNode) node2);
    }

    private static ArrayNode mergeArray(ArrayNode node1, ArrayNode node2){
        return mergeArrayByKey(node1, node2, null);
    }

    private static ArrayNode mergeArrayByKey(ArrayNode node1, ArrayNode node2, String key){
        if( !node1.isArray() || ! node2.isArray())
            return node1;

        if(key == null) {
            node1.addAll(node2);
        }else {
            for(JsonNode item2: node2){
                if(item2.has(key)) {
                    for (JsonNode item1 : node1) {
                        if (item1.has(key) && item1.get(key).equals(item2.get(key))){
                            mergeNode(item1.get(key), item2.get(key));
                        }
                    }
                }
            }
        }
        return node1;
    }

    public static JsonNode clean(JsonNode node){
        if(node == null || node.size() == 0)
            return null;

        if(node.isArray())
            return cleanArray((ArrayNode) node);

        if(!node.isObject())
            return node.size() == 0 ? null:node;

        Iterator<String> keys = node.fieldNames();
        if(!keys.hasNext())
            return null;

        ObjectNode result = JsonNodeFactory.instance.objectNode();

        while(keys.hasNext()){
            String key = keys.next();
            JsonNode item = node.get(key);

            if(item.isArray()) {
                ArrayNode value = cleanArray((ArrayNode) item);
                if(value != null)
                    result.set(key,value);
            }else if(item.isObject()){
                JsonNode value = clean(item);
                if(value != null)
                    result.set(key, value);
            }else if(item.isTextual()){
                if(!item.asText().isEmpty())
                    result.set(key, item);
            }else if(!item.isNull()){
                result.set(key, item);
            }
        }

        return result.size() == 0 ? null:result;
    }

    private static ArrayNode cleanArray(ArrayNode node){
        if(node == null || node.size() == 0)
            return null;

        ArrayNode result = JsonNodeFactory.instance.arrayNode();
        for(JsonNode item: node){
            if(item.isArray()) {
                ArrayNode value = cleanArray((ArrayNode) item);
                if(value != null)
                    result.add(value);
            }else if(item.isObject()){
                JsonNode value = clean(item);
                if(value != null)
                    result.add(value);
            }else if(item.isTextual()){
                if(item.size() != 0)
                    result.add(item);
            }else{
                result.add(item);
            }
        }
        return result.size() == 0 ? null:result;
    }

    public static JsonNode stringToNode(String json){
        try {
            return mapper.readTree(json);
        } catch (IOException ignored) {
        }
        return null;
    }

    public static ObjectNode toNode(Object source) {
        try {
            return mapper.convertValue(source, ObjectNode.class);
        } catch (Exception ignored) {
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(Object source) {
        try {
            return mapper.convertValue(source, Map.class);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static <T> T toObject(JsonNode source, Class<T> c){
        try {
            return mapper.treeToValue(source, c);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static <T> T convert(Object source, Class<T> c){
        try {
            return mapper.convertValue(source, c);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static <T> T merge(T obj1, T obj2){
        if(obj1 == null || obj2 == null)
            return obj1;

        try {
            PropertyDescriptor[] properties = Introspector.getBeanInfo(obj2.getClass()).getPropertyDescriptors();
            for(PropertyDescriptor property : properties){
                try {
                    Object val1 = property.getReadMethod().invoke(obj1);
                    Object val2 = property.getReadMethod().invoke(obj2);
                    if(val1 == null && val2 != null){
                        property.getWriteMethod().invoke(obj1, val2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

        return obj1;

        //        JsonNode obj = mergeObject(toNode(obj1), toNode(obj2));
//        return (T) toObject(obj, obj1.getClass());
    }

}
