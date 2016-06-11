package es.npatarino.android.gotchallenge.domain.datasource.remote;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.House;
import rx.Observable;

public interface HouseRemoteDataSource {
    Observable<List<House>> getAll();
}
