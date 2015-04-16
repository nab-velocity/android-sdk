package com.velocity.gson;

import com.velocity.gson.annotations.Expose;

/**
 * Excludes fields that do not have the {@link Expose} annotation
 *
 * @author Ranjitk
 */
final class ExposeAnnotationDeserializationExclusionStrategy implements ExclusionStrategy {
  public boolean shouldSkipClass(Class<?> clazz) {
    return false;
  }

  public boolean shouldSkipField(FieldAttributes f) {
    Expose annotation = f.getAnnotation(Expose.class);
    if (annotation == null) {
      return true;
    }
    return !annotation.deserialize();
  }
}
