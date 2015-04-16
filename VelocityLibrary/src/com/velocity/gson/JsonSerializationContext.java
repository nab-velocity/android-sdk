package com.velocity.gson;

import java.lang.reflect.Type;

/**
 * @author Joel Leitch
 */
public class JsonSerializationContext {

  private final ObjectNavigator objectNavigator;
  private final FieldNamingStrategy2 fieldNamingPolicy;
  private final ParameterizedTypeHandlerMap<JsonSerializer<?>> serializers;
  private final boolean serializeNulls;
  private final MemoryRefStack ancestors;

  JsonSerializationContext(ObjectNavigator objectNavigator,
      FieldNamingStrategy2 fieldNamingPolicy, boolean serializeNulls,
      ParameterizedTypeHandlerMap<JsonSerializer<?>> serializers) {
    this.objectNavigator = objectNavigator;
    this.fieldNamingPolicy = fieldNamingPolicy;
    this.serializeNulls = serializeNulls;
    this.serializers = serializers;
    this.ancestors = new MemoryRefStack();
  }
  
  JsonSerializationContext() {
    this(null, null, false, null);
  }

  /**
   * Invokes default serialization on the specified object.
   *
   * @param src the object that needs to be serialized.
   * @return a tree of {@link JsonElement}s corresponding to the serialized form of {@code src}.
   */
  public JsonElement serialize(Object src) {
    if (src == null) {
      return JsonNull.INSTANCE;
    }
    return serialize(src, src.getClass(), false, false);
  }

  /**
   * Invokes default serialization on the specified object passing the specific type information.
   * It should never be invoked on the element received as a parameter of the
   * {@link JsonSerializer#serialize(Object, Type, JsonSerializationContext)} method. Doing
   * so will result in an infinite loop since Gson will in-turn call the custom serializer again.
   *
   * @param src the object that needs to be serialized.
   * @param typeOfSrc the actual genericized type of src object.
   * @return a tree of {@link JsonElement}s corresponding to the serialized form of {@code src}.
   */
  public JsonElement serialize(Object src, Type typeOfSrc) {
    return serialize(src, typeOfSrc, true, false);
  }

  public JsonElement serializeDefault(Object src, Type typeOfSrc) {
    return serialize(src, typeOfSrc, true, true);
  }

  JsonElement serialize(Object src, Type typeOfSrc, boolean preserveType, boolean defaultOnly) {
    if (src == null) {
      return JsonNull.INSTANCE;
    }
    JsonSerializationVisitor visitor = new JsonSerializationVisitor(
        objectNavigator, fieldNamingPolicy, serializeNulls, serializers, this, ancestors);
    ObjectTypePair objTypePair = new ObjectTypePair(src, typeOfSrc, preserveType, defaultOnly);
    objectNavigator.accept(objTypePair, visitor);
    return visitor.getJsonElement();
  }
}