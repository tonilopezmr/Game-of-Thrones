package es.npatarino.android.gotchallenge.houses.data;

import es.npatarino.android.gotchallenge.houses.domain.HousesDomain;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import rx.Observable;

import java.util.List;

public class HouseRepository implements HousesDomain.Repository {

  private HousesDomain.NetworkDataSource networkDataSource;
  private HousesDomain.LocalDataSource localDataSource;

  public HouseRepository(HousesDomain.NetworkDataSource networkDataSource,
                         HousesDomain.LocalDataSource localDataSource) {
    this.networkDataSource = networkDataSource;
    this.localDataSource = localDataSource;
  }

  @Override
  public Observable<List<GoTHouse>> getList() {
    Observable<List<GoTHouse>> observable;

    if (localDataSource.isExpired()) {
      observable = networkDataSource.getAll()
          .doOnNext(houses -> {
            localDataSource.removeAll(houses);
            localDataSource.save(houses);
          })
          .retry((attempts, error) -> error instanceof Exception && attempts < 2)
          .onErrorResumeNext(throwable -> {
            return localDataSource.getAll();
          });
    } else {
      observable = localDataSource.getAll();
    }

    return observable;
  }
}

