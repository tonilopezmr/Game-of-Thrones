package es.npatarino.android.gotchallenge.domain.datasource.remote;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTHouse;

public interface HouseRemoteDataSource {
    List<GoTHouse> getAll() throws Exception;
}
