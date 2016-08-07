package es.npatarino.android.gotchallenge.houses.data.source.local.mapper;

import android.support.annotation.NonNull;
import es.npatarino.android.gotchallenge.BuildConfig;
import es.npatarino.android.gotchallenge.houses.data.source.local.entities.BddHouse;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BddHouseMapperTest {

    private static final String HOUSE_ID = "houseId";
    private static final String HOUSE_NAME = "houseName";
    private static final String HOUSE_IMAGE_URL = "houseImageUrl";
    private static final int NUMBER_OF_HOUSES = 10;
    private BddHouseMapper mapper = new BddHouseMapper();

    @Test
    public void
    should_return_correct_BddHouse_when_map() {
        GoTHouse character = getHouse();
        BddHouse bddHouse = mapper.map(character);

        assertThat(bddHouse.getHouseId(), is(HOUSE_ID));
        assertThat(bddHouse.getHouseName(), is(HOUSE_NAME));
        assertThat(bddHouse.getHouseImageUrl(), is(HOUSE_IMAGE_URL));
    }

    @Test
    public void
    should_return_correct_House_when_inverseMap() {
        BddHouse bddHouse = new BddHouse();
        bddHouse.setHouseId(HOUSE_ID);
        bddHouse.setHouseName(HOUSE_NAME);
        bddHouse.setHouseImageUrl(HOUSE_IMAGE_URL);

        GoTHouse house = mapper.inverseMap(bddHouse);

        assertThat(house.getHouseId(), is(HOUSE_ID));
        assertThat(house.getHouseName(), is(BuildConfig.DEBUG ? HOUSE_NAME + " cache" : HOUSE_NAME));
        assertThat(house.getHouseImageUrl(), is(HOUSE_IMAGE_URL));
    }

    @Test
    public void
    should_return_correct_BddHouse_list_when_map() {
        List<GoTHouse> goTCharacterList = getHouseList(NUMBER_OF_HOUSES);
        List<BddHouse> bddHouse = mapper.map(goTCharacterList);

        assertThat(NUMBER_OF_HOUSES, is(bddHouse.size()));
        for (BddHouse house : bddHouse) {
            assertThat(house.getHouseId(), is(HOUSE_ID));
            assertThat(house.getHouseName(), is(HOUSE_NAME));
            assertThat(house.getHouseImageUrl(), is(HOUSE_IMAGE_URL));
        }
    }

    private List<GoTHouse> getHouseList(int numberHouse) {
        GoTHouse character = getHouse();
        ArrayList<GoTHouse> characterList = new ArrayList();

        for (int i = 0; i < numberHouse; i++) {
            characterList.add(character);
        }

        return characterList;
    }

    @NonNull
    private GoTHouse getHouse() {
        return new GoTHouse(HOUSE_ID, HOUSE_NAME, HOUSE_IMAGE_URL);
    }

}
