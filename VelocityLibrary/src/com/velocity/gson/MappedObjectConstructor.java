package com.velocity.gson;


import com.velocity.gson.internal.$Gson$Types;
import com.velocity.gson.internal.UnsafeAllocator;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

/**
 * @author Ranjitk
 */
final class MappedObjectConstructor implements ObjectConstructor {
  private static final UnsafeAllocator unsafeAllocator = UnsafeAllocator.create();
  private static final DefaultConstructorAllocator defaultConstructorAllocator =
      new DefaultConstructorAllocator(500);

  private final ParameterizedTypeHandlerMap<InstanceCreator<?>> instanceCreatorMap;

  public MappedObjectConstructor(
      ParameterizedTypeHandlerMap<InstanceCreator<?>> instanceCreators) {
    instanceCreatorMap = instanceCreators;
  }

  @SuppressWarnings("unchecked")
  public <T> T construct(Type typeOfT) {
    InstanceCreator<T> creator = (InstanceCreator<T>) instanceCreatorMap.getHandlerFor(typeOfT, false);
    if (creator != null) {
      return creator.createInstance(typeOfT);
    }
    return (T) constructWithAllocators(typeOfT);
  }

  public Object constructArray(Type type, int length) {
    return Array.newInstance($Gson$Types.getRawType(type), length);
  }

  @SuppressWarnings({"unchecked", "cast"})
  private <T> T constructWithAllocators(Type typeOfT) {
    try {
      Class<T> clazz = (Class<T>) $Gson$Types.getRawType(typeOfT);
      T obj = defaultConstructorAllocator.newInstance(clazz);
      return (obj == null)
          ? unsafeAllocator.newInstance(clazz)
          : obj;
    } catch (Exception e) {
      throw new RuntimeException(("Unable to invoke no-args constructor for " + typeOfT + ". "
          + "Register an InstanceCreator with Gson for this type may fix this problem."), e);
    }
  }

  @Override
  public String toString() {
    return instanceCreatorMap.toString();
  }
}
