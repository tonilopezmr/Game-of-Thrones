package es.npatarino.android.gotchallenge.data.source.local.entities.mapper;

import android.support.annotation.NonNull;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.data.source.local.entities.BddHouse;
import es.npatarino.android.gotchallenge.domain.House;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BddHouseMapperTest {

    public static final String HOUSE_ID = "houseId";
    public static final String HOUSE_NAME = "houseName";
    public static final String HOUSE_IMAGE_URL = "houseImageUrl";
    public static final int NUMBER_OF_HOUSES = 10;
    private BddHouseMapper mapper = new BddHouseMapper();
    
    @Test
    public void
    should_return_correct_BddHouse_when_map() {
        House character = getHouse();
        BddHouse bddHouse = mapper.map(character);

        assertThat(HOUSE_ID, is(bddHouse.getHouseId()));
        assertThat(HOUSE_NAME, is(bddHouse.getHouseName()));
        assertThat(HOUSE_IMAGE_URL, is(bddHouse.getHouseImageUrl()));
    }

    @Test
    public void
    should_return_correct_House_when_inverseMap() {
        BddHouse bddHouse = new BddHouse();
        bddHouse.setHouseId(HOUSE_ID);
        bddHouse.setHouseName(HOUSE_NAME);
        bddHouse.setHouseImageUrl(HOUSE_IMAGE_URL);

        House house = mapper.inverseMap(bddHouse);

        assertThat(HOUSE_ID, is(house.getHouseId()));
        assertThat(HOUSE_IMAGE_URL, is(house.getHouseImageUrl()));
        assertThat(HOUSE_NAME, is(house.getHouseName()));
    }

    @Test
    public void
    should_return_correct_BddHouse_list_when_map() {
        List<House> goTCharacterList = getHouseList(NUMBER_OF_HOUSES);
        List<BddHouse> bddHouse = mapper.map(goTCharacterList);

        assertThat(NUMBER_OF_HOUSES, is(bddHouse.size()));
        for (BddHouse house : bddHouse) {
            assertThat(HOUSE_ID, is(house.getHouseId()));
            assertThat(HOUSE_IMAGE_URL, is(house.getHouseImageUrl()));
            assertThat(HOUSE_NAME, is(house.getHouseName()));
        }
    }

    private List<House> getHouseList(int numberHouse){
        House character = getHouse();
        ArrayList<House> characterList = new ArrayList();

        for (int i = 0; i < numberHouse; i++) {
            characterList.add(character);
        }

        return characterList;
    }

    @NonNull
    private House getHouse() {
        return new House(HOUSE_ID, HOUSE_NAME, HOUSE_IMAGE_URL);
    }
}