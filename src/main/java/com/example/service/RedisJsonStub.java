package com.example.service;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.providers.PooledConnectionProvider;

import java.util.List;

public class RedisJsonStub {
  private final UnifiedJedis client;

  private static final RedisJsonStub instance = new RedisJsonStub();

  private RedisJsonStub() {
    HostAndPort config = new HostAndPort(Protocol.DEFAULT_HOST, 6379);
    PooledConnectionProvider provider = new PooledConnectionProvider(config);
    client = new UnifiedJedis(provider);
  }

  public static RedisJsonStub getInstance() {
    return instance;
  }

  public void store(String key, String jsonValue, String etag) {
    client.rpush(key, jsonValue, etag);
  }

  public List<String> query(String key) {
    return client.lrange(key, 0, -1);
  }

  public boolean remove(String key) {
    return client.del(key) > 0;
  }

  public static void main(String[] args) {
    RedisJsonStub instance = getInstance();
    instance.store("list1", "A", "B");
  }
}
