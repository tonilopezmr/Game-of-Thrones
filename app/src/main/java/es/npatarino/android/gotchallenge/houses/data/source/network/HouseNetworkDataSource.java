package es.npatarino.android.gotchallenge.houses.data.source.network;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.characters.domain.Characters;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.houses.domain.Houses;
import es.npatarino.android.gotchallenge.houses.domain.model.House;
import rx.Observable;

public class HouseNetworkDataSource implements Houses.NetworkDataSource {

    private Characters.NetworkDataSource dataSource;

    public HouseNetworkDataSource(Characters.NetworkDataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void addHouseInList(House house, ArrayList<House> hs) {
        if (isValidHouse(house) && !hs.contains(house)) {
            hs.add(house);
        }
    }

    private boolean isValidHouse(House house) {
        return house.getHouseId() != null && !house.getHouseId().isEmpty();
    }

    private House getHouseFromCharacter(GoTCharacter character) {
        House h = new House();
        h.setHouseId(character.getHouseId());
        h.setHouseName(character.getHouseName());
        h.setHouseImageUrl(character.getHouseImageUrl());
        return h;
    }

    @Override
    public Observable<List<House>> getAll() {
        return dataSource.getAll().map(characters -> {
            ArrayList<House> hs = new ArrayList<House>();
            for (int i = 0, size = characters.size(); i < size; i++) {
                GoTCharacter character = characters.get(i);
                House house = getHouseFromCharacter(character);
                addHouseInList(house, hs);
            }
            return hs;
        });
    }


}
