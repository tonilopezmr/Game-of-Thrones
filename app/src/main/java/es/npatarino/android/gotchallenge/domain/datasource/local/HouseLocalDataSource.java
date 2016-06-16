package es.npatarino.android.gotchallenge.domain.datasource.local;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.House;
import rx.Observable;

public interface HouseLocalDataSource {
    void save(List<House> save);
    boolean isExpired();
    void removeAll(List<House> remove);

    Observable<List<House>> getAll();
}
