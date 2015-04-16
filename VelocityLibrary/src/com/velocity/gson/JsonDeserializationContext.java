package com.velocity.gson;

import java.lang.reflect.Type;

/**
 * @author Ranjitk
 */
public class JsonDeserializationContext {
  private final ObjectNavigator objectNavigator;
  private final FieldNamingStrategy2 fieldNamingPolicy;
  private final ParameterizedTypeHandlerMap<JsonDeserializer<?>> deserializers;
  private final MappedObjectConstructor objectConstructor;

  JsonDeserializationContext(ObjectNavigator objectNavigator,
      FieldNamingStrategy2 fieldNamingPolicy,
      ParameterizedTypeHandlerMap<JsonDeserializer<?>> deserializers,
      MappedObjectConstructor objectConstructor) {
    this.objectNavigator = objectNavigator;
    this.fieldNamingPolicy = fieldNamingPolicy;
    this.deserializers = deserializers;
    this.objectConstructor = objectConstructor;
  }

  JsonDeserializationContext() {
    this(null, null, null, null);
  }

  @SuppressWarnings("unchecked")
  public <T> T construct(Type type) {
    Object instance = objectConstructor.construct(type);
    return (T) instance;
  }

  public Object constructArray(Type type, int length) {
    return objectConstructor.constructArray(type, length);
  }


  private <T> T fromJsonArray(Type arrayType, JsonArray jsonArray,
      JsonDeserializationContext context, boolean systemOnly) throws JsonParseException {
    JsonArrayDeserializationVisitor<T> visitor = new JsonArrayDeserializationVisitor<T>(
        jsonArray, arrayType, objectNavigator, fieldNamingPolicy,
        objectConstructor, deserializers, context);
    objectNavigator.accept(new ObjectTypePair(null, arrayType, true, systemOnly), visitor);
    return visitor.getTarget();
  }

  private <T> T fromJsonObject(Type typeOfT, JsonObject jsonObject,
      JsonDeserializationContext context, boolean systemOnly) throws JsonParseException {
    JsonObjectDeserializationVisitor<T> visitor = new JsonObjectDeserializationVisitor<T>(
        jsonObject, typeOfT, objectNavigator, fieldNamingPolicy,
        objectConstructor, deserializers, context);
    objectNavigator.accept(new ObjectTypePair(null, typeOfT, true, systemOnly), visitor);
    return visitor.getTarget();
  }

  @SuppressWarnings("unchecked")
  private <T> T fromJsonPrimitive(Type typeOfT, JsonPrimitive json,
      JsonDeserializationContext context, boolean systemOnly) throws JsonParseException {
    JsonObjectDeserializationVisitor<T> visitor = new JsonObjectDeserializationVisitor<T>(
        json, typeOfT, objectNavigator, fieldNamingPolicy, objectConstructor, deserializers, context);
    objectNavigator.accept(new ObjectTypePair(json.getAsObject(), typeOfT, true, systemOnly), visitor);
    Object target = visitor.getTarget();
    return (T) target;
  }

  /**
   * Invokes default deserialization on the specified object. It should never be invoked on
   * the element received as a parameter of the
   * {@link JsonDeserializer#deserialize(JsonElement, Type, JsonDeserializationContext)} method. Doing
   * so will result in an infinite loop since Gson will in-turn call the custom deserializer again.

   * @param json the parse tree.
   * @param typeOfT type of the expected return value.
   * @param <T> The type of the deserialized object.
   * @return An object of type typeOfT.
   * @throws JsonParseException if the parse tree does not contain expected data.
   */
  @SuppressWarnings("unchecked")
  public <T> T deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
    if (json == null || json.isJsonNull()) {
      return null;
    } else if (json.isJsonArray()) {
      Object array = fromJsonArray(typeOfT, json.getAsJsonArray(), this, false);
      return (T) array;
    } else if (json.isJsonObject()) {
      Object object = fromJsonObject(typeOfT, json.getAsJsonObject(), this, false);
      return (T) object;
    } else if (json.isJsonPrimitive()) {
      Object primitive = fromJsonPrimitive(typeOfT, json.getAsJsonPrimitive(), this, false);
      return (T) primitive;
    } else {
      throw new JsonParseException("Failed parsing JSON source: " + json + " to Json");
    }
  }

  @SuppressWarnings("unchecked")
  public <T> T deserializeDefault(JsonElement json, Type typeOfT) throws JsonParseException {
    if (json == null || json.isJsonNull()) {
      return null;
    } else if (json.isJsonArray()) {
      Object array = fromJsonArray(typeOfT, json.getAsJsonArray(), this, true);
      return (T) array;
    } else if (json.isJsonObject()) {
      Object object = fromJsonObject(typeOfT, json.getAsJsonObject(), this, true);
      return (T) object;
    } else if (json.isJsonPrimitive()) {
      Object primitive = fromJsonPrimitive(typeOfT, json.getAsJsonPrimitive(), this, true);
      return (T) primitive;
    } else {
      throw new JsonParseException("Failed parsing JSON source: " + json + " to Json");
    }
  }
}