package es.npatarino.android.gotchallenge.data;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.datasource.local.CharacterLocalDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.CharacterRemoteDataSource;
import es.npatarino.android.gotchallenge.domain.repository.GotCharacterRepository;
import rx.Observable;

/**
 * @author Antonio López.
 */
public class GotCharacterRepositoryImp implements GotCharacterRepository {

    private CharacterRemoteDataSource remoteDataSource;
    private CharacterLocalDataSource localDataSource;

    public GotCharacterRepositoryImp(CharacterRemoteDataSource remoteDataSource, CharacterLocalDataSource localDataSource) {
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
    public Observable<List<GoTCharacter>> read(GoTHouse house){
        return remoteDataSource.read(house);
    }

}
