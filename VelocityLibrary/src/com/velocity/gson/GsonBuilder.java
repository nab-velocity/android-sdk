package com.velocity.gson;

import com.velocity.gson.DefaultTypeAdapters.DefaultDateTypeAdapter;
import com.velocity.gson.internal.$Gson$Preconditions;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ranjitk
 */
public final class GsonBuilder {
  private static final MapAsArrayTypeAdapter COMPLEX_KEY_MAP_TYPE_ADAPTER =
      new MapAsArrayTypeAdapter();
  private static final InnerClassExclusionStrategy innerClassExclusionStrategy =
      new InnerClassExclusionStrategy();
  private static final ExposeAnnotationDeserializationExclusionStrategy
  exposeAnnotationDeserializationExclusionStrategy =
      new ExposeAnnotationDeserializationExclusionStrategy();
  private static final ExposeAnnotationSerializationExclusionStrategy
  exposeAnnotationSerializationExclusionStrategy =
      new ExposeAnnotationSerializationExclusionStrategy();

  private final Set<ExclusionStrategy> serializeExclusionStrategies =
      new HashSet<ExclusionStrategy>();
  private final Set<ExclusionStrategy> deserializeExclusionStrategies =
      new HashSet<ExclusionStrategy>();

  private double ignoreVersionsAfter;
  private ModifierBasedExclusionStrategy modifierBasedExclusionStrategy;
  private boolean serializeInnerClasses;
  private boolean excludeFieldsWithoutExposeAnnotation;
  private LongSerializationPolicy longSerializationPolicy;
  private FieldNamingStrategy2 fieldNamingPolicy;
  private final ParameterizedTypeHandlerMap<InstanceCreator<?>> instanceCreators;
  private final ParameterizedTypeHandlerMap<JsonSerializer<?>> serializers;
  private final ParameterizedTypeHandlerMap<JsonDeserializer<?>> deserializers;
  private boolean serializeNulls;
  private String datePattern;
  private int dateStyle;
  private int timeStyle;
  private boolean serializeSpecialFloatingPointValues;
  private boolean escapeHtmlChars;
  private boolean prettyPrinting;
  private boolean generateNonExecutableJson;

  /**
   * Creates a GsonBuilder instance that can be used to build Gson with various configuration
   * settings. GsonBuilder follows the builder pattern, and it is typically used by first
   * invoking various configuration methods to set desired options, and finally calling
   * {@link #create()}.
   */
  public GsonBuilder() {
    // add default exclusion strategies
    deserializeExclusionStrategies.add(Gson.DEFAULT_ANON_LOCAL_CLASS_EXCLUSION_STRATEGY);
    deserializeExclusionStrategies.add(Gson.DEFAULT_SYNTHETIC_FIELD_EXCLUSION_STRATEGY);
    serializeExclusionStrategies.add(Gson.DEFAULT_ANON_LOCAL_CLASS_EXCLUSION_STRATEGY);
    serializeExclusionStrategies.add(Gson.DEFAULT_SYNTHETIC_FIELD_EXCLUSION_STRATEGY);

    // setup default values
    ignoreVersionsAfter = VersionConstants.IGNORE_VERSIONS;
    serializeInnerClasses = true;
    prettyPrinting = false;
    escapeHtmlChars = true;
    modifierBasedExclusionStrategy = Gson.DEFAULT_MODIFIER_BASED_EXCLUSION_STRATEGY;
    excludeFieldsWithoutExposeAnnotation = false;
    longSerializationPolicy = LongSerializationPolicy.DEFAULT;
    fieldNamingPolicy = Gson.DEFAULT_NAMING_POLICY;
    instanceCreators = new ParameterizedTypeHandlerMap<InstanceCreator<?>>();
    serializers = new ParameterizedTypeHandlerMap<JsonSerializer<?>>();
    deserializers = new ParameterizedTypeHandlerMap<JsonDeserializer<?>>();
    serializeNulls = false;
    dateStyle = DateFormat.DEFAULT;
    timeStyle = DateFormat.DEFAULT;
    serializeSpecialFloatingPointValues = false;
    generateNonExecutableJson = false;
  }

  /**
   * Configures Gson to enable versioning support.
   *
   * @param ignoreVersionsAfter any field or type marked with a version higher than this value
   * are ignored during serialization or deserialization.
   * @return a reference to this {@code GsonBuilder} object to fulfill the "Builder" pattern
   */
  public GsonBuilder setVersion(double ignoreVersionsAfter) {
    this.ignoreVersionsAfter = ignoreVersionsAfter;
    return this;
  }

  /**
   * Configures Gson to excludes all class fields that have the specified modifiers. By default,
   * Gson will exclude all fields marked transient or static. This method will override that
   * behavior.
   *
   * @param modifiers the field modifiers. You must use the modifiers specified in the
   * {@link java.lang.reflect.Modifier} class. For example,
   * {@link java.lang.reflect.Modifier#TRANSIENT},
   * {@link java.lang.reflect.Modifier#STATIC}.
   * @return a reference to this {@code GsonBuilder} object to fulfill the "Builder" pattern
   */
  public GsonBuilder excludeFieldsWithModifiers(int... modifiers) {
    modifierBasedExclusionStrategy = new ModifierBasedExclusionStrategy(modifiers);
    return this;
  }

  /**
   * Makes the output JSON non-executable in Javascript by prefixing the generated JSON with some
   * special text. This prevents attacks from third-party sites through script sourcing. See
   * <a href="http://code.google.com/p/google-gson/issues/detail?id=42">Gson Issue 42</a>
   * for details.
   *
   * @return a reference to this {@code GsonBuilder} object to fulfill the "Builder" pattern
   * @since 1.3
   */
  public GsonBuilder generateNonExecutableJson() {
    this.generateNonExecutableJson = true;
    return this;
  }

  /**
   * Configures Gson to exclude all fields from consideration for serialization or deserialization
   * that do not have the {@link com.velocity.gson.annotations.Expose} annotation.
   *
   * @return a reference to this {@code GsonBuilder} object to fulfill the "Builder" pattern
   */
  public GsonBuilder excludeFieldsWithoutExposeAnnotation() {
    excludeFieldsWithoutExposeAnnotation = true;
    return this;
  }

  /**
   * Configure Gson to serialize null fields. By default, Gson omits all fields that are null
   * during serialization.
   *
   * @return a reference to this {@code GsonBuilder} object to fulfill the "Builder" pattern
   * @since 1.2
   */
  public GsonBuilder serializeNulls() {
    this.serializeNulls = true;
    return this;
  }

    public GsonBuilder enableComplexMapKeySerialization() {
    registerTypeHierarchyAdapter(Map.class, COMPLEX_KEY_MAP_TYPE_ADAPTER);
    return this;
  }

  public GsonBuilder disableInnerClassSerialization() {
    serializeInnerClasses = false;
    return this;
  }

 
  public GsonBuilder setLongSerializationPolicy(LongSerializationPolicy serializationPolicy) {
    this.longSerializationPolicy = serializationPolicy;
    return this;
  }


  public GsonBuilder setFieldNamingPolicy(FieldNamingPolicy namingConvention) {
    return setFieldNamingStrategy(namingConvention.getFieldNamingPolicy());
  }


  public GsonBuilder setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
    return setFieldNamingStrategy(new FieldNamingStrategy2Adapter(fieldNamingStrategy));
  }

  /**
   * Configures Gson to apply a specific naming policy strategy to an object's field during
   * serialization and deserialization.
   *
   * @param fieldNamingStrategy the actual naming strategy to apply to the fields
   * @return a reference to this {@code GsonBuilder} object to fulfill the "Builder" pattern
   */
  GsonBuilder setFieldNamingStrategy(FieldNamingStrategy2 fieldNamingStrategy) {
    this.fieldNamingPolicy =
        new SerializedNameAnnotationInterceptingNamingPolicy(fieldNamingStrategy);
    return this;
  }


  public GsonBuilder setExclusionStrategies(ExclusionStrategy... strategies) {
    List<ExclusionStrategy> strategyList = Arrays.asList(strategies);
    serializeExclusionStrategies.addAll(strategyList);
    deserializeExclusionStrategies.addAll(strategyList);
    return this;
  }


  public GsonBuilder addSerializationExclusionStrategy(ExclusionStrategy strategy) {
    serializeExclusionStrategies.add(strategy);
    return this;
  }


  public GsonBuilder addDeserializationExclusionStrategy(ExclusionStrategy strategy) {
    deserializeExclusionStrategies.add(strategy);
    return this;
  }

  public GsonBuilder setPrettyPrinting() {
    prettyPrinting = true;
    return this;
  }


  public GsonBuilder disableHtmlEscaping() {
    this.escapeHtmlChars = false;
    return this;
  }

  public GsonBuilder setDateFormat(String pattern) {
    // TODO(Joel): Make this fail fast if it is an invalid date format
    this.datePattern = pattern;
    return this;
  }

  
  public GsonBuilder setDateFormat(int style) {
    this.dateStyle = style;
    this.datePattern = null;
    return this;
  }

 
  public GsonBuilder setDateFormat(int dateStyle, int timeStyle) {
    this.dateStyle = dateStyle;
    this.timeStyle = timeStyle;
    this.datePattern = null;
    return this;
  }


  public GsonBuilder registerTypeAdapter(Type type, Object typeAdapter) {
    return registerTypeAdapter(type, typeAdapter, false);
  }

  private GsonBuilder registerTypeAdapter(Type type, Object typeAdapter, boolean isSystem) {
    $Gson$Preconditions.checkArgument(typeAdapter instanceof JsonSerializer<?>
        || typeAdapter instanceof JsonDeserializer<?> || typeAdapter instanceof InstanceCreator<?>);
    if (typeAdapter instanceof InstanceCreator<?>) {
      registerInstanceCreator(type, (InstanceCreator<?>) typeAdapter, isSystem);
    }
    if (typeAdapter instanceof JsonSerializer<?>) {
      registerSerializer(type, (JsonSerializer<?>) typeAdapter, isSystem);
    }
    if (typeAdapter instanceof JsonDeserializer<?>) {
      registerDeserializer(type, (JsonDeserializer<?>) typeAdapter, isSystem);
    }
    return this;
  }


  private <T> GsonBuilder registerInstanceCreator(Type typeOfT,
      InstanceCreator<? extends T> instanceCreator, boolean isSystem) {
    instanceCreators.register(typeOfT, instanceCreator, isSystem);
    return this;
  }

 
  private <T> GsonBuilder registerSerializer(Type typeOfT, JsonSerializer<T> serializer,
      boolean isSystem) {
    serializers.register(typeOfT, serializer, isSystem);
    return this;
  }


  private <T> GsonBuilder registerDeserializer(Type typeOfT, JsonDeserializer<T> deserializer,
      boolean isSystem) {
    deserializers.register(typeOfT, new JsonDeserializerExceptionWrapper<T>(deserializer), isSystem);
    return this;
  }

  
  public GsonBuilder registerTypeHierarchyAdapter(Class<?> baseType, Object typeAdapter) {
    return registerTypeHierarchyAdapter(baseType, typeAdapter, false);
  }

  private GsonBuilder registerTypeHierarchyAdapter(Class<?> baseType, Object typeAdapter,
      boolean isSystem) {
    $Gson$Preconditions.checkArgument(typeAdapter instanceof JsonSerializer<?>
        || typeAdapter instanceof JsonDeserializer<?> || typeAdapter instanceof InstanceCreator<?>);
    if (typeAdapter instanceof InstanceCreator<?>) {
      registerInstanceCreatorForTypeHierarchy(baseType, (InstanceCreator<?>) typeAdapter, isSystem);
    }
    if (typeAdapter instanceof JsonSerializer<?>) {
      registerSerializerForTypeHierarchy(baseType, (JsonSerializer<?>) typeAdapter, isSystem);
    }
    if (typeAdapter instanceof JsonDeserializer<?>) {
      registerDeserializerForTypeHierarchy(baseType, (JsonDeserializer<?>) typeAdapter, isSystem);
    }
    return this;
  }

  private <T> GsonBuilder registerInstanceCreatorForTypeHierarchy(Class<?> classOfT,
      InstanceCreator<? extends T> instanceCreator, boolean isSystem) {
    instanceCreators.registerForTypeHierarchy(classOfT, instanceCreator, isSystem);
    return this;
  }

  private <T> GsonBuilder registerSerializerForTypeHierarchy(Class<?> classOfT,
      JsonSerializer<T> serializer, boolean isSystem) {
    serializers.registerForTypeHierarchy(classOfT, serializer, isSystem);
    return this;
  }

  private <T> GsonBuilder registerDeserializerForTypeHierarchy(Class<?> classOfT,
      JsonDeserializer<T> deserializer, boolean isSystem) {
    deserializers.registerForTypeHierarchy(classOfT,
        new JsonDeserializerExceptionWrapper<T>(deserializer), isSystem);
    return this;
  }

 
  public GsonBuilder serializeSpecialFloatingPointValues() {
    this.serializeSpecialFloatingPointValues = true;
    return this;
  }

  /**
   * Creates a {@link Gson} instance based on the current configuration. This method is free of
   * side-effects to this {@code GsonBuilder} instance and hence can be called multiple times.
   *
   * @return an instance of Gson configured with the options currently set in this builder
   */
  public Gson create() {
    List<ExclusionStrategy> deserializationStrategies =
        new LinkedList<ExclusionStrategy>(deserializeExclusionStrategies);
    List<ExclusionStrategy> serializationStrategies =
        new LinkedList<ExclusionStrategy>(serializeExclusionStrategies);
    deserializationStrategies.add(modifierBasedExclusionStrategy);
    serializationStrategies.add(modifierBasedExclusionStrategy);

    if (!serializeInnerClasses) {
      deserializationStrategies.add(innerClassExclusionStrategy);
      serializationStrategies.add(innerClassExclusionStrategy);
    }
    if (ignoreVersionsAfter != VersionConstants.IGNORE_VERSIONS) {
      VersionExclusionStrategy versionExclusionStrategy =
          new VersionExclusionStrategy(ignoreVersionsAfter);
      deserializationStrategies.add(versionExclusionStrategy);
      serializationStrategies.add(versionExclusionStrategy);
    }
    if (excludeFieldsWithoutExposeAnnotation) {
      deserializationStrategies.add(exposeAnnotationDeserializationExclusionStrategy);
      serializationStrategies.add(exposeAnnotationSerializationExclusionStrategy);
    }

    ParameterizedTypeHandlerMap<JsonSerializer<?>> customSerializers =
        DefaultTypeAdapters.DEFAULT_HIERARCHY_SERIALIZERS.copyOf();
    customSerializers.register(serializers.copyOf());
    ParameterizedTypeHandlerMap<JsonDeserializer<?>> customDeserializers =
        DefaultTypeAdapters.DEFAULT_HIERARCHY_DESERIALIZERS.copyOf();
    customDeserializers.register(deserializers.copyOf());
    addTypeAdaptersForDate(datePattern, dateStyle, timeStyle, customSerializers,
        customDeserializers);

    customDeserializers.registerIfAbsent(DefaultTypeAdapters.getDefaultDeserializers());

    ParameterizedTypeHandlerMap<InstanceCreator<?>> customInstanceCreators =
        instanceCreators.copyOf();
    customInstanceCreators.registerIfAbsent(DefaultTypeAdapters.getDefaultInstanceCreators());

    customSerializers.makeUnmodifiable();
    customDeserializers.makeUnmodifiable();
    instanceCreators.makeUnmodifiable();

    MappedObjectConstructor objConstructor = new MappedObjectConstructor(customInstanceCreators);

    return new Gson(new DisjunctionExclusionStrategy(deserializationStrategies),
        new DisjunctionExclusionStrategy(serializationStrategies),
        fieldNamingPolicy, objConstructor, serializeNulls,
        customSerializers, customDeserializers, generateNonExecutableJson, escapeHtmlChars,
        prettyPrinting, serializeSpecialFloatingPointValues, longSerializationPolicy);
  }

  private static void addTypeAdaptersForDate(String datePattern, int dateStyle, int timeStyle,
      ParameterizedTypeHandlerMap<JsonSerializer<?>> serializers,
      ParameterizedTypeHandlerMap<JsonDeserializer<?>> deserializers) {
    DefaultDateTypeAdapter dateTypeAdapter = null;
    if (datePattern != null && !"".equals(datePattern.trim())) {
      dateTypeAdapter = new DefaultDateTypeAdapter(datePattern);
    } else if (dateStyle != DateFormat.DEFAULT && timeStyle != DateFormat.DEFAULT) {
      dateTypeAdapter = new DefaultDateTypeAdapter(dateStyle, timeStyle);
    }

    if (dateTypeAdapter != null) {
      registerIfAbsent(Date.class, serializers, dateTypeAdapter);
      registerIfAbsent(Date.class, deserializers, dateTypeAdapter);
      registerIfAbsent(Timestamp.class, serializers, dateTypeAdapter);
      registerIfAbsent(Timestamp.class, deserializers, dateTypeAdapter);
      registerIfAbsent(java.sql.Date.class, serializers, dateTypeAdapter);
      registerIfAbsent(java.sql.Date.class, deserializers, dateTypeAdapter);
    }
  }

  private static <T> void registerIfAbsent(Class<?> type,
      ParameterizedTypeHandlerMap<T> adapters, T adapter) {
    if (!adapters.hasSpecificHandlerFor(type)) {
      adapters.register(type, adapter, false);
    }
  }
}
