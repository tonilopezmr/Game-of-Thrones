package es.npatarino.android.gotchallenge.domain.repository;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.data.HouseRepositoryImp;
import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.datasource.local.HouseLocalDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.HouseRemoteDataSource;
import rx.Observable;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HouseRepositoryTest {

    public static final boolean EXPIRED = true;
    @Mock
    HouseRemoteDataSource remoteDataSource;
    @Mock
    HouseLocalDataSource localDataSource;

    HouseRepositoryImp repository;

    @Before
    public void setUp() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        repository = new HouseRepositoryImp(remoteDataSource, localDataSource);
    }

    @Test public void
    should_not_return_any_house() throws Exception {
        when(localDataSource.isExpired()).thenReturn(EXPIRED);
        when(remoteDataSource.getAll()).thenReturn(getEmptyHouseListObservable());

        repository.getList()
                .subscribe(list -> Assert.assertThat(list.size(), Is.is(0)), throwable -> fail());

        verify(remoteDataSource).getAll();
        verify(localDataSource).save(anyList());
        verify(localDataSource, never()).getAll();
    }

    @Test public void
    should_return_all_houses() throws Exception {
        when(localDataSource.isExpired()).thenReturn(EXPIRED);
        when(remoteDataSource.getAll()).thenReturn(getSevenHousesObservable());

        repository.getList()
                .subscribe(list -> Assert.assertThat(list.size(), Is.is(7)), throwable -> fail());

        verify(remoteDataSource).getAll();
        verify(localDataSource).save(anyList());
        verify(localDataSource, never()).getAll();
    }

    private List<House> getSevenHouses(){
        List<House> houses = new ArrayList<>();
        for (int i = 0; i < 7; i++){
            houses.add(new House());
        }
        return houses;
    }

    private Observable<List<House>> getSevenHousesObservable(){
        return Observable.just(getSevenHouses());
    }

    private Observable<List<House>> getEmptyHouseListObservable(){
        return Observable.just(new ArrayList<>());
    }
}