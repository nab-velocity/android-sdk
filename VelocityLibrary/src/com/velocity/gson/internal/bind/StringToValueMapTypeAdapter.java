
package com.velocity.gson.internal.bind;

import com.velocity.gson.internal.$Gson$Types;
import com.velocity.gson.reflect.TypeToken;
import com.velocity.gson.stream.JsonReader;
import com.velocity.gson.stream.JsonToken;
import com.velocity.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Adapt a map whose keys are strings.
 */
public final class StringToValueMapTypeAdapter<V> extends TypeAdapter<Map<String, V>> {
  public static final Factory FACTORY = new Factory() {
    public <T> TypeAdapter<T> create(MiniGson context, TypeToken<T> typeToken) {
      Type type = typeToken.getType();
      if (!(type instanceof ParameterizedType)) {
        return null;
      }

      Class<? super T> rawType = typeToken.getRawType();
      if (!Map.class.isAssignableFrom(rawType)) {
        return null;
      }

      Type[] keyAndValueTypes = $Gson$Types.getMapKeyAndValueTypes(type, rawType);
      if (keyAndValueTypes[0] != String.class) {
        return null;
      }
      TypeAdapter<?> valueAdapter = context.getAdapter(TypeToken.get(keyAndValueTypes[1]));

      Constructor<?> constructor;
      try {
        Class<?> constructorType = (rawType == Map.class) ? LinkedHashMap.class : rawType;
        constructor = constructorType.getConstructor();
      } catch (NoSuchMethodException e) {
        return null;
      }

      @SuppressWarnings("unchecked") // we don't define a type parameter for the key or value types
      TypeAdapter<T> result = new StringToValueMapTypeAdapter(valueAdapter, constructor);
      return result;
    }
  };

  private final TypeAdapter<V> valueTypeAdapter;
  private final Constructor<? extends Map<String, V>> constructor;

  public StringToValueMapTypeAdapter(TypeAdapter<V> valueTypeAdapter,
      Constructor<? extends Map<String, V>> constructor) {
    this.valueTypeAdapter = valueTypeAdapter;
    this.constructor = constructor;
  }

  public Map<String, V> read(JsonReader reader) throws IOException {
    if (reader.peek() == JsonToken.NULL) {
      reader.nextNull(); // TODO: does this belong here?
      return null;
    }

    Map<String, V> map = Reflection.newInstance(constructor);
    reader.beginObject();
    while (reader.hasNext()) {
      String key = reader.nextName();
      V value = valueTypeAdapter.read(reader);
      map.put(key, value);
    }
    reader.endObject();
    return map;
  }

  public void write(JsonWriter writer, Map<String, V> map) throws IOException {
    if (map == null) {
      writer.nullValue(); // TODO: better policy here?
      return;
    }

    writer.beginObject();
    for (Map.Entry<String, V> entry : map.entrySet()) {
      writer.name(entry.getKey());
      valueTypeAdapter.write(writer, entry.getValue());
    }
    writer.endObject();
  }
}
