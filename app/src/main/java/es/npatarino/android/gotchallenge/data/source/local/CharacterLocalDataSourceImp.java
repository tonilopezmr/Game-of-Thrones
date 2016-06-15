package es.npatarino.android.gotchallenge.data.source.local;

import android.support.annotation.NonNull;

import java.util.Arrays;
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
        GoTCharacter goTCharacter = getGoTCharacter();
        return Observable.just(goTCharacter);
    }

    @NonNull
    private GoTCharacter getGoTCharacter() {
        GoTCharacter goTCharacter = new GoTCharacter();
        goTCharacter.setName("Tonilopezmr");
        goTCharacter.setDescription("A great warrior");
        goTCharacter.setHouseId("someId");
        goTCharacter.setHouseImageUrl("https://avatars3.githubusercontent.com/u/5845622?v=3&s=460");
        return goTCharacter;
    }

    @Override
    public Observable<List<GoTCharacter>> read(House house) {
        return Observable.just(Arrays.asList(getGoTCharacter(), getGoTCharacter()));
    }

    @Override
    public Observable<List<GoTCharacter>> getAll() {
        return Observable.just(Arrays.asList(getGoTCharacter(), getGoTCharacter(), getGoTCharacter(), getGoTCharacter()));
    }
}
