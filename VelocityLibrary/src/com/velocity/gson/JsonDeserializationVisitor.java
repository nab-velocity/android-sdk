package com.velocity.gson;

import com.velocity.gson.internal.$Gson$Preconditions;

import java.lang.reflect.Type;

/**
 * @author Ranjitk
 */
abstract class JsonDeserializationVisitor<T> implements ObjectNavigator.Visitor {

  protected final ObjectNavigator objectNavigator;
  protected final FieldNamingStrategy2 fieldNamingPolicy;
  protected final ObjectConstructor objectConstructor;
  protected final ParameterizedTypeHandlerMap<JsonDeserializer<?>> deserializers;
  protected T target;
  protected final JsonElement json;
  protected final Type targetType;
  protected final JsonDeserializationContext context;
  protected boolean constructed;

  JsonDeserializationVisitor(JsonElement json, Type targetType,
      ObjectNavigator objectNavigator, FieldNamingStrategy2 fieldNamingPolicy,
      ObjectConstructor objectConstructor,
      ParameterizedTypeHandlerMap<JsonDeserializer<?>> deserializers,
      JsonDeserializationContext context) {
    this.targetType = targetType;
    this.objectNavigator = objectNavigator;
    this.fieldNamingPolicy = fieldNamingPolicy;
    this.objectConstructor = objectConstructor;
    this.deserializers = deserializers;
    this.json = $Gson$Preconditions.checkNotNull(json);
    this.context = context;
    this.constructed = false;
  }

  public T getTarget() {
    if (!constructed) {
      target = constructTarget();
      constructed = true;
    }
    return target;
  }

  protected abstract T constructTarget();

  public void start(ObjectTypePair node) {
  }

  public void end(ObjectTypePair node) {
  }

  @SuppressWarnings("unchecked")
  public final boolean visitUsingCustomHandler(ObjectTypePair objTypePair) {
    Pair<JsonDeserializer<?>, ObjectTypePair> pair = objTypePair.getMatchingHandler(deserializers);
    if (pair == null) {
      return false;
    }
    Object value = invokeCustomDeserializer(json, pair);
    target = (T) value;
    constructed = true;
    return true;
  }

  protected Object invokeCustomDeserializer(JsonElement element,
      Pair<JsonDeserializer<?>, ObjectTypePair> pair) {
    if (element == null || element.isJsonNull()) {
      return null;
    }
    Type objType = pair.second.type;
    return (pair.first).deserialize(element, objType, context);
  }

  final Object visitChildAsObject(Type childType, JsonElement jsonChild) {
    JsonDeserializationVisitor<?> childVisitor =
        new JsonObjectDeserializationVisitor<Object>(jsonChild, childType,
            objectNavigator, fieldNamingPolicy, objectConstructor, deserializers, context);
    return visitChild(childType, childVisitor);
  }

  final Object visitChildAsArray(Type childType, JsonArray jsonChild) {
    JsonDeserializationVisitor<?> childVisitor =
        new JsonArrayDeserializationVisitor<Object>(jsonChild.getAsJsonArray(), childType,
            objectNavigator, fieldNamingPolicy, objectConstructor, deserializers, context);
    return visitChild(childType, childVisitor);
  }

  private Object visitChild(Type type, JsonDeserializationVisitor<?> childVisitor) {
    objectNavigator.accept(new ObjectTypePair(null, type, false, false), childVisitor);
    // the underlying object may have changed during the construction phase
    // This happens primarily because of custom deserializers
    return childVisitor.getTarget();
  }
}
