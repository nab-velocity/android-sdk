package com.velocity.gson;

import com.velocity.gson.internal.$Gson$Preconditions;

import java.util.Collection;

/**
 * A wrapper class used to collect numerous {@link ExclusionStrategy} objects
 * and perform a short-circuited OR operation.
 *
 * @author Ranjitk
 */
final class DisjunctionExclusionStrategy implements ExclusionStrategy {
  private final Collection<ExclusionStrategy> strategies;

  DisjunctionExclusionStrategy(Collection<ExclusionStrategy> strategies) {
    this.strategies = $Gson$Preconditions.checkNotNull(strategies);
  }

  public boolean shouldSkipField(FieldAttributes f) {
    for (ExclusionStrategy strategy : strategies) {
      if (strategy.shouldSkipField(f)) {
        return true;
      }
    }
    return false;
  }

  public boolean shouldSkipClass(Class<?> clazz) {
    for (ExclusionStrategy strategy : strategies) {
      if (strategy.shouldSkipClass(clazz)) {
        return true;
      }
    }
    return false;
  }
}
