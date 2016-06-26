package es.npatarino.android.gotchallenge.houses.data.source.network;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.characters.domain.CharactersDomain;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.houses.domain.Houses;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import rx.Observable;

public class HouseNetworkDataSource implements Houses.NetworkDataSource {

    private CharactersDomain.NetworkDataSource dataSource;

    public HouseNetworkDataSource(CharactersDomain.NetworkDataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void addHouseInList(GoTHouse house, ArrayList<GoTHouse> hs) {
        if (isValidHouse(house) && !hs.contains(house)) {
            hs.add(house);
        }
    }

    private boolean isValidHouse(GoTHouse house) {
        return house.getHouseId() != null && !house.getHouseId().isEmpty();
    }

    private GoTHouse getHouseFromCharacter(GoTCharacter character) {
        GoTHouse h = new GoTHouse();
        h.setHouseId(character.getHouseId());
        h.setHouseName(character.getHouseName());
        h.setHouseImageUrl(character.getHouseImageUrl());
        return h;
    }

    @Override
    public Observable<List<GoTHouse>> getAll() {
        return dataSource.getAll().map(characters -> {
            ArrayList<GoTHouse> hs = new ArrayList<GoTHouse>();
            for (int i = 0, size = characters.size(); i < size; i++) {
                GoTCharacter character = characters.get(i);
                GoTHouse house = getHouseFromCharacter(character);
                addHouseInList(house, hs);
            }
            return hs;
        });
    }


}
