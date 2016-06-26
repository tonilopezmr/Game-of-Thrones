package es.npatarino.android.gotchallenge.characters.data;

import android.util.Log;

import java.util.List;

import es.npatarino.android.gotchallenge.characters.domain.CharactersDomain;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import rx.Observable;

public class CharacterRepository implements CharactersDomain.Repository {

    public static final String TAG = CharacterRepository.class.getSimpleName();
    private CharactersDomain.NetworkDataSource networkDataSource;
    private CharactersDomain.LocalDataSource localDataSource;

    public CharacterRepository(CharactersDomain.NetworkDataSource networkDataSource, CharactersDomain.LocalDataSource localDataSource) {
        this.networkDataSource = networkDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public Observable<List<GoTCharacter>> getList() {
        Log.i(TAG, "getList()");
        Observable<List<GoTCharacter>> observable;

        if (localDataSource.isExpired()){
            observable = networkDataSource.getAll()
                    .doOnNext(goTCharacters -> {
                        Log.i(TAG, "will save on cache");
                        localDataSource.removeAll(goTCharacters);
                        localDataSource.save(goTCharacters);
                        Log.i(TAG, "cached network in memory");
                    })
                    .retry((attempts, error) -> error instanceof Exception && attempts < 2)
                    .onErrorResumeNext(throwable -> {
                        Log.i(TAG, "some network error, return cache");
                        return localDataSource.getAll();
                    });
            Log.i(TAG, "fetching from network");
        }else {
            observable = localDataSource.getAll();
            Log.i(TAG, "fetching from local");
        }

        return observable;
    }

    @Override
    public Observable<GoTCharacter> read(GoTCharacter entity){
        Observable<GoTCharacter> observable;

        if (localDataSource.isExpired()){
            observable = networkDataSource.read(entity)
                    .retry((attempts, error) -> error instanceof Exception && attempts < 2)
                    .onErrorResumeNext(throwable -> {
                        return localDataSource.read(entity);
                    });
        }else {
            observable = localDataSource.read(entity);
        }

        return observable;
    }

    @Override
    public Observable<List<GoTCharacter>> read(GoTHouse house){
        Observable<List<GoTCharacter>> observable;

        if (localDataSource.isExpired()){
            observable = networkDataSource.read(house)
                    .retry((attempts, error) -> error instanceof Exception && attempts < 2)
                    .onErrorResumeNext(throwable -> {
                        return localDataSource.read(house);
                    });
        }else {
            observable = localDataSource.read(house);
        }

        return observable;
    }

}
