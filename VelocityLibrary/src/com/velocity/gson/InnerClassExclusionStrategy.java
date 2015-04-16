package com.velocity.gson;

import java.lang.reflect.Modifier;

/**
 * Strategy for excluding inner classes.
 *
 * @author Ranjitk
 */
final class InnerClassExclusionStrategy implements ExclusionStrategy {

  public boolean shouldSkipField(FieldAttributes f) {
    return isInnerClass(f.getDeclaredClass());
  }

  public boolean shouldSkipClass(Class<?> clazz) {
    return isInnerClass(clazz);
  }

  private boolean isInnerClass(Class<?> clazz) {
    return clazz.isMemberClass() && !isStatic(clazz);
  }

  private boolean isStatic(Class<?> clazz) {
    return (clazz.getModifiers() & Modifier.STATIC) != 0;
  }
}
