package com.velocity.gson;

/**
 *@author ranjitk
 */
interface FieldNamingStrategy2 {

  /**
   * Translates the field name into its JSON field name representation.
   *
   * @param f the field that is being translated
   * @return the translated field name.
   */
  public String translateName(FieldAttributes f);
}
