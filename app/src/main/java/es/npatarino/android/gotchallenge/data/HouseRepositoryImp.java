package es.npatarino.android.gotchallenge.data;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.datasource.local.HouseLocalDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.HouseRemoteDataSource;
import es.npatarino.android.gotchallenge.domain.repository.HouseRepository;
import rx.Observable;

public class HouseRepositoryImp implements HouseRepository {
    
    private HouseRemoteDataSource remoteDataSource;
    private HouseLocalDataSource localDataSource;

    public HouseRepositoryImp(HouseRemoteDataSource remoteDataSource, HouseLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public Observable<List<House>> getList() {
        return remoteDataSource.getAll();
    }
}

