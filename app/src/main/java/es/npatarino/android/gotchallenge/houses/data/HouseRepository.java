package es.npatarino.android.gotchallenge.houses.data;

import java.util.List;

import es.npatarino.android.gotchallenge.houses.domain.Houses;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import rx.Observable;

public class HouseRepository implements Houses.Repository {
    
    private Houses.NetworkDataSource networkDataSource;
    private Houses.LocalDataSource localDataSource;

    public HouseRepository(Houses.NetworkDataSource networkDataSource, Houses.LocalDataSource localDataSource) {
        this.networkDataSource = networkDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public Observable<List<GoTHouse>> getList() {
        Observable<List<GoTHouse>> observable;

        if (localDataSource.isExpired()){
            observable = networkDataSource.getAll()
                    .doOnNext(houses -> {
                        localDataSource.removeAll(houses);
                        localDataSource.save(houses);
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
}

