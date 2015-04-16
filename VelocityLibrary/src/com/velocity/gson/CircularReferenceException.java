package com.velocity.gson;

/**
 * Exception class to indicate a circular reference error.
 * This class is not part of the public API and hence is not public.
 *
 * @author Ranjitk
 * 
 */
final class CircularReferenceException extends RuntimeException {
  private static final long serialVersionUID = 7444343294106513081L;
  private final Object offendingNode;

  CircularReferenceException(Object offendingNode) {
    super("circular reference error");
    this.offendingNode = offendingNode;
  }
  
  public IllegalStateException createDetailedException(FieldAttributes offendingField) {
    StringBuilder msg = new StringBuilder(getMessage());
    if (offendingField != null) {
      msg.append("\n  ").append("Offending field: ").append(offendingField.getName() + "\n");
    }
    if (offendingNode != null) {
      msg.append("\n  ").append("Offending object: ").append(offendingNode);
    }
    return new IllegalStateException(msg.toString(), this);
  }
}
