package com.velocity.gson;


import java.lang.reflect.Constructor;

/**
 * Use the default constructor on the class to instantiate an object.
 *
 * @author Ranjitk
 */
final class DefaultConstructorAllocator {
  private static final Constructor<Null> NULL_CONSTRUCTOR = createNullConstructor();

  private final Cache<Class<?>, Constructor<?>> constructorCache;

  public DefaultConstructorAllocator() {
    this(200);
  }

  public DefaultConstructorAllocator(int cacheSize) {
    constructorCache = new LruCache<Class<?>, Constructor<?>>(cacheSize);
  }

  // for testing purpose
  final boolean isInCache(Class<?> cacheKey) {
    return constructorCache.getElement(cacheKey) != null;
  }

  private static final Constructor<Null> createNullConstructor() {
    try {
      return getNoArgsConstructor(Null.class);
    } catch (Exception e) {
      return null;
    }
  }

  public <T> T newInstance(Class<T> c) throws Exception {
    Constructor<T> constructor = findConstructor(c);
    return (constructor != null) ? constructor.newInstance() : null;
  }

  @SuppressWarnings("unchecked")
  private <T> Constructor<T> findConstructor(Class<T> c) {
    Constructor<T> cachedElement = (Constructor<T>) constructorCache.getElement(c);
    if (cachedElement != null) {
      if (cachedElement == NULL_CONSTRUCTOR) {
        return null;
      } else {
        return cachedElement;
      }
    }

    Constructor<T> noArgsConstructor = getNoArgsConstructor(c);
    if (noArgsConstructor != null) {
      constructorCache.addElement(c, noArgsConstructor);
    } else {
      constructorCache.addElement(c, NULL_CONSTRUCTOR);
    }
    return noArgsConstructor;
  }

  private static <T> Constructor<T> getNoArgsConstructor(Class<T> c) {
    try {
      Constructor<T> declaredConstructor = c.getDeclaredConstructor();
      declaredConstructor.setAccessible(true);
      return declaredConstructor;
    } catch (Exception e) {
      return null;
    }
  }

  // placeholder class for Null constructor
  private static final class Null {
  }
}
