package com.velocity.gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author Ranjitk
 */
final class LowerCaseNamingPolicy extends RecursiveFieldNamingPolicy {

  @Override
  protected String translateName(String target, Type fieldType,
      Collection<Annotation> annotations) {
    return target.toLowerCase();
  }
}
