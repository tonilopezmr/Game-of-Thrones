package es.npatarino.android.gotchallenge.data.source.local;

import java.util.List;

import es.npatarino.android.gotchallenge.data.caching.TimeProvider;
import es.npatarino.android.gotchallenge.data.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.data.source.local.entities.BddHouse;
import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.datasource.local.HouseLocalDataSource;
import es.npatarino.android.gotchallenge.domain.mapper.TwoWaysMapper;
import io.realm.Realm;
import rx.Observable;

public class HouseLocalDataSourceImp implements HouseLocalDataSource {

    private TTLCachingStrategy cachingStrategy;
    private TimeProvider timeProvider;
    private TwoWaysMapper<House, BddHouse> mapper;

    public HouseLocalDataSourceImp(TTLCachingStrategy cachingStrategy, TimeProvider timeProvider, TwoWaysMapper<House, BddHouse> mapper) {
        this.cachingStrategy = cachingStrategy;
        this.timeProvider = timeProvider;
        this.mapper = mapper;
    }

    @Override
    public void save(List<House> save) {
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
        return cachingStrategy.isValid(timeProvider.getPersistedTime());
    }

    @Override
    public void removeAll(List<House> remove) {
        for (int i = 0, size = remove.size(); i < size; i++) {
            remove(remove.get(i));
        }
    }

    private void remove(House house){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            BddHouse bddHouse = find(house);
            if (bddHouse!=null){
                bddHouse.deleteFromRealm();
            }
        } );
        realm.close();
    }

    @Override
    public Observable<List<House>> getAll() {
        return Observable.create(subscriber -> {
            Realm realm = Realm.getDefaultInstance();
            List<BddHouse> result = realm.where(BddHouse.class).findAll();
            subscriber.onNext(mapper.inverseMap(result));
            realm.close();
            subscriber.onCompleted();
        });
    }

    public BddHouse find(House house) {
        Realm realm = Realm.getDefaultInstance();
        BddHouse bddHouse = realm.where(BddHouse.class)
                .equalTo(BddHouse.PRIMARY_KEY_NAME, house.getHouseId())
                .findFirst();
        realm.close();
        return bddHouse;
    }
}
