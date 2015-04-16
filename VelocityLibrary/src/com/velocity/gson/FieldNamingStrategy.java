package com.velocity.gson;

import java.lang.reflect.Field;

/**
 * @author ranjitk
 */
public interface FieldNamingStrategy {

  public String translateName(Field f);
}
