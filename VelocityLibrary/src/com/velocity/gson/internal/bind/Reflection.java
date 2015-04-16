
package com.velocity.gson.internal.bind;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class Reflection {
  /**
   * Finds a compatible runtime type if it is more specific
   */
  public static Type getRuntimeTypeIfMoreSpecific(Type type, Object value) {
    if (value != null
        && (type == Object.class || type instanceof TypeVariable || type instanceof Class<?>)) {
      type = (Class<?>) value.getClass();
    }
    return type;
  }

  // TODO: this should use Joel's unsafe constructor stuff
  public static <T> T newInstance(Constructor<T> constructor) {
    try {
      Object[] args = null;
      return constructor.newInstance(args);
    } catch (InstantiationException e) {
      // TODO: JsonParseException ?
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      // TODO: don't wrap if cause is unchecked!
      // TODO: JsonParseException ?
      throw new RuntimeException(e.getTargetException());
    } catch (IllegalAccessException e) {
      throw new AssertionError(e);
    }
  }

}
