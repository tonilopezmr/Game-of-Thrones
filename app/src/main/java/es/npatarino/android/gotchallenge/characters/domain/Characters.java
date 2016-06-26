package es.npatarino.android.gotchallenge.characters.domain;

import java.util.List;

import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.houses.domain.model.House;
import es.npatarino.android.gotchallenge.common.repository.ListRepository;
import rx.Observable;

public interface Characters {

    interface LocalDataSource {
        void save(List<GoTCharacter> save);
        boolean isExpired();
        void removeAll(List<GoTCharacter> remove);

        Observable<GoTCharacter> read(GoTCharacter entity);
        Observable<List<GoTCharacter>> read(House house);
        Observable<List<GoTCharacter>> getAll();
    }

    interface NetworkDataSource {
        Observable<GoTCharacter> read(GoTCharacter entity);
        Observable<List<GoTCharacter>> read(House house);
        Observable<List<GoTCharacter>> getAll();
    }

    interface Repository extends ListRepository<GoTCharacter> {
        Observable<GoTCharacter> read(GoTCharacter entity);
        Observable<List<GoTCharacter>> read(House house);
    }

}
