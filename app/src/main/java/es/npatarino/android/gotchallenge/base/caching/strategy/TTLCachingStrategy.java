package es.npatarino.android.gotchallenge.base.caching.strategy;

import java.util.concurrent.TimeUnit;

import es.npatarino.android.gotchallenge.base.caching.CachingStrategy;

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
