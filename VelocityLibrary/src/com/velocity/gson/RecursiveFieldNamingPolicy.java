package com.velocity.gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author Ranjitk
 */
abstract class RecursiveFieldNamingPolicy implements FieldNamingStrategy2 {

  public final String translateName(FieldAttributes f) {
    return translateName(f.getName(), f.getDeclaredType(), f.getAnnotations());
  }

  /**
   * Performs the specific string translation.
   *
   * @param target the string object that will be manipulation/translated
   * @param fieldType the actual type value of the field
   * @param annotations the annotations set on the field
   * @return the translated field name
   */
  protected abstract String translateName(String target, Type fieldType, Collection<Annotation> annotations);
}
