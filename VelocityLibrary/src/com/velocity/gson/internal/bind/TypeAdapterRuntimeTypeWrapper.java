
package com.velocity.gson.internal.bind;

import java.io.IOException;
import java.lang.reflect.Type;

import com.velocity.gson.reflect.TypeToken;
import com.velocity.gson.stream.JsonReader;
import com.velocity.gson.stream.JsonWriter;

final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {

  private final MiniGson context;
  private final TypeAdapter<T> delegate;
  private final Type type;

  TypeAdapterRuntimeTypeWrapper(MiniGson context, TypeAdapter<T> delegate, Type type) {
    this.context = context;
    this.delegate = delegate;
    this.type = type;
  }

  @Override
  public T read(JsonReader reader) throws IOException {
    return delegate.read(reader);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void write(JsonWriter writer, T value) throws IOException {
    Type runtimeType = Reflection.getRuntimeTypeIfMoreSpecific(type, value);
    TypeAdapter t = runtimeType != type ?
        context.getAdapter(TypeToken.get(runtimeType)) : delegate;
    t.write(writer, value);
  }
}
