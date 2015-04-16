package com.velocity.gson;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Captures all the common/shared logic between the old, ({@link MapTypeAdapter}, and
 * the new, {@link MapAsArrayTypeAdapter}, map type adapters.
 *
 * @author Ranjitk
 */
abstract class BaseMapTypeAdapter
    implements JsonSerializer<Map<?, ?>>, JsonDeserializer<Map<?, ?>> {

  protected static final JsonElement serialize(JsonSerializationContext context,
      Object src, Type srcType) {
    return context.serialize(src, srcType, false, false);
  }

  protected static final Map<Object, Object> constructMapType(
      Type mapType, JsonDeserializationContext context) {
    return context.construct(mapType);
  }
}