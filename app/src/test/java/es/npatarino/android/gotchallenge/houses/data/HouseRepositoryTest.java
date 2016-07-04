package es.npatarino.android.gotchallenge.houses.data;

import es.npatarino.android.gotchallenge.houses.domain.HousesDomain;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HouseRepositoryTest {

    private static final boolean EXPIRED = true;

    @Mock
    HousesDomain.NetworkDataSource networkDataSource;
    @Mock
    HousesDomain.LocalDataSource localDataSource;

    HouseRepository repository;

    @Before
    public void setUp() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        repository = new HouseRepository(networkDataSource, localDataSource);
    }

    @Test
    public void
    should_not_return_any_house() throws Exception {
        when(localDataSource.isExpired()).thenReturn(EXPIRED);
        when(networkDataSource.getAll()).thenReturn(getEmptyHouseListObservable());

        repository.getList()
                .subscribe(list -> Assert.assertThat(list.size(), Is.is(0)), throwable -> fail());

        verify(networkDataSource).getAll();
        verify(localDataSource).save(anyList());
        verify(localDataSource, never()).getAll();
    }

    @Test
    public void
    should_return_all_houses() throws Exception {
        when(localDataSource.isExpired()).thenReturn(EXPIRED);
        when(networkDataSource.getAll()).thenReturn(getSevenHousesObservable());

        repository.getList()
                .subscribe(list -> Assert.assertThat(list.size(), Is.is(7)), throwable -> fail());

        verify(networkDataSource).getAll();
        verify(localDataSource).save(anyList());
        verify(localDataSource, never()).getAll();
    }

    private List<GoTHouse> getSevenHouses() {
        List<GoTHouse> houses = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            houses.add(new GoTHouse());
        }
        return houses;
    }

    private Observable<List<GoTHouse>> getSevenHousesObservable() {
        return Observable.just(getSevenHouses());
    }

    private Observable<List<GoTHouse>> getEmptyHouseListObservable() {
        return Observable.just(new ArrayList<>());
    }

}
