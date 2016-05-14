package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.data.GotHouseRepositoryImp;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.datasource.local.HouseLocalDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.HouseRemoteDataSource;
import rx.Observable;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

/**
 * @author Antonio López.
 */
public class GotHouseRepositoryTest {

    @Mock
    HouseRemoteDataSource remoteDataSource;
    @Mock
    HouseLocalDataSource localDataSource;

    GotHouseRepositoryImp repository;

    @Before
    public void setUp() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        repository = new GotHouseRepositoryImp(remoteDataSource, localDataSource);
    }

    @Test public void
    should_not_return_any_house() throws Exception {
        when(remoteDataSource.getAll()).thenReturn(getEmptyHouseListObservable());

        repository.getList()
                .subscribe(list -> Assert.assertThat(list.size(), Is.is(0)), throwable -> fail());
    }

    @Test public void
    should_return_all_houses() throws Exception {
        when(remoteDataSource.getAll()).thenReturn(getSevenHousesObservable());

        repository.getList()
                .subscribe(list -> Assert.assertThat(list.size(), Is.is(7)), throwable -> fail());
    }

    private List<GoTHouse> getSevenHouses(){
        List<GoTHouse> houses = new ArrayList<>();
        for (int i = 0; i < 7; i++){
            houses.add(new GoTHouse());
        }
        return houses;
    }

    private Observable<List<GoTHouse>> getSevenHousesObservable(){
        return Observable.just(getSevenHouses());
    }

    private Observable<List<GoTHouse>> getEmptyHouseListObservable(){
        return Observable.just(new ArrayList<>());
    }
}