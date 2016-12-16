package es.npatarino.android.gotchallenge.houses.domain;

import es.npatarino.android.gotchallenge.base.list.repository.ListRepository;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import rx.Observable;

import java.util.List;

public interface HousesDomain {

  interface LocalDataSource {
    void save(List<GoTHouse> save);

    boolean isExpired();

    void removeAll(List<GoTHouse> remove);

    Observable<List<GoTHouse>> getAll();
  }

  interface NetworkDataSource {
    Observable<List<GoTHouse>> getAll();
  }

  interface Repository extends ListRepository<GoTHouse> {
  }

}
