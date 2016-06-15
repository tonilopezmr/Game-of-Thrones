package es.npatarino.android.gotchallenge.data;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.datasource.local.CharacterLocalDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.CharacterRemoteDataSource;
import es.npatarino.android.gotchallenge.domain.repository.CharacterRepository;
import rx.Observable;

public class CharacterRepositoryImp implements CharacterRepository {

    private CharacterRemoteDataSource remoteDataSource;
    private CharacterLocalDataSource localDataSource;

    public CharacterRepositoryImp(CharacterRemoteDataSource remoteDataSource, CharacterLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public Observable<List<GoTCharacter>> getList(){
        Observable<List<GoTCharacter>> observable;

        if (localDataSource.isExpired()){
            observable = remoteDataSource.getAll()
                    .doOnNext(goTCharacters -> {
                        localDataSource.removeAll(goTCharacters);
                        localDataSource.save(goTCharacters);
                    })
                    .retry((attempts, error) -> error instanceof Exception && attempts < 2)
                    .onErrorResumeNext(throwable -> {
                        return localDataSource.getAll();
                    });
        }else {
            observable = localDataSource.getAll();
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
                    });;
        }else {
            observable = localDataSource.read(house);
        }

        return observable;
    }

}
