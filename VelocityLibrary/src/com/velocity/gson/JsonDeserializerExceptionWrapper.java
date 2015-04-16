package com.velocity.gson;

import com.velocity.gson.internal.$Gson$Preconditions;

import java.lang.reflect.Type;

/**
 * @author Ranjitk
 */
final class JsonDeserializerExceptionWrapper<T> implements JsonDeserializer<T> {

  private final JsonDeserializer<T> delegate;

  /**
   * Returns a wrapped {@link JsonDeserializer} object that has been decorated with
   * {@link JsonParseException} handling.
   *
   * @param delegate the {@code JsonDeserializer} instance to be wrapped.
   * @throws IllegalArgumentException if {@code delegate} is {@code null}.
   */
  JsonDeserializerExceptionWrapper(JsonDeserializer<T> delegate) {
    this.delegate = $Gson$Preconditions.checkNotNull(delegate);
  }

  public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    try {
      return delegate.deserialize(json, typeOfT, context);
    } catch (JsonParseException e) {
      // just rethrow the exception
      throw e;
    } catch (Exception e) {
      // rethrow as a JsonParseException
      StringBuilder errorMsg = new StringBuilder()
          .append("The JsonDeserializer ")
          .append(delegate)
          .append(" failed to deserialize json object ")
          .append(json)
          .append(" given the type ")
          .append(typeOfT);
      throw new JsonParseException(errorMsg.toString(), e);
    }
  }

  @Override
  public String toString() {
    return delegate.toString();
  }
}