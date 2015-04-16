
package com.velocity.gson.internal.bind;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.velocity.gson.internal.$Gson$Types;
import com.velocity.gson.reflect.TypeToken;
import com.velocity.gson.stream.JsonReader;
import com.velocity.gson.stream.JsonToken;
import com.velocity.gson.stream.JsonWriter;

/**
 * Adapt an array of objects.
 */
public final class ArrayTypeAdapter<E> extends TypeAdapter<Object> {
  public static final Factory FACTORY = new Factory() {
    public <T> TypeAdapter<T> create(MiniGson context, TypeToken<T> typeToken) {
      Type type = typeToken.getType();
      if (!(type instanceof GenericArrayType || type instanceof Class && ((Class<?>) type).isArray())) {
        return null;
      }

      Type componentType = $Gson$Types.getArrayComponentType(type);
      TypeAdapter<?> componentTypeAdapter = context.getAdapter(TypeToken.get(componentType));
      @SuppressWarnings("unchecked") // create() doesn't define a type parameter
      TypeAdapter<T> result = new ArrayTypeAdapter(
          context, componentTypeAdapter, $Gson$Types.getRawType(componentType));
      return result;
    }
  };

  private final Class<E> componentType;
  private final TypeAdapter<E> componentTypeAdapter;

  public ArrayTypeAdapter(MiniGson context, TypeAdapter<E> componentTypeAdapter, Class<E> componentType) {
    this.componentTypeAdapter =
      new TypeAdapterRuntimeTypeWrapper<E>(context, componentTypeAdapter, componentType);
    this.componentType = componentType;
  }

  public Object read(JsonReader reader) throws IOException {
    if (reader.peek() == JsonToken.NULL) {
      reader.nextNull(); // TODO: does this belong here?
      return null;
    }

    List<E> list = new ArrayList<E>();
    reader.beginArray();
    while (reader.hasNext()) {
      E instance = componentTypeAdapter.read(reader);
      list.add(instance);
    }
    reader.endArray();
    Object array = Array.newInstance(componentType, list.size());
    for (int i = 0; i < list.size(); i++) {
      Array.set(array, i, list.get(i));
    }
    return array;
  }

  @Override public void write(JsonWriter writer, Object array) throws IOException {
    if (array == null) {
      writer.nullValue(); // TODO: better policy here?
      return;
    }

    writer.beginArray();
    for (int i = 0, length = Array.getLength(array); i < length; i++) {
      final E value = (E) Array.get(array, i);
      componentTypeAdapter.write(writer, value);
    }
    writer.endArray();
  }
}
