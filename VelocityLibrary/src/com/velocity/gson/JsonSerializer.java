package com.velocity.gson;

import java.lang.reflect.Type;

/**
 * @author Ranjitk
 *
 */
public interface JsonSerializer<T> {


    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context);
}
