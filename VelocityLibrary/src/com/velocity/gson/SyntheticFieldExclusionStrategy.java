package com.velocity.gson;

/**
 * @author Ranjitk
 */
final class SyntheticFieldExclusionStrategy implements ExclusionStrategy {
  private final boolean skipSyntheticFields;

  SyntheticFieldExclusionStrategy(boolean skipSyntheticFields) {
    this.skipSyntheticFields = skipSyntheticFields;
  }

  public boolean shouldSkipClass(Class<?> clazz) {
    return false;
  }

  public boolean shouldSkipField(FieldAttributes f) {
    return skipSyntheticFields && f.isSynthetic();
  }

}
