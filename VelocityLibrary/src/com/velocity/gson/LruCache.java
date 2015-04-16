package com.velocity.gson;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ranjitk
 */
final class LruCache<K, V> extends LinkedHashMap<K, V> implements Cache<K, V> {
  private static final long serialVersionUID = 1L;

  private final int maxCapacity;

  public LruCache(int maxCapacity) {
    super(maxCapacity, 0.7F, true);
    this.maxCapacity = maxCapacity;
  }

  public synchronized void addElement(K key, V value) {
    put(key, value);
  }

  public synchronized V getElement(K key) {
    return get(key);
  }

  public synchronized V removeElement(K key) {
    return remove(key);
  }

  @Override
  protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
    return size() > maxCapacity;
  }
}
