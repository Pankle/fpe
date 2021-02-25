package com.anji.src.fpe.config;

import java.util.*;

/**
 * Created by Pank on 2020/06/01
 */
public class ExpireMap<K, V> extends HashMap<K, V> {


    private long EXPIRY = 1000 * 60 * 2;
    private HashMap<K, Long> expiryMap = new HashMap<K, Long>();

    public ExpireMap() {
        super();
    }

    public ExpireMap(long defaultExpiryTime) {
        this(1 << 4, defaultExpiryTime);
    }

    public ExpireMap(int initialCapacity, long defaultExpiryTime) {
        super(initialCapacity);
        this.EXPIRY = defaultExpiryTime;
    }

    @Override
    public V put(K key, V value) {
        expiryMap.put(key, System.currentTimeMillis() + EXPIRY);
        return super.put(key, value);
    }

    @Override
    public boolean containsKey(Object key) {
        return !checkExpiry(key, true) && super.containsKey(key);
    }

    public V put(K key, V value, long expiryTime) {
        expiryMap.put(key, System.currentTimeMillis() + expiryTime);
        return super.put(key, value);
    }

    @Override
    public int size() {
        return entrySet().size();
    }

    @Override
    public boolean isEmpty() {
        return entrySet().size() == 0;
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null) {
            return Boolean.FALSE;
        }
        Set<Entry<K, V>> set = super.entrySet();
        Iterator<Entry<K, V>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (value.equals(entry.getValue())) {
                if (checkExpiry(entry.getKey(), false)) {
                    iterator.remove();
                    return Boolean.FALSE;
                } else {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public Collection<V> values() {

        Collection<V> values = super.values();

        if (values == null || values.size() < 1) {
            return values;
        }

        Iterator<V> iterator = values.iterator();

        while (iterator.hasNext()) {
            V next = iterator.next();
            if (!containsValue(next)) {
                iterator.remove();
            }
        }
        return values;
    }

    @Override
    public V get(Object key) {
        if (key == null) {
            return null;
        }
        if (checkExpiry(key, true)) {
            return null;
        }
        return super.get(key);
    }


    public Object isInvalid(Object key) {
        if (key == null) {
            return null;
        }
        if (!expiryMap.containsKey(key)) {
            return null;
        }
        long expiryTime = expiryMap.get(key);

        boolean flag = System.currentTimeMillis() > expiryTime;

        if (flag) {
            super.remove(key);
            expiryMap.remove(key);
            return -1;
        }
        return super.get(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> e : m.entrySet()) {
            expiryMap.put(e.getKey(), System.currentTimeMillis() + EXPIRY);
        }
        super.putAll(m);
    }


    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = super.entrySet();
        Iterator<Entry<K, V>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (checkExpiry(entry.getKey(), false)) {
                iterator.remove();
            }
        }

        return set;
    }

    private boolean checkExpiry(Object key, boolean isRemoveSuper) {

        if (!expiryMap.containsKey(key)) {
            return Boolean.FALSE;
        }
        long expiryTime = expiryMap.get(key);

        boolean flag = System.currentTimeMillis() > expiryTime;

        if (flag) {
            if (isRemoveSuper) {
                super.remove(key);
            }
            expiryMap.remove(key);
        }
        return flag;
    }


    public boolean push(K key, V value, long expiryTime) {
        if (containsKey(key)) {
            return Boolean.FALSE.booleanValue();
        } else {
            put(key, value, expiryTime);
            return Boolean.TRUE.booleanValue();
        }
    }

    public void pop(K key) {
        remove(key);
    }

    public static void main(String[] args) throws InterruptedException {
        ExpireMap<String, String> map = new ExpireMap<String, String>(10);
        map.put("test", "ankang");
        map.put("test1", "ankang");
        map.put("test2", "ankang", 6000);
        System.out.println("test1" + map.get("test"));
        while (true) {
            Thread.sleep(2000);
            System.out.println("isInvalid:" + map.isInvalid("test"));
            System.out.println("size:" + map.size());
            System.out.println("size:" + ((HashMap<String, String>) map).size());
            for (Entry<String, String> m : map.entrySet()) {
                System.out.println("isInvalid:" + map.isInvalid(m.getKey()));
                map.containsKey(m.getKey());
                System.out.println("key:" + m.getKey() + "     value:" + m.getValue());
            }
            System.out.println("test1" + map.get("test"));
            System.out.println("test2" + map.get("test2"));
        }
    }
}
