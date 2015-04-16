package com.velocity.gson;

import java.lang.reflect.Type;

/**
 * @author Ranjitk
 *
 */
public interface JsonDeserializer<T> {

  public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException;
}
