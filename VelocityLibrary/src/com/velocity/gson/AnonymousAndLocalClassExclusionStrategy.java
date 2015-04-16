package com.velocity.gson;

/**
 * Strategy for excluding anonymous and local classes.
 *
 * @author Ranjitk
 */
final class AnonymousAndLocalClassExclusionStrategy implements ExclusionStrategy {

  public boolean shouldSkipField(FieldAttributes f) {
    return isAnonymousOrLocal(f.getDeclaredClass());
  }

  public boolean shouldSkipClass(Class<?> clazz) {
    return isAnonymousOrLocal(clazz);
  }

  private boolean isAnonymousOrLocal(Class<?> clazz) {
    return !Enum.class.isAssignableFrom(clazz)
        && (clazz.isAnonymousClass() || clazz.isLocalClass());
  }
}
