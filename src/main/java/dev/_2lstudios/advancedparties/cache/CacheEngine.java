package dev._2lstudios.advancedparties.cache;

public interface CacheEngine {
    public String get(String key);
    public void set(String key, int expiration, String value);
    public void delete(String key);
}
