package com.velocity.gson;

/**
 * @author Ranjitk
 */
final class LowerCamelCaseSeparatorNamingPolicy extends CompositionFieldNamingPolicy {

  public LowerCamelCaseSeparatorNamingPolicy(String separatorString) {
    super(new CamelCaseSeparatorNamingPolicy(separatorString), new LowerCaseNamingPolicy());
  }
}
