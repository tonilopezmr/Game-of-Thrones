package es.npatarino.android.gotchallenge.common.caching;

public interface CachingStrategy<T> {
    boolean isValid(T value);
}
