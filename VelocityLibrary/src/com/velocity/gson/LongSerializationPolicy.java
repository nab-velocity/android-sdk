package com.velocity.gson;

/**
 * @author Ranjitk
 */
public enum LongSerializationPolicy {
 
  DEFAULT(new DefaultStrategy()),
  
  
  STRING(new StringStrategy());
  
  private final Strategy strategy;
  
  private LongSerializationPolicy(Strategy strategy) {
    this.strategy = strategy;
  }

    public JsonElement serialize(Long value) {
    return strategy.serialize(value);
  }
  
  private interface Strategy {
    JsonElement serialize(Long value);
  }
  
  private static class DefaultStrategy implements Strategy {
    public JsonElement serialize(Long value) {
      return new JsonPrimitive(value);
    }
  }
  
  private static class StringStrategy implements Strategy {
    public JsonElement serialize(Long value) {
      return new JsonPrimitive(String.valueOf(value));
    }
  }
}
