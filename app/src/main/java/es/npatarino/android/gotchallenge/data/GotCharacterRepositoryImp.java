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
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(remoteDataSource.getList());
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(e);
            }finally {
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<GoTCharacter> read(GoTCharacter entity){
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(remoteDataSource.read(entity));
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(e);
            }finally {
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<GoTCharacter>> read(GoTHouse house){
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(remoteDataSource.read(house));
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(e);
            }finally {
                subscriber.onCompleted();
            }
        });
    }

}
