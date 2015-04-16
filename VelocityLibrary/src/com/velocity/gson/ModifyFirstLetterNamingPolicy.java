package com.velocity.gson;

import com.velocity.gson.internal.$Gson$Preconditions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author Ranjitk
 */
final class ModifyFirstLetterNamingPolicy extends RecursiveFieldNamingPolicy {

  public enum LetterModifier {
    UPPER,
    LOWER;
  }

  private final LetterModifier letterModifier;

  /**
   * Creates a new ModifyFirstLetterNamingPolicy that will either modify the first letter of the
   * target name to either UPPER case or LOWER case depending on the {@code modifier} parameter.
   *
   * @param modifier the type of modification that should be performed
   * @throws IllegalArgumentException if {@code modifier} is null
   */
  ModifyFirstLetterNamingPolicy(LetterModifier modifier) {
    this.letterModifier = $Gson$Preconditions.checkNotNull(modifier);
  }

  @Override
  protected String translateName(String target, Type fieldType,
      Collection<Annotation> annotations) {
    StringBuilder fieldNameBuilder = new StringBuilder();
    int index = 0;
    char firstCharacter = target.charAt(index);

    while (index < target.length() - 1) {
      if (Character.isLetter(firstCharacter)) {
        break;
      }

      fieldNameBuilder.append(firstCharacter);
      firstCharacter = target.charAt(++index);
    }

    if (index == target.length()) {
      return fieldNameBuilder.toString();
    }

    boolean capitalizeFirstLetter = (letterModifier == LetterModifier.UPPER);
    if (capitalizeFirstLetter && !Character.isUpperCase(firstCharacter)) {
      String modifiedTarget = modifyString(Character.toUpperCase(firstCharacter), target, ++index);
      return fieldNameBuilder.append(modifiedTarget).toString();
    } else if (!capitalizeFirstLetter && Character.isUpperCase(firstCharacter)) {
      String modifiedTarget = modifyString(Character.toLowerCase(firstCharacter), target, ++index);
      return fieldNameBuilder.append(modifiedTarget).toString();
    } else {
      return target;
    }
  }

  private String modifyString(char firstCharacter, String srcString, int indexOfSubstring) {
    return (indexOfSubstring < srcString.length())
        ? firstCharacter + srcString.substring(indexOfSubstring)
        : String.valueOf(firstCharacter);
  }
}
