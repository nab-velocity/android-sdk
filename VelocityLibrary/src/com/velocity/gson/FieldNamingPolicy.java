package com.velocity.gson;

/**
 *
 * @author ranjitk
 * 
 */
public enum FieldNamingPolicy {

  UPPER_CAMEL_CASE(new ModifyFirstLetterNamingPolicy(
      ModifyFirstLetterNamingPolicy.LetterModifier.UPPER)),

  UPPER_CAMEL_CASE_WITH_SPACES(new UpperCamelCaseSeparatorNamingPolicy(" ")),
          
  LOWER_CASE_WITH_UNDERSCORES(new LowerCamelCaseSeparatorNamingPolicy("_")),
  
 
  LOWER_CASE_WITH_DASHES(new LowerCamelCaseSeparatorNamingPolicy("-"));

  private final FieldNamingStrategy2 namingPolicy;

  private FieldNamingPolicy(FieldNamingStrategy2 namingPolicy) {
    this.namingPolicy = namingPolicy;
  }

  FieldNamingStrategy2 getFieldNamingPolicy() {
    return namingPolicy;
  }
}
