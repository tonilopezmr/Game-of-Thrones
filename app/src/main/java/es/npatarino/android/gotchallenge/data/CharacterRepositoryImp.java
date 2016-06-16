package es.npatarino.android.gotchallenge.data;

import android.util.Log;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.datasource.local.CharacterLocalDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.CharacterRemoteDataSource;
import es.npatarino.android.gotchallenge.domain.repository.CharacterRepository;
import rx.Observable;

public class CharacterRepositoryImp implements CharacterRepository {

    public static final String TAG = CharacterRepositoryImp.class.getSimpleName();
    private CharacterRemoteDataSource remoteDataSource;
    private CharacterLocalDataSource localDataSource;

    public CharacterRepositoryImp(CharacterRemoteDataSource remoteDataSource, CharacterLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public Observable<List<GoTCharacter>> getList(){
        Log.i(TAG, "getList()");
        Observable<List<GoTCharacter>> observable;

        if (localDataSource.isExpired()){
            observable = remoteDataSource.getAll()
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
            observable = remoteDataSource.read(entity)
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
    public Observable<List<GoTCharacter>> read(House house){
        Observable<List<GoTCharacter>> observable;

        if (localDataSource.isExpired()){
            observable = remoteDataSource.read(house)
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
