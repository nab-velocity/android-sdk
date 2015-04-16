package com.velocity.gson;

import com.velocity.gson.internal.$Gson$Preconditions;

import java.util.Stack;

/**
 * @author Ranjitk
 */
final class MemoryRefStack {
  private final Stack<ObjectTypePair> stack = new Stack<ObjectTypePair>();

  /**
   * Adds a new element to the top of the stack.
   *
   * @param obj the object to add to the stack
   * @return the object that was added
   */
  public ObjectTypePair push(ObjectTypePair obj) {
    $Gson$Preconditions.checkNotNull(obj);
    return stack.push(obj);
  }

  /**
   * Removes the top element from the stack.
   *
   * @return the element being removed from the stack
   * @throws java.util.EmptyStackException thrown if the stack is empty
   */
  public ObjectTypePair pop() {
    return stack.pop();
  }

  public boolean isEmpty() {
    return stack.isEmpty();
  }

  /**
   * Retrieves the item from the top of the stack, but does not remove it.
   *
   * @return the item from the top of the stack
   * @throws java.util.EmptyStackException thrown if the stack is empty
   */
  public ObjectTypePair peek() {
    return stack.peek();
  }

  /**
   * Performs a memory reference check to see it the {@code obj} exists in
   * the stack.
   *
   * @param obj the object to search for in the stack
   * @return true if this object is already in the stack otherwise false
   */
  public boolean contains(ObjectTypePair obj) {
    if (obj == null) {
      return false;
    }

    for (ObjectTypePair stackObject : stack) {
      if (stackObject.getObject() == obj.getObject()
          && stackObject.type.equals(obj.type) ) {
        return true;
      }
    }
    return false;
  }
}
