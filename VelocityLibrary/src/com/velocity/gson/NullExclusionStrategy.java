package com.velocity.gson;

/**
 *
 * @author Ranjitk
 */
final class NullExclusionStrategy implements ExclusionStrategy {

  public boolean shouldSkipField(FieldAttributes f) {
    return false;
  }

  public boolean shouldSkipClass(Class<?> clazz) {
    return false;
  }
}
