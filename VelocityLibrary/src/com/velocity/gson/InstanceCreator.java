package com.velocity.gson;

import java.lang.reflect.Type;

/**
 * @author Ranjitk
 * 
 */
public interface InstanceCreator<T> {

  public T createInstance(Type type);
}
