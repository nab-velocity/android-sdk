package com.velocity.gson;

import com.velocity.gson.internal.$Gson$Preconditions;

/**
 *@author ranjitk
 */
final class FieldNamingStrategy2Adapter implements FieldNamingStrategy2 {
  private final FieldNamingStrategy adaptee;

  FieldNamingStrategy2Adapter(FieldNamingStrategy adaptee) {
    this.adaptee = $Gson$Preconditions.checkNotNull(adaptee);
  }

  @SuppressWarnings("deprecation")
  public String translateName(FieldAttributes f) {
    return adaptee.translateName(f.getFieldObject());
  }
}
