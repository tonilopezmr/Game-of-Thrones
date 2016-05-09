package es.npatarino.android.gotchallenge.data;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.datasource.local.HouseLocalDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.HouseRemoteDataSource;
import es.npatarino.android.gotchallenge.domain.repository.GotHouseRepository;
import rx.Observable;

/**
 * @author Antonio López.
 */
public class GotHouseRepositoryImp implements GotHouseRepository {
    
    private HouseRemoteDataSource remoteDataSource;
    private HouseLocalDataSource localDataSource;

    public GotHouseRepositoryImp(HouseRemoteDataSource remoteDataSource, HouseLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public Observable<List<GoTHouse>> getList() {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(remoteDataSource.getAll());
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        });
    }
}

