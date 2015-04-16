package com.velocity.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
final class MapAsArrayTypeAdapter
    extends BaseMapTypeAdapter
    implements JsonSerializer<Map<?, ?>>, JsonDeserializer<Map<?, ?>> {

  public Map<?, ?> deserialize(JsonElement json, Type typeOfT,
      JsonDeserializationContext context) throws JsonParseException {
    Map<Object, Object> result = constructMapType(typeOfT, context);
    Type[] keyAndValueType = typeToTypeArguments(typeOfT);
    if (json.isJsonArray()) {
      JsonArray array = json.getAsJsonArray();
      for (int i = 0; i < array.size(); i++) {
        JsonArray entryArray = array.get(i).getAsJsonArray();
        Object k = context.deserialize(entryArray.get(0), keyAndValueType[0]);
        Object v = context.deserialize(entryArray.get(1), keyAndValueType[1]);
        result.put(k, v);
      }
      checkSize(array, array.size(), result, result.size());
    } else {
      JsonObject object = json.getAsJsonObject();
      for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
        Object k = context.deserialize(new JsonPrimitive(entry.getKey()), keyAndValueType[0]);
        Object v = context.deserialize(entry.getValue(), keyAndValueType[1]);
        result.put(k, v);
      }
      checkSize(object, object.entrySet().size(), result, result.size());
    }
    return result;
  }

  public JsonElement serialize(Map<?, ?> src, Type typeOfSrc, JsonSerializationContext context) {
    Type[] keyAndValueType = typeToTypeArguments(typeOfSrc);
    boolean serializeAsArray = false;
    List<JsonElement> keysAndValues = new ArrayList<JsonElement>();
    for (Map.Entry<?, ?> entry : src.entrySet()) {
      JsonElement key = serialize(context, entry.getKey(), keyAndValueType[0]);
      serializeAsArray |= key.isJsonObject() || key.isJsonArray();
      keysAndValues.add(key);
      keysAndValues.add(serialize(context, entry.getValue(), keyAndValueType[1]));
    }

    if (serializeAsArray) {
      JsonArray result = new JsonArray();
      for (int i = 0; i < keysAndValues.size(); i+=2) {
        JsonArray entryArray = new JsonArray();
        entryArray.add(keysAndValues.get(i));
        entryArray.add(keysAndValues.get(i + 1));
        result.add(entryArray);
      }
      return result;
    } else {
      JsonObject result = new JsonObject();
      for (int i = 0; i < keysAndValues.size(); i+=2) {
        result.add(keysAndValues.get(i).getAsString(), keysAndValues.get(i + 1));
      }
      checkSize(src, src.size(), result, result.entrySet().size());
      return result;
    }
  }

  private Type[] typeToTypeArguments(Type typeOfT) {
    if (typeOfT instanceof ParameterizedType) {
      Type[] typeArguments = ((ParameterizedType) typeOfT).getActualTypeArguments();
      if (typeArguments.length != 2) {
        throw new IllegalArgumentException("MapAsArrayTypeAdapter cannot handle " + typeOfT);
      }
      return typeArguments;
    }
    return new Type[] { Object.class, Object.class };
  }

  private void checkSize(Object input, int inputSize, Object output, int outputSize) {
    if (inputSize != outputSize) {
      throw new JsonSyntaxException("Input size " + inputSize + " != output size " + outputSize
          + " for input " + input + " and output " + output);
    }
  }
}
