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
        return remoteDataSource.getAll();
    }

    @Override
    public Observable<GoTCharacter> read(GoTCharacter entity){
        return remoteDataSource.read(entity);
    }

    @Override
    public Observable<List<GoTCharacter>> read(House house){
        return remoteDataSource.read(house);
    }

}
