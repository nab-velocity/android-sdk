
package com.velocity.gson;

import java.io.IOException;
import java.lang.reflect.Type;

import com.velocity.gson.internal.Streams;
import com.velocity.gson.internal.bind.MiniGson;
import com.velocity.gson.internal.bind.TypeAdapter;
import com.velocity.gson.reflect.TypeToken;
import com.velocity.gson.stream.JsonReader;
import com.velocity.gson.stream.JsonWriter;

final class GsonToMiniGsonTypeAdapter implements TypeAdapter.Factory {
  private final ParameterizedTypeHandlerMap<JsonSerializer<?>> serializers;
  private final ParameterizedTypeHandlerMap<JsonDeserializer<?>> deserializers;
  private final boolean serializeNulls;

  GsonToMiniGsonTypeAdapter(ParameterizedTypeHandlerMap<JsonSerializer<?>> serializers,
      ParameterizedTypeHandlerMap<JsonDeserializer<?>> deserializers, boolean serializeNulls) {
    this.serializers = serializers;
    this.deserializers = deserializers;
    this.serializeNulls = serializeNulls;
  }

  public <T> TypeAdapter<T> create(final MiniGson miniGson, TypeToken<T> type) {
    final Type typeOfT = type.getType();
    final JsonSerializer serializer = serializers.getHandlerFor(typeOfT, false);
    final JsonDeserializer deserializer = deserializers.getHandlerFor(typeOfT, false);
    if (serializer == null && deserializer == null) {
      return null;
    }
    return new TypeAdapter() {
      @Override
      public Object read(JsonReader reader) throws IOException {
        if (deserializer == null) {
          // TODO: handle if deserializer is null
          throw new UnsupportedOperationException();
        }
        return deserializer.deserialize(Streams.parse(reader), typeOfT, createDeserializationContext(miniGson));
      }
      @Override
      public void write(JsonWriter writer, Object value) throws IOException {
        if (serializer == null) {
          // TODO: handle if serializer is null
          throw new UnsupportedOperationException();
        }
        if (value == null) {
          writer.nullValue();
          return;
        }
        JsonElement element = serializer.serialize(value, typeOfT, createSerializationContext(miniGson));
        Streams.write(element, serializeNulls, writer);
      }
    };
  }
  
  public JsonSerializationContext createSerializationContext(final MiniGson miniGson) {
    return new JsonSerializationContext() {
      @Override
      JsonElement serialize(Object src, Type typeOfSrc, boolean preserveType, boolean defaultOnly) {
        TypeToken typeToken = TypeToken.get(typeOfSrc);
        return miniGson.getAdapter(typeToken).toJsonElement(src);
      }
    };
  }
  public JsonDeserializationContext createDeserializationContext(final MiniGson miniGson) {
    return new JsonDeserializationContext() {
      @Override
      public <T> T deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
        TypeToken typeToken = TypeToken.get(typeOfT);
        return (T) miniGson.getAdapter(typeToken).fromJsonElement(json);
      }
    };
  }
}
