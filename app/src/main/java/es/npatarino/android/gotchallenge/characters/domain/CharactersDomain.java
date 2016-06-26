package es.npatarino.android.gotchallenge.characters.domain;

import java.util.List;

import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import es.npatarino.android.gotchallenge.common.list.repository.ListRepository;
import rx.Observable;

public interface CharactersDomain {

    interface LocalDataSource {
        void save(List<GoTCharacter> save);
        boolean isExpired();
        void removeAll(List<GoTCharacter> remove);

        Observable<GoTCharacter> read(GoTCharacter entity);
        Observable<List<GoTCharacter>> read(GoTHouse house);
        Observable<List<GoTCharacter>> getAll();
    }

    interface NetworkDataSource {
        Observable<GoTCharacter> read(GoTCharacter entity);
        Observable<List<GoTCharacter>> read(GoTHouse house);
        Observable<List<GoTCharacter>> getAll();
    }

    interface Repository extends ListRepository<GoTCharacter> {
        Observable<GoTCharacter> read(GoTCharacter entity);
        Observable<List<GoTCharacter>> read(GoTHouse house);
    }

}
