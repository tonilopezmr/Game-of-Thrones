package es.npatarino.android.gotchallenge.data.source.local;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import es.npatarino.android.gotchallenge.TestUtils;
import es.npatarino.android.gotchallenge.common.caching.TimeProvider;
import es.npatarino.android.gotchallenge.common.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.houses.data.source.local.HouseLocalDataSource;
import es.npatarino.android.gotchallenge.houses.data.source.local.mapper.BddHouseMapper;
import es.npatarino.android.gotchallenge.houses.domain.Houses;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class HouseLocalDataSourceTest {

    private TTLCachingStrategy ttlCachingStrategy = new TTLCachingStrategy(2 , TimeUnit.MINUTES);
    private TimeProvider timeProvider = new TimeProvider(InstrumentationRegistry.getTargetContext());
    private BddHouseMapper mapper = new BddHouseMapper();

    private Houses.LocalDataSource dataSource;

    @Before
    public void setUp(){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(InstrumentationRegistry.getTargetContext())
                .name("test.realm")
                .schemaVersion(2)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        dataSource = new HouseLocalDataSource(ttlCachingStrategy, timeProvider, mapper);
    }

    @Test
    public void
    save_houses_and_get_all() {
        List<GoTHouse> houseList = getHouses();

        dataSource.save(houseList);

        dataSource.getAll()
                .subscribe(list -> assertHouseList(houseList, list), throwable -> fail());

        dataSource.removeAll(houseList);
    }

    @Test
    public void
    remove_houses() {
        List<GoTHouse> houseList = getHouses();

        dataSource.save(houseList);
        dataSource.removeAll(houseList);

        dataSource.getAll()
                .subscribe(list -> assertThat(list.size(), is(0)), throwable -> fail());
    }

    private void assertHouseList(List<GoTHouse> expectedList, List<GoTHouse> list) {
        assertThat(list.size(), is(expectedList.size()));

        for (int i = 0; i < list.size(); i++) {
            GoTHouse expected = expectedList.get(i);
            GoTHouse house = list.get(i);

            assertHouse(expected, house);
        }
    }

    private void assertHouse(GoTHouse expected, GoTHouse house) {
        assertThat(house.getHouseId(), is(expected.getHouseId()));
        assertThat(house.getHouseImageUrl(), is(expected.getHouseImageUrl()));
        assertThat(house.getHouseName(), is(expected.getHouseName() + " cache"));
    }

    private List<GoTHouse> getHouses() {
        List<GoTHouse> list = Arrays.asList(TestUtils.defaultGotHouse(),
                TestUtils.defaultGotHouse(),
                TestUtils.defaultGotHouse());

        for (int i = 0; i < list.size(); i++) {
            GoTHouse house = list.get(i);
            house.setHouseId(house.getHouseId()+ i);
        }

        return list;
    }
}
