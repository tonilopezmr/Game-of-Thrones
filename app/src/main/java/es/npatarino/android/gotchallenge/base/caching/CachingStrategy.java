package es.npatarino.android.gotchallenge.base.caching;

public interface CachingStrategy<T> {
  boolean isValid(T value);
}
