package com.velocity.gson;

import com.velocity.gson.internal.$Gson$Preconditions;

import java.io.IOException;

/**
 *
 * @author Ranjitk
 */
final class DelegatingJsonElementVisitor implements JsonElementVisitor {
  private final JsonElementVisitor delegate;

  protected DelegatingJsonElementVisitor(JsonElementVisitor delegate) {
    this.delegate = $Gson$Preconditions.checkNotNull(delegate);
  }

  public void endArray(JsonArray array) throws IOException {
    delegate.endArray(array);
  }

  public void endObject(JsonObject object) throws IOException {
    delegate.endObject(object);
  }

  public void startArray(JsonArray array) throws IOException {
    delegate.startArray(array);
  }

  public void startObject(JsonObject object) throws IOException {
    delegate.startObject(object);
  }

  public void visitArrayMember(JsonArray parent, JsonPrimitive member,
      boolean isFirst) throws IOException {
    delegate.visitArrayMember(parent, member, isFirst);
  }

  public void visitArrayMember(JsonArray parent, JsonArray member,
      boolean isFirst) throws IOException {
    delegate.visitArrayMember(parent, member, isFirst);
  }

  public void visitArrayMember(JsonArray parent, JsonObject member,
      boolean isFirst) throws IOException {
    delegate.visitArrayMember(parent, member, isFirst);
  }

  public void visitObjectMember(JsonObject parent, String memberName, JsonPrimitive member,
      boolean isFirst) throws IOException {
    delegate.visitObjectMember(parent, memberName, member, isFirst);
  }

  public void visitObjectMember(JsonObject parent, String memberName, JsonArray member,
      boolean isFirst) throws IOException {
    delegate.visitObjectMember(parent, memberName, member, isFirst);
  }

  public void visitObjectMember(JsonObject parent, String memberName, JsonObject member,
      boolean isFirst) throws IOException {
    delegate.visitObjectMember(parent, memberName, member, isFirst);
  }

  public void visitNullObjectMember(JsonObject parent, String memberName,
      boolean isFirst) throws IOException {
    delegate.visitNullObjectMember(parent, memberName, isFirst);
  }

  public void visitPrimitive(JsonPrimitive primitive) throws IOException {
    delegate.visitPrimitive(primitive);
  }

  public void visitNull() throws IOException {
    delegate.visitNull();
  }

  public void visitNullArrayMember(JsonArray parent, boolean isFirst) throws IOException {
    delegate.visitNullArrayMember(parent, isFirst);
  }
}
