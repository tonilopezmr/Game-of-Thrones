package es.npatarino.android.gotchallenge.base.caching.strategy;

import es.npatarino.android.gotchallenge.base.caching.CachingStrategy;

import java.util.concurrent.TimeUnit;

public class TTLCachingStrategy implements CachingStrategy<Long> {

  private final long ttlMillis;

  public TTLCachingStrategy(long ttl, TimeUnit timeUnit) {
    this.ttlMillis = timeUnit.toMillis(ttl);
  }


  @Override
  public boolean isValid(Long value) {
    return (value + ttlMillis) > System.currentTimeMillis();
  }
}
