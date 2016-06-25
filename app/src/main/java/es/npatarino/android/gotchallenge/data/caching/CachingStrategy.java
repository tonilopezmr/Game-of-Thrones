package es.npatarino.android.gotchallenge.data.caching;

public interface CachingStrategy<T> {
    boolean isValid(T value);
}
