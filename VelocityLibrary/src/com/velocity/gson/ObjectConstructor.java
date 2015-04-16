package com.velocity.gson;

import java.lang.reflect.Type;

/**
 * @author Ranjitk
 */
interface ObjectConstructor {

  /**
   * Creates a new instance of the given type.
   *
   * @param typeOfT the class type that should be instantiated
   * @return a default instance of the provided class.
   */
  public <T> T construct(Type typeOfT);

  /**
   * Constructs an array type of the provided length.
   *
   * @param typeOfArrayElements type of objects in the array
   * @param length size of the array
   * @return new array of size length
   */
  public Object constructArray(Type typeOfArrayElements, int length);
}