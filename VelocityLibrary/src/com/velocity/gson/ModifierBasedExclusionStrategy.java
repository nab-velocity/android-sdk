package com.velocity.gson;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Ranjitk
 */
final class ModifierBasedExclusionStrategy implements ExclusionStrategy {
  private final Collection<Integer> modifiers;

  public ModifierBasedExclusionStrategy(int... modifiers) {
    this.modifiers = new HashSet<Integer>();
    if (modifiers != null) {
      for (int modifier : modifiers) {
        this.modifiers.add(modifier);
      }
    }
  }

  public boolean shouldSkipField(FieldAttributes f) {
    for (int modifier : modifiers) {
      if (f.hasModifier(modifier)) {
        return true;
      }
    }
    return false;
  }

  public boolean shouldSkipClass(Class<?> clazz) {
    return false;
  }
}
