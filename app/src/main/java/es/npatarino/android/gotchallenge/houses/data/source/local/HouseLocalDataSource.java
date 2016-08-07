package es.npatarino.android.gotchallenge.houses.data.source.local;

import es.npatarino.android.gotchallenge.base.caching.TimeProvider;
import es.npatarino.android.gotchallenge.base.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.base.mapper.TwoWaysMapper;
import es.npatarino.android.gotchallenge.houses.data.source.local.entities.BddHouse;
import es.npatarino.android.gotchallenge.houses.domain.HousesDomain;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import io.realm.Realm;
import rx.Observable;

import java.util.List;

public class HouseLocalDataSource implements HousesDomain.LocalDataSource {

    private TTLCachingStrategy cachingStrategy;
    private TimeProvider timeProvider;
    private TwoWaysMapper<GoTHouse, BddHouse> mapper;

    public HouseLocalDataSource(TTLCachingStrategy cachingStrategy,
                                TimeProvider timeProvider,
                                TwoWaysMapper<GoTHouse,
                                BddHouse> mapper) {
        this.cachingStrategy = cachingStrategy;
        this.timeProvider = timeProvider;
        this.mapper = mapper;
    }

    @Override
    public void save(List<GoTHouse> save) {
        List<BddHouse> list = mapper.map(save);
        for (int i = 0, size = list.size(); i < size; i++) {
            save(list.get(i));
        }
    }

    private void save(BddHouse house) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm.copyToRealmOrUpdate(house);
        });
        realm.close();
    }


    @Override
    public boolean isExpired() {
        return !cachingStrategy.isValid(timeProvider.getPersistedTime());
    }

    @Override
    public void removeAll(List<GoTHouse> remove) {
        for (int i = 0, size = remove.size(); i < size; i++) {
            remove(remove.get(i));
        }
    }

    private void remove(GoTHouse house) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            BddHouse bddHouse = find(house);
            if (bddHouse != null) {
                bddHouse.deleteFromRealm();
            }
        });
        realm.close();
    }

    @Override
    public Observable<List<GoTHouse>> getAll() {
        return Observable.fromCallable(() -> {
            Realm realm = Realm.getDefaultInstance();
            List<BddHouse> result = realm.where(BddHouse.class).findAll();
            List<GoTHouse> goTHouses = mapper.inverseMap(result);
            realm.close();
            return goTHouses;
        });
    }

    private BddHouse find(GoTHouse house) {
        Realm realm = Realm.getDefaultInstance();
        BddHouse bddHouse = realm.where(BddHouse.class)
                .equalTo(BddHouse.PRIMARY_KEY_NAME, house.getHouseId())
                .findFirst();
        realm.close();
        return bddHouse;
    }
}
