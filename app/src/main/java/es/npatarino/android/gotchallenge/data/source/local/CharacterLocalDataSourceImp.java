package es.npatarino.android.gotchallenge.data.source.local;

import java.util.List;

import es.npatarino.android.gotchallenge.data.caching.TimeProvider;
import es.npatarino.android.gotchallenge.data.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.data.source.local.entities.BddGoTCharacter;
import es.npatarino.android.gotchallenge.data.source.local.entities.BddHouse;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.datasource.local.CharacterLocalDataSource;
import es.npatarino.android.gotchallenge.domain.mapper.TwoWaysMapper;
import io.realm.Realm;
import rx.Observable;

public class CharacterLocalDataSourceImp implements CharacterLocalDataSource{

    private TTLCachingStrategy ttlCachingStrategy;
    private TimeProvider timeProvider;
    private TwoWaysMapper<GoTCharacter, BddGoTCharacter> mapper;

    public CharacterLocalDataSourceImp(TTLCachingStrategy ttlCachingStrategy,
                                       TimeProvider timeProvider,
                                       TwoWaysMapper<GoTCharacter, BddGoTCharacter> mapper) {
        this.ttlCachingStrategy = ttlCachingStrategy;
        this.timeProvider = timeProvider;
        this.mapper = mapper;
    }

    @Override
    public void save(List<GoTCharacter> save) {
        List<BddGoTCharacter> list = mapper.map(save);
        for (int i = 0, size = list.size(); i < size; i++) {
            save(list.get(i));
        }
        timeProvider.persistTime();
    }

    private void save(BddGoTCharacter character) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm.copyToRealmOrUpdate(character);
        });
        realm.close();
    }

    @Override
    public boolean isExpired() {
        return !ttlCachingStrategy.isValid(timeProvider.getPersistedTime());
    }

    @Override
    public void removeAll(List<GoTCharacter> remove) {
        for (int i = 0, size = remove.size(); i < size; i++) {
            remove(remove.get(i));
        }
    }

    private void remove(GoTCharacter character){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            BddGoTCharacter bddGoTCharacter = find(character);
            if (bddGoTCharacter!=null){
                bddGoTCharacter.deleteFromRealm();
            }
        });
        realm.close();
    }

    @Override
    public Observable<GoTCharacter> read(GoTCharacter entity) {
        return Observable.create(subscriber -> {
            Realm realm = Realm.getDefaultInstance();
            BddGoTCharacter result = realm.where(BddGoTCharacter.class)
                    .equalTo(BddGoTCharacter.PRIMARY_KEY_NAME, entity.getName())
                    .findFirst();
            subscriber.onNext(mapper.inverseMap(result));
            realm.close();
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<List<GoTCharacter>> read(House house) {
        return Observable.create(subscriber -> {
            Realm realm = Realm.getDefaultInstance();
            List<BddGoTCharacter> result = realm.where(BddGoTCharacter.class)
                    .equalTo(BddHouse.PRIMARY_KEY_NAME, house.getHouseId())
                    .findAll();
            subscriber.onNext(mapper.inverseMap(result));
            realm.close();
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<List<GoTCharacter>> getAll() {
        return Observable.create(subscriber -> {
            Realm realm = Realm.getDefaultInstance();
            try{
                List<BddGoTCharacter> result = realm.where(BddGoTCharacter.class).findAll();
                subscriber.onNext(mapper.inverseMap(result));
            }catch (Exception e){
                e.printStackTrace();
                subscriber.onError(e);
            }finally {
                realm.close();
                subscriber.onCompleted();
            }
        });
    }

    public BddGoTCharacter find(GoTCharacter goTCharacter) {
        Realm realm = Realm.getDefaultInstance();
        BddGoTCharacter bddGoTCharacter = realm.where(BddGoTCharacter.class)
                .equalTo(BddGoTCharacter.PRIMARY_KEY_NAME, goTCharacter.getName())
                .findFirst();
        realm.close();
        return bddGoTCharacter;
    }
}
