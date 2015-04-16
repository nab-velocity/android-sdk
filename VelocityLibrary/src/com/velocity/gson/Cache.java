package com.velocity.gson;

/**
 * Defines generic cache interface.
 *
 * @author Ranjitk
 * 
 */
interface Cache<K, V> {

  /**
   * Adds the new value object into the cache for the given key.  If the key already
   * exists, then this method will override the value for the key.
   *
   * @param key the key identifier for the {@code value} object
   * @param value the value object to store in the cache
   */
  void addElement(K key, V value);

  /**
   * Retrieve the cached value for the given {@code key}.
   *
   * @param key the key identifying the value
   * @return the cached value for the given {@code key}
   */
  V getElement(K key);

  /**
   * Removes the value from the cache for the given key.
   *
   * @param key the key identifying the value to remove
   * @return the value for the given {@code key}
   */
  V removeElement(K key);
}
