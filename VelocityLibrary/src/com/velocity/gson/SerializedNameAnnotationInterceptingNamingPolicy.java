package com.velocity.gson;

import com.velocity.gson.annotations.SerializedName;

/**
 * A {@link FieldNamingStrategy2} that acts as a chain of responsibility. If the
 * @author Ranjitk
 */
final class SerializedNameAnnotationInterceptingNamingPolicy implements FieldNamingStrategy2 {
  private final FieldNamingStrategy2 delegate;

  SerializedNameAnnotationInterceptingNamingPolicy(FieldNamingStrategy2 delegate) {
    this.delegate = delegate;
  }

  public String translateName(FieldAttributes f) {
    SerializedName serializedName = f.getAnnotation(SerializedName.class);
    return serializedName == null ? delegate.translateName(f) : serializedName.value();
  }
}
