package com.example.rickmotyapi.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TTLCache<K, V> {
    private final Map<K, CacheItem<V>> cache;
    private final long ttlMillis;
    private final ScheduledExecutorService scheduler;
    private final Lock lock = new ReentrantLock();

    // Constructor: ttlMillis is the Time-To-Live in milliseconds
    public TTLCache(long ttlMillis) {
        this.cache = new HashMap<>();
        this.ttlMillis = ttlMillis;
        this.scheduler = Executors.newScheduledThreadPool(1);
        
        // Periodic cleanup task
        scheduler.scheduleAtFixedRate(this::cleanup, ttlMillis, ttlMillis, TimeUnit.MILLISECONDS);
    }

    // Cache item wrapper that holds value and timestamp
    private static class CacheItem<V> {
        V value;
        long timestamp;

        CacheItem(V value) {
            this.value = value;
            this.timestamp = System.currentTimeMillis();
        }
    }

    // Put a value into the cache
    public void put(K key, V value) {
        lock.lock();
        try {
            cache.put(key, new CacheItem<>(value));
        } finally {
            lock.unlock();
        }
    }

    // Get a value from the cache
    public V get(K key) {
        lock.lock();
        try {
            CacheItem<V> item = cache.get(key);
            if (item == null || isExpired(item)) {
                cache.remove(key);  // Remove expired or non-existent item
                return null;
            }
            return item.value;
        } finally {
            lock.unlock();
        }
    }

    // Check if an item has expired
    private boolean isExpired(CacheItem<V> item) {
        return System.currentTimeMillis() - item.timestamp > ttlMillis;
    }

    // Cleanup expired items periodically
    private void cleanup() {
        lock.lock();
        try {
            Iterator<Map.Entry<K, CacheItem<V>>> iterator = cache.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<K, CacheItem<V>> entry = iterator.next();
                if (isExpired(entry.getValue())) {
                    iterator.remove();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    // Close the scheduler
    public void shutdown() {
        scheduler.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        TTLCache<String, String> ttlCache = new TTLCache<>(3000); // TTL is 3 seconds
        
        ttlCache.put("key1", "value1");
        System.out.println("Stored key1");

        // Wait for 2 seconds and try to get the value
        Thread.sleep(2000);
        System.out.println("key1: " + ttlCache.get("key1"));

        // Wait for another 2 seconds and try to get the value again (should be expired)
        Thread.sleep(2000);
        System.out.println("key1: " + ttlCache.get("key1"));

        ttlCache.shutdown();
    }
}
