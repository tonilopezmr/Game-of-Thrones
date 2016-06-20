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
import es.npatarino.android.gotchallenge.data.caching.TimeProvider;
import es.npatarino.android.gotchallenge.data.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.data.source.local.entities.mapper.BddHouseMapper;
import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.datasource.local.HouseLocalDataSource;
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

    private HouseLocalDataSource dataSource;

    @Before
    public void setUp(){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(InstrumentationRegistry.getTargetContext())
                .name("test.realm")
                .schemaVersion(2)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        dataSource = new HouseLocalDataSourceImp(ttlCachingStrategy, timeProvider, mapper);
    }

    @Test
    public void
    save_houses_and_get_all() {
        List<House> houseList = getHouses();

        dataSource.save(houseList);

        dataSource.getAll()
                .subscribe(list -> assertHouseList(houseList, list), throwable -> fail());

        dataSource.removeAll(houseList);
    }

    @Test
    public void
    remove_houses() {
        List<House> houseList = getHouses();

        dataSource.save(houseList);
        dataSource.removeAll(houseList);

        dataSource.getAll()
                .subscribe(list -> assertThat(list.size(), is(0)), throwable -> fail());
    }

    private void assertHouseList(List<House> expectedList, List<House> list) {
        assertThat(list.size(), is(expectedList.size()));

        for (int i = 0; i < list.size(); i++) {
            House expected = expectedList.get(i);
            House house = list.get(i);

            assertHouse(expected, house);
        }
    }

    private void assertHouse(House expected, House house) {
        assertThat(house.getHouseId(), is(expected.getHouseId()));
        assertThat(house.getHouseImageUrl(), is(expected.getHouseImageUrl()));
        assertThat(house.getHouseName(), is(expected.getHouseName() + " cache"));
    }

    private List<House> getHouses() {
        List<House> list = Arrays.asList(TestUtils.defaultGotHouse(),
                TestUtils.defaultGotHouse(),
                TestUtils.defaultGotHouse());

        for (int i = 0; i < list.size(); i++) {
            House house = list.get(i);
            house.setHouseId(house.getHouseId()+ i);
        }

        return list;
    }
}
