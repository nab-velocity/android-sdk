package com.velocity.gson;

import com.velocity.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author Ranjitk
 */
@SuppressWarnings("unchecked")
final class MapTypeAdapter extends BaseMapTypeAdapter {

  public JsonElement serialize(Map src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject map = new JsonObject();
    Type childGenericType = null;
    if (typeOfSrc instanceof ParameterizedType) {
      Class<?> rawTypeOfSrc = $Gson$Types.getRawType(typeOfSrc);
      childGenericType = $Gson$Types.getMapKeyAndValueTypes(typeOfSrc, rawTypeOfSrc)[1];
    }

    for (Map.Entry entry : (Set<Map.Entry>) src.entrySet()) {
      Object value = entry.getValue();

      JsonElement valueElement;
      if (value == null) {
        valueElement = JsonNull.INSTANCE;
      } else {
        Type childType = (childGenericType == null)
            ? value.getClass() : childGenericType;
        valueElement = serialize(context, value, childType);
      }
      map.add(String.valueOf(entry.getKey()), valueElement);
    }
    return map;
  }

  public Map deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    // Use ObjectConstructor to create instance instead of hard-coding a specific type.
    // This handles cases where users are using their own subclass of Map.
    Map<Object, Object> map = constructMapType(typeOfT, context);
    Type[] keyAndValueTypes = $Gson$Types.getMapKeyAndValueTypes(typeOfT, $Gson$Types.getRawType(typeOfT));
    for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
      Object key = context.deserialize(new JsonPrimitive(entry.getKey()), keyAndValueTypes[0]);
      Object value = context.deserialize(entry.getValue(), keyAndValueTypes[1]);
      map.put(key, value);
    }
    return map;
  }

  @Override
  public String toString() {
    return MapTypeAdapter.class.getSimpleName();
  }
}