package es.npatarino.android.gotchallenge.houses.domain;

import java.util.List;

import es.npatarino.android.gotchallenge.common.list.repository.ListRepository;
import es.npatarino.android.gotchallenge.houses.domain.model.House;
import rx.Observable;

public interface Houses {

    interface LocalDataSource {
        void save(List<House> save);
        boolean isExpired();
        void removeAll(List<House> remove);

        Observable<List<House>> getAll();
    }

    interface NetworkDataSource {
        Observable<List<House>> getAll();
    }

    interface Repository extends ListRepository<House> {
    }

}
