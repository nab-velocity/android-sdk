
package com.velocity.gson.internal.bind;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import com.velocity.gson.JsonElement;
import com.velocity.gson.internal.Streams;
import com.velocity.gson.reflect.TypeToken;
import com.velocity.gson.stream.JsonReader;
import com.velocity.gson.stream.JsonWriter;

public abstract class TypeAdapter<T> {
  public abstract T read(JsonReader reader) throws IOException;
  public abstract void write(JsonWriter writer, T value) throws IOException;

  public final String toJson(T value) throws IOException {
    StringWriter stringWriter = new StringWriter();
    write(stringWriter, value);
    return stringWriter.toString();
  }

  public final void write(Writer out, T value) throws IOException {
    JsonWriter writer = new JsonWriter(out);
    write(writer, value);
  }

  public final T fromJson(String json) throws IOException {
    return read(new StringReader(json));
  }

  public final T read(Reader in) throws IOException {
    JsonReader reader = new JsonReader(in);
    reader.setLenient(true);
    return read(reader);
  }

  public JsonElement toJsonElement(T src) {
    try {
      StringWriter stringWriter = new StringWriter();
      JsonWriter jsonWriter = new JsonWriter(stringWriter);
      jsonWriter.setLenient(true);
      write(jsonWriter, src);
      JsonReader reader = new JsonReader(new StringReader(stringWriter.toString()));
      reader.setLenient(true);
      return Streams.parse(reader);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public T fromJsonElement(JsonElement json) {
    try {
      StringWriter stringWriter = new StringWriter();
      JsonWriter jsonWriter = new JsonWriter(stringWriter);
      jsonWriter.setLenient(true);
      Streams.write(json, false, jsonWriter);
      JsonReader jsonReader = new JsonReader(new StringReader(stringWriter.toString()));
      jsonReader.setLenient(true);
      return read(jsonReader);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public interface Factory {
    <T> TypeAdapter<T> create(MiniGson context, TypeToken<T> type);
  }
}
