package com.velocity.gson;

import com.velocity.gson.annotations.Since;
import com.velocity.gson.annotations.Until;
import com.velocity.gson.internal.$Gson$Preconditions;

/**
 * This strategy will exclude any files and/or class that are passed the
 * {@link #version} value.
 *
 * @author Ranjitk
 */
final class VersionExclusionStrategy implements ExclusionStrategy {
  private final double version;

  VersionExclusionStrategy(double version) {
    $Gson$Preconditions.checkArgument(version >= 0.0D);
    this.version = version;
  }

  public boolean shouldSkipField(FieldAttributes f) {
    return !isValidVersion(f.getAnnotation(Since.class), f.getAnnotation(Until.class));
  }

  public boolean shouldSkipClass(Class<?> clazz) {
    return !isValidVersion(clazz.getAnnotation(Since.class), clazz.getAnnotation(Until.class));
  }

  private boolean isValidVersion(Since since, Until until) {
    return (isValidSince(since) && isValidUntil(until));
  }

  private boolean isValidSince(Since annotation) {
    if (annotation != null) {
      double annotationVersion = annotation.value();
      if (annotationVersion > version) {
        return false;
      }
    }
    return true;
  }

  private boolean isValidUntil(Until annotation) {
    if (annotation != null) {
      double annotationVersion = annotation.value();
      if (annotationVersion <= version) {
        return false;
      }
    }
    return true;
  }
}
