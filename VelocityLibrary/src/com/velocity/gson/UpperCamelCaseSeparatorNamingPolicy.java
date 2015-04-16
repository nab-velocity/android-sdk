package com.velocity.gson;

/**
 * @author Ranjitk
 */
final class UpperCamelCaseSeparatorNamingPolicy extends CompositionFieldNamingPolicy {

  public UpperCamelCaseSeparatorNamingPolicy(String separatorString) {
    super(new CamelCaseSeparatorNamingPolicy(separatorString),
        new ModifyFirstLetterNamingPolicy(ModifyFirstLetterNamingPolicy.LetterModifier.UPPER));
  }
}
