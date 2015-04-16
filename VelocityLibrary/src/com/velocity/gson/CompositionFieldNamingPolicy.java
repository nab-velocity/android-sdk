package com.velocity.gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Performs numerous field naming translations wrapped up as one object.
 *
 * @author Ranjitk
 */
abstract class CompositionFieldNamingPolicy extends RecursiveFieldNamingPolicy {

  private final RecursiveFieldNamingPolicy[] fieldPolicies;

  public CompositionFieldNamingPolicy(RecursiveFieldNamingPolicy... fieldNamingPolicies) {
    if (fieldNamingPolicies == null) {
      throw new NullPointerException("naming policies can not be null.");
    }
    this.fieldPolicies = fieldNamingPolicies;
  }

  @Override
  protected String translateName(String target, Type fieldType, Collection<Annotation> annotations) {
    for (RecursiveFieldNamingPolicy policy : fieldPolicies) {
      target = policy.translateName(target, fieldType, annotations);
    }
    return target;
  }
}
