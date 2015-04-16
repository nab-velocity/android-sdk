package com.velocity.gson;

import com.velocity.gson.internal.$Gson$Preconditions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 *
 * @author Ranjitk
 */
final class CamelCaseSeparatorNamingPolicy extends RecursiveFieldNamingPolicy {
  private final String separatorString;

  /**
   * Constructs a new CamelCaseSeparatorNamingPolicy object that will add the
   * {@code separatorString} between each of the words separated by camel case.
   *
   * @param separatorString the string value to place between words
   * @throws IllegalArgumentException thrown if the {@code separatorString} parameter
   *         is null or empty.
   */
  public CamelCaseSeparatorNamingPolicy(String separatorString) {
    $Gson$Preconditions.checkNotNull(separatorString);
    $Gson$Preconditions.checkArgument(!"".equals(separatorString));
    this.separatorString = separatorString;
  }

  @Override
  protected String translateName(String target, Type fieldType,
      Collection<Annotation> annnotations) {
    StringBuilder translation = new StringBuilder();
    for (int i = 0; i < target.length(); i++) {
      char character = target.charAt(i);
      if (Character.isUpperCase(character) && translation.length() != 0) {
        translation.append(separatorString);
      }
      translation.append(character);
    }

    return translation.toString();
  }
}
