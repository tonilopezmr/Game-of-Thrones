package es.npatarino.android.gotchallenge.data.source.local;

import java.util.List;

import es.npatarino.android.gotchallenge.data.caching.TimeProvider;
import es.npatarino.android.gotchallenge.data.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.datasource.local.CharacterLocalDataSource;
import rx.Observable;

public class CharacterLocalDataSourceImp implements CharacterLocalDataSource{

    private TTLCachingStrategy ttlCachingStrategy;
    private TimeProvider timeProvider;

    public CharacterLocalDataSourceImp(TTLCachingStrategy ttlCachingStrategy, TimeProvider timeProvider) {
        this.ttlCachingStrategy = ttlCachingStrategy;
        this.timeProvider = timeProvider;
    }

    @Override
    public void save(List<GoTCharacter> save) {

    }

    @Override
    public boolean isExpired() {
        return true;
    }

    @Override
    public void removeAll(List<GoTCharacter> remove) {

    }

    @Override
    public Observable<GoTCharacter> read(GoTCharacter entity) {
        return null;
    }

    @Override
    public Observable<List<GoTCharacter>> read(House house) {
        return null;
    }

    @Override
    public Observable<List<GoTCharacter>> getAll() {
        return null;
    }
}
