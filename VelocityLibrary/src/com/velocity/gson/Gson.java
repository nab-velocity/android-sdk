
package com.velocity.gson;

import com.velocity.gson.internal.Streams;
import com.velocity.gson.internal.bind.ArrayTypeAdapter;
import com.velocity.gson.internal.bind.CollectionTypeAdapter;
import com.velocity.gson.internal.bind.MiniGson;
import com.velocity.gson.internal.bind.ReflectiveTypeAdapter;
import com.velocity.gson.internal.bind.StringToValueMapTypeAdapter;
import com.velocity.gson.internal.bind.TypeAdapter;
import com.velocity.gson.internal.bind.TypeAdapters;
import com.velocity.gson.reflect.TypeToken;
import com.velocity.gson.stream.JsonReader;
import com.velocity.gson.stream.JsonToken;
import com.velocity.gson.stream.JsonWriter;
import com.velocity.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public final class Gson {

  //TODO(inder): get rid of all the registerXXX methods and take all such parameters in the
  // constructor instead. At the minimum, mark those methods private.

   static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;

  // Default instances of plug-ins
  static final AnonymousAndLocalClassExclusionStrategy DEFAULT_ANON_LOCAL_CLASS_EXCLUSION_STRATEGY =
      new AnonymousAndLocalClassExclusionStrategy();
  static final SyntheticFieldExclusionStrategy DEFAULT_SYNTHETIC_FIELD_EXCLUSION_STRATEGY =
      new SyntheticFieldExclusionStrategy(true);
  static final ModifierBasedExclusionStrategy DEFAULT_MODIFIER_BASED_EXCLUSION_STRATEGY =
      new ModifierBasedExclusionStrategy(new int[] { Modifier.TRANSIENT, Modifier.STATIC });
  static final FieldNamingStrategy2 DEFAULT_NAMING_POLICY =
      new SerializedNameAnnotationInterceptingNamingPolicy(new JavaFieldNamingPolicy());

  private static final ExclusionStrategy DEFAULT_EXCLUSION_STRATEGY = createExclusionStrategy();

  private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";

  private final ExclusionStrategy deserializationExclusionStrategy;
  private final ExclusionStrategy serializationExclusionStrategy;
  private final FieldNamingStrategy2 fieldNamingPolicy;
  private final MappedObjectConstructor objectConstructor;

  /** Map containing Type or Class objects as keys */
  private final ParameterizedTypeHandlerMap<JsonSerializer<?>> serializers;

  /** Map containing Type or Class objects as keys */
  private final ParameterizedTypeHandlerMap<JsonDeserializer<?>> deserializers;

  private final boolean serializeNulls;
  private final boolean htmlSafe;
  private final boolean generateNonExecutableJson;
  private final boolean prettyPrinting;

  private final MiniGson miniGson;

  
  public Gson() {
    this(DEFAULT_EXCLUSION_STRATEGY, DEFAULT_EXCLUSION_STRATEGY, DEFAULT_NAMING_POLICY,
        new MappedObjectConstructor(DefaultTypeAdapters.getDefaultInstanceCreators()),
        false, DefaultTypeAdapters.getAllDefaultSerializers(),
        DefaultTypeAdapters.getAllDefaultDeserializers(), DEFAULT_JSON_NON_EXECUTABLE, true, false,
        false, LongSerializationPolicy.DEFAULT);
  }

  Gson(final ExclusionStrategy deserializationExclusionStrategy,
      final ExclusionStrategy serializationExclusionStrategy,
      final FieldNamingStrategy2 fieldNamingPolicy,
      final MappedObjectConstructor objectConstructor, boolean serializeNulls,
      final ParameterizedTypeHandlerMap<JsonSerializer<?>> serializers,
      final ParameterizedTypeHandlerMap<JsonDeserializer<?>> deserializers,
      boolean generateNonExecutableGson, boolean htmlSafe, boolean prettyPrinting,
      boolean serializeSpecialFloatingPointValues, LongSerializationPolicy longSerializationPolicy) {
    this.deserializationExclusionStrategy = deserializationExclusionStrategy;
    this.serializationExclusionStrategy = serializationExclusionStrategy;
    this.fieldNamingPolicy = fieldNamingPolicy;
    this.objectConstructor = objectConstructor;
    this.serializeNulls = serializeNulls;
    this.serializers = serializers;
    this.deserializers = deserializers;
    this.generateNonExecutableJson = generateNonExecutableGson;
    this.htmlSafe = htmlSafe;
    this.prettyPrinting = prettyPrinting;

    /*
      TODO: for serialization, honor:
        serializationExclusionStrategy
        fieldNamingPolicy
        serializeNulls
        serializers
     */
    TypeAdapter.Factory reflectiveTypeAdapterFactory =
      new ReflectiveTypeAdapter.FactoryImpl() {
      @Override
      public String getFieldName(Class<?> declaringClazz, Field f, Type declaredType) {
        return fieldNamingPolicy.translateName(new FieldAttributes(declaringClazz, f, declaredType));
      }
      @Override
      public boolean serializeField(Class<?> declaringClazz, Field f, Type declaredType) {
        return !Gson.this.serializationExclusionStrategy.shouldSkipField(
            new FieldAttributes(declaringClazz, f, declaredType));
      }
      @Override
      public boolean deserializeField(Class<?> declaringClazz, Field f, Type declaredType) {
        return !Gson.this.deserializationExclusionStrategy.shouldSkipField(
            new FieldAttributes(declaringClazz, f, declaredType));
      }
    };

    TypeAdapter.Factory excludedTypeFactory = new TypeAdapter.Factory() {
      public <T> TypeAdapter<T> create(MiniGson context, TypeToken<T> type) {
        Class<?> rawType = type.getRawType();
        if (serializationExclusionStrategy.shouldSkipClass(rawType)
            || deserializationExclusionStrategy.shouldSkipClass(rawType)) {
          return TypeAdapters.EXCLUDED_TYPE_ADAPTER;
        } else {
          return null;
        }
      }
    };

    MiniGson.Builder builder = new MiniGson.Builder()
        .withoutDefaultFactories()
        .factory(TypeAdapters.BOOLEAN_FACTORY)
        .factory(TypeAdapters.INTEGER_FACTORY)
        .factory(TypeAdapters.newFactory(double.class, Double.class,
            doubleAdapter(serializeSpecialFloatingPointValues)))
        .factory(TypeAdapters.newFactory(float.class, Float.class,
            floatAdapter(serializeSpecialFloatingPointValues)))
        .factory(TypeAdapters.newFactory(long.class, Long.class,
            longAdapter(longSerializationPolicy)))
        .factory(TypeAdapters.STRING_FACTORY)
        .factory(excludedTypeFactory)
        .factory(new GsonToMiniGsonTypeAdapter(serializers, deserializers, serializeNulls))
        .factory(CollectionTypeAdapter.FACTORY)
        .factory(StringToValueMapTypeAdapter.FACTORY)
        .factory(ArrayTypeAdapter.FACTORY)
        .factory(reflectiveTypeAdapterFactory);

    this.miniGson = builder.build();
  }

  private TypeAdapter<Double> doubleAdapter(boolean serializeSpecialFloatingPointValues) {
    if (serializeSpecialFloatingPointValues) {
      return TypeAdapters.DOUBLE;
    }
    return new TypeAdapter<Double>() {
      @Override public Double read(JsonReader reader) throws IOException {
        return reader.nextDouble();
      }
      @Override public void write(JsonWriter writer, Double value) throws IOException {
        checkValidFloatingPoint(value);
        writer.value(value);
      }
    };
  }

  private TypeAdapter<Float> floatAdapter(boolean serializeSpecialFloatingPointValues) {
    if (serializeSpecialFloatingPointValues) {
      return TypeAdapters.FLOAT;
    }
    return new TypeAdapter<Float>() {
      @Override public Float read(JsonReader reader) throws IOException {
        return (float) reader.nextDouble();
      }
      @Override public void write(JsonWriter writer, Float value) throws IOException {
        checkValidFloatingPoint(value);
        writer.value(value);
      }
    };
  }

  private void checkValidFloatingPoint(double value) {
    if (Double.isNaN(value) || Double.isInfinite(value)) {
      throw new IllegalArgumentException(value
          + " is not a valid double value as per JSON specification. To override this"
          + " behavior, use GsonBuilder.serializeSpecialDoubleValues() method.");
    }
  }

  private TypeAdapter<Long> longAdapter(LongSerializationPolicy longSerializationPolicy) {
    if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
      return TypeAdapters.LONG;
    }
    return new TypeAdapter<Long>() {
      @Override public Long read(JsonReader reader) throws IOException {
        return reader.nextLong();
      }
      @Override public void write(JsonWriter writer, Long value) throws IOException {
        writer.value(value.toString());
      }
    };
  }

  private static ExclusionStrategy createExclusionStrategy() {
    List<ExclusionStrategy> strategies = new LinkedList<ExclusionStrategy>();
    strategies.add(DEFAULT_ANON_LOCAL_CLASS_EXCLUSION_STRATEGY);
    strategies.add(DEFAULT_SYNTHETIC_FIELD_EXCLUSION_STRATEGY);
    strategies.add(DEFAULT_MODIFIER_BASED_EXCLUSION_STRATEGY);
    return new DisjunctionExclusionStrategy(strategies);
  }

  /**
   * This method serializes the specified object into its equivalent representation as a tree of
   * {@link JsonElement}s. This method should be used when the specified object is not a generic
   * type. This method uses {@link Class#getClass()} to get the type for the specified object, but
   * the {@code getClass()} loses the generic type information because of the Type Erasure feature
   * of Java. Note that this method works fine if the any of the object fields are of generic type,
   * just the object itself should not be of a generic type. If the object is of generic type, use
   * {@link #toJsonTree(Object, Type)} instead.
   *
   * @param src the object for which Json representation is to be created setting for Gson
   * @return Json representation of {@code src}.
   * @since 1.4
   */
  public JsonElement toJsonTree(Object src) {
    if (src == null) {
      return JsonNull.INSTANCE;
    }
    return toJsonTree(src, src.getClass());
  }

  /**
   * This method serializes the specified object, including those of generic types, into its
   * equivalent representation as a tree of {@link JsonElement}s. This method must be used if the
   * specified object is a generic type. For non-generic objects, use {@link #toJsonTree(Object)}
   * instead.
   *
   * @param src the object for which JSON representation is to be created
   * @param typeOfSrc The specific genericized type of src. You can obtain
   * this type by using the {@link com.velocity.gson.reflect.TypeToken} class. For example,
   * to get the type for {@code Collection<Foo>}, you should use:
   * <pre>
   * Type typeOfSrc = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType();
   * </pre>
   * @return Json representation of {@code src}
   * @since 1.4
   */
  @SuppressWarnings("unchecked") // the caller is required to make src and typeOfSrc consistent
  public JsonElement toJsonTree(Object src, Type typeOfSrc) {
    // Serialize 'src' to JSON, then deserialize that to a JSON tree.
    TypeAdapter adapter = miniGson.getAdapter(TypeToken.get(typeOfSrc));
    return adapter.toJsonElement(src);
  }

  /**
   * This method serializes the specified object into its equivalent Json representation.
   * This method should be used when the specified object is not a generic type. This method uses
   * {@link Class#getClass()} to get the type for the specified object, but the
   * {@code getClass()} loses the generic type information because of the Type Erasure feature
   * of Java. Note that this method works fine if the any of the object fields are of generic type,
   * just the object itself should not be of a generic type. If the object is of generic type, use
   * {@link #toJson(Object, Type)} instead. If you want to write out the object to a
   * {@link Writer}, use {@link #toJson(Object, Appendable)} instead.
   *
   * @param src the object for which Json representation is to be created setting for Gson
   * @return Json representation of {@code src}.
   */
  public String toJson(Object src) {
    if (src == null) {
      return toJson(JsonNull.INSTANCE);
    }
    return toJson(src, src.getClass());
  }

  /**
   * This method serializes the specified object, including those of generic types, into its
   * equivalent Json representation. This method must be used if the specified object is a generic
   * type. For non-generic objects, use {@link #toJson(Object)} instead. If you want to write out
   * the object to a {@link Appendable}, use {@link #toJson(Object, Type, Appendable)} instead.
   *
   * @param src the object for which JSON representation is to be created
   * @param typeOfSrc The specific genericized type of src. You can obtain
   * this type by using the {@link com.velocity.gson.reflect.TypeToken} class. For example,
   * to get the type for {@code Collection<Foo>}, you should use:
   * <pre>
   * Type typeOfSrc = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType();
   * </pre>
   * @return Json representation of {@code src}
   */
  public String toJson(Object src, Type typeOfSrc) {
    StringWriter writer = new StringWriter();
    toJson(toJsonTree(src, typeOfSrc), writer);
    return writer.toString();
  }

  /**
   * This method serializes the specified object into its equivalent Json representation.
   * This method should be used when the specified object is not a generic type. This method uses
   * {@link Class#getClass()} to get the type for the specified object, but the
   * {@code getClass()} loses the generic type information because of the Type Erasure feature
   * of Java. Note that this method works fine if the any of the object fields are of generic type,
   * just the object itself should not be of a generic type. If the object is of generic type, use
   * {@link #toJson(Object, Type, Appendable)} instead.
   *
   * @param src the object for which Json representation is to be created setting for Gson
   * @param writer Writer to which the Json representation needs to be written
   * @throws JsonIOException if there was a problem writing to the writer
   * @since 1.2
   */
  public void toJson(Object src, Appendable writer) throws JsonIOException {
    if (src != null) {
      toJson(src, src.getClass(), writer);
    } else {
      toJson(JsonNull.INSTANCE, writer);
    }
  }

  /**
   * This method serializes the specified object, including those of generic types, into its
   * equivalent Json representation. This method must be used if the specified object is a generic
   * type. For non-generic objects, use {@link #toJson(Object, Appendable)} instead.
   *
   * @param src the object for which JSON representation is to be created
   * @param typeOfSrc The specific genericized type of src. You can obtain
   * this type by using the {@link com.velocity.gson.reflect.TypeToken} class. For example,
   * to get the type for {@code Collection<Foo>}, you should use:
   * <pre>
   * Type typeOfSrc = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType();
   * </pre>
   * @param writer Writer to which the Json representation of src needs to be written.
   * @throws JsonIOException if there was a problem writing to the writer
   * @since 1.2
   */
  public void toJson(Object src, Type typeOfSrc, Appendable writer) throws JsonIOException {
    JsonElement jsonElement = toJsonTree(src, typeOfSrc);
    toJson(jsonElement, writer);
  }

  /**
   * Writes the JSON representation of {@code src} of type {@code typeOfSrc} to
   * {@code writer}.
   * @throws JsonIOException if there was a problem writing to the writer
   */
  public void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException {
    toJson(toJsonTree(src, typeOfSrc), writer);
  }

  /**
   * Converts a tree of {@link JsonElement}s into its equivalent JSON representation.
   *
   * @param jsonElement root of a tree of {@link JsonElement}s
   * @return JSON String representation of the tree
   * @since 1.4
   */
  public String toJson(JsonElement jsonElement) {
    StringWriter writer = new StringWriter();
    toJson(jsonElement, writer);
    return writer.toString();
  }

  /**
   * Writes out the equivalent JSON for a tree of {@link JsonElement}s.
   *
   * @param jsonElement root of a tree of {@link JsonElement}s
   * @param writer Writer to which the Json representation needs to be written
   * @throws JsonIOException if there was a problem writing to the writer
   * @since 1.4
   */
  public void toJson(JsonElement jsonElement, Appendable writer) throws JsonIOException {
    try {
      if (generateNonExecutableJson) {
        writer.append(JSON_NON_EXECUTABLE_PREFIX);
      }
      JsonWriter jsonWriter = new JsonWriter(Streams.writerForAppendable(writer));
      if (prettyPrinting) {
        jsonWriter.setIndent("  ");
      }
      toJson(jsonElement, jsonWriter);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Writes the JSON for {@code jsonElement} to {@code writer}.
   * @throws JsonIOException if there was a problem writing to the writer
   */
  public void toJson(JsonElement jsonElement, JsonWriter writer) throws JsonIOException {
    boolean oldLenient = writer.isLenient();
    writer.setLenient(true);
    boolean oldHtmlSafe = writer.isHtmlSafe();
    writer.setHtmlSafe(htmlSafe);
    try {
      Streams.write(jsonElement, serializeNulls, writer);
    } catch (IOException e) {
      throw new JsonIOException(e);
    } finally {
      writer.setLenient(oldLenient);
      writer.setHtmlSafe(oldHtmlSafe);
    }
  }

  /**
   * This method deserializes the specified Json into an object of the specified class. It is not
   * suitable to use if the specified class is a generic type since it will not have the generic
   * type information because of the Type Erasure feature of Java. Therefore, this method should not
   * be used if the desired type is a generic type. Note that this method works fine if the any of
   * the fields of the specified object are generics, just the object itself should not be a
   * generic type. For the cases when the object is of generic type, invoke
   * {@link #fromJson(String, Type)}. If you have the Json in a {@link Reader} instead of
   * a String, use {@link #fromJson(Reader, Class)} instead.
   *
   * @param <T> the type of the desired object
   * @param json the string from which the object is to be deserialized
   * @param classOfT the class of T
   * @return an object of type T from the string
   * @throws JsonSyntaxException if json is not a valid representation for an object of type
   * classOfT
   */
  public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
    Object object = fromJson(json, (Type) classOfT);
    return Primitives.wrap(classOfT).cast(object);
  }

  /**
   * This method deserializes the specified Json into an object of the specified type. This method
   * is useful if the specified object is a generic type. For non-generic objects, use
   * {@link #fromJson(String, Class)} instead. If you have the Json in a {@link Reader} instead of
   * a String, use {@link #fromJson(Reader, Type)} instead.
   *
   * @param <T> the type of the desired object
   * @param json the string from which the object is to be deserialized
   * @param typeOfT The specific genericized type of src. You can obtain this type by using the
   * {@link com.velocity.gson.reflect.TypeToken} class. For example, to get the type for
   * {@code Collection<Foo>}, you should use:
   * <pre>
   * Type typeOfT = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType();
   * </pre>
   * @return an object of type T from the string
   * @throws JsonParseException if json is not a valid representation for an object of type typeOfT
   * @throws JsonSyntaxException if json is not a valid representation for an object of type
   */
  @SuppressWarnings("unchecked")
  public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
    if (json == null) {
      return null;
    }
    StringReader reader = new StringReader(json);
    T target = (T) fromJson(reader, typeOfT);
    return target;
  }

  /**
   * This method deserializes the Json read from the specified reader into an object of the
   * specified class. It is not suitable to use if the specified class is a generic type since it
   * will not have the generic type information because of the Type Erasure feature of Java.
   * Therefore, this method should not be used if the desired type is a generic type. Note that
   * this method works fine if the any of the fields of the specified object are generics, just the
   * object itself should not be a generic type. For the cases when the object is of generic type,
   * invoke {@link #fromJson(Reader, Type)}. If you have the Json in a String form instead of a
   * {@link Reader}, use {@link #fromJson(String, Class)} instead.
   *
   * @param <T> the type of the desired object
   * @param json the reader producing the Json from which the object is to be deserialized.
   * @param classOfT the class of T
   * @return an object of type T from the string
   * @throws JsonIOException if there was a problem reading from the Reader
   * @throws JsonSyntaxException if json is not a valid representation for an object of type
   * @since 1.2
   */
  public <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
    JsonReader jsonReader = new JsonReader(json);
    Object object = fromJson(jsonReader, classOfT);
    assertFullConsumption(object, jsonReader);
    return Primitives.wrap(classOfT).cast(object);
  }

  /**
   * This method deserializes the Json read from the specified reader into an object of the
   * specified type. This method is useful if the specified object is a generic type. For
   * non-generic objects, use {@link #fromJson(Reader, Class)} instead. If you have the Json in a
   * String form instead of a {@link Reader}, use {@link #fromJson(String, Type)} instead.
   *
   * @param <T> the type of the desired object
   * @param json the reader producing Json from which the object is to be deserialized
   * @param typeOfT The specific genericized type of src. You can obtain this type by using the
   * {@link com.velocity.gson.reflect.TypeToken} class. For example, to get the type for
   * {@code Collection<Foo>}, you should use:
   * <pre>
   * Type typeOfT = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType();
   * </pre>
   * @return an object of type T from the json
   * @throws JsonIOException if there was a problem reading from the Reader
   * @throws JsonSyntaxException if json is not a valid representation for an object of type
   * @since 1.2
   */
  public <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
    JsonReader jsonReader = new JsonReader(json);
    T object = this.<T>fromJson(jsonReader, typeOfT);
    assertFullConsumption(object, jsonReader);
    return object;
  }

  private static void assertFullConsumption(Object obj, JsonReader reader) {
    try {
      if (obj != null && reader.peek() != JsonToken.END_DOCUMENT) {
        throw new JsonIOException("JSON document was not fully consumed.");
      }
    } catch (MalformedJsonException e) {
      throw new JsonSyntaxException(e);
    } catch (IOException e) {
      throw new JsonIOException(e);
    }
  }

  /**
   * Reads the next JSON value from {@code reader} and convert it to an object
   * of type {@code typeOfT}.
   * Since Type is not parameterized by T, this method is type unsafe and should be used carefully
   *
   * @throws JsonIOException if there was a problem writing to the Reader
   * @throws JsonSyntaxException if json is not a valid representation for an object of type
   */
  @SuppressWarnings("unchecked")
  public <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
    boolean oldLenient = reader.isLenient();
    reader.setLenient(true);
    try {
      JsonElement root = Streams.parse(reader);
      return (T) fromJson(root, typeOfT);
    } finally {
      reader.setLenient(oldLenient);
    }
  }

  /**
   * This method deserializes the Json read from the specified parse tree into an object of the
   * specified type. It is not suitable to use if the specified class is a generic type since it
   * will not have the generic type information because of the Type Erasure feature of Java.
   * Therefore, this method should not be used if the desired type is a generic type. Note that
   * this method works fine if the any of the fields of the specified object are generics, just the
   * object itself should not be a generic type. For the cases when the object is of generic type,
   * invoke {@link #fromJson(JsonElement, Type)}.
   * @param <T> the type of the desired object
   * @param json the root of the parse tree of {@link JsonElement}s from which the object is to
   * be deserialized
   * @param classOfT The class of T
   * @return an object of type T from the json
   * @throws JsonSyntaxException if json is not a valid representation for an object of type typeOfT
   * @since 1.3
   */
  public <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
    Object object = fromJson(json, (Type) classOfT);
    return Primitives.wrap(classOfT).cast(object);
  }

  /**
   * This method deserializes the Json read from the specified parse tree into an object of the
   * specified type. This method is useful if the specified object is a generic type. For
   * non-generic objects, use {@link #fromJson(JsonElement, Class)} instead.
   *
   * @param <T> the type of the desired object
   * @param json the root of the parse tree of {@link JsonElement}s from which the object is to
   * be deserialized
   * @param typeOfT The specific genericized type of src. You can obtain this type by using the
   * {@link com.velocity.gson.reflect.TypeToken} class. For example, to get the type for
   * {@code Collection<Foo>}, you should use:
   * <pre>
   * Type typeOfT = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType();
   * </pre>
   * @return an object of type T from the json
   * @throws JsonSyntaxException if json is not a valid representation for an object of type typeOfT
   * @since 1.3
   */
  @SuppressWarnings("unchecked")
  public <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
    if (json == null) {
      return null;
    }
    JsonDeserializationContext context = new JsonDeserializationContext(
        new ObjectNavigator(deserializationExclusionStrategy), fieldNamingPolicy,
        deserializers, objectConstructor);
    T target = (T) context.deserialize(json, typeOfT);
    return target;
  }

  @Override
  public String toString() {
  	StringBuilder sb = new StringBuilder("{")
  	    .append("serializeNulls:").append(serializeNulls)
  	    .append(",serializers:").append(serializers)
  	    .append(",deserializers:").append(deserializers)

      	// using the name instanceCreator instead of ObjectConstructor since the users of Gson are
      	// more familiar with the concept of Instance Creators. Moreover, the objectConstructor is
      	// just a utility class around instance creators, and its toString() only displays them.
        .append(",instanceCreators:").append(objectConstructor)
        .append("}");
  	return sb.toString();
  }
}
