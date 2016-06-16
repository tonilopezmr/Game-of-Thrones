package es.npatarino.android.gotchallenge.data.source.local.entities.mapper;

import android.support.annotation.NonNull;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.data.source.local.entities.BddGoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class BddGoTCharacterMapperTest {


    public static final String NAME = "name";
    public static final String IMAGE_URL = "imageUrl";
    public static final String DES = "des";
    public static final String HOUSE_IMAGE_URL = "houseImageUrl";
    public static final String HOUSE_NAME = "houseName";
    public static final String HOUSE_ID = "houseId";

    private BddGoTCharacterMapper mapper = new BddGoTCharacterMapper();

    @Test
    public void
    should_return_correct_BddGoTCharacter_when_map() {
        GoTCharacter character = getGoTCharacter();
        BddGoTCharacter bddGoTCharacter = mapper.map(character);

        assertThat(NAME, is(bddGoTCharacter.getName()));
        assertThat(IMAGE_URL, is(bddGoTCharacter.getImageUrl()));
        assertThat(DES, is(bddGoTCharacter.getDescription()));
        assertThat(HOUSE_ID, is(bddGoTCharacter.getHouseId()));
    }

    @Test
    public void
    should_return_correct_GoTCharacter_when_inverseMap() {
        BddGoTCharacter bddGoTCharacter = new BddGoTCharacter();
        bddGoTCharacter.setName(NAME);
        bddGoTCharacter.setImageUrl(IMAGE_URL);
        bddGoTCharacter.setDescription(DES);
        bddGoTCharacter.setHouseId(HOUSE_ID);

        GoTCharacter goTCharacter = mapper.inverseMap(bddGoTCharacter);

        assertThat(NAME, is(goTCharacter.getName()));
        assertThat(IMAGE_URL, is(goTCharacter.getImageUrl()));
        assertThat(DES, is(goTCharacter.getDescription()));
        assertThat(HOUSE_ID, is(goTCharacter.getHouseId()));
        assertNull(goTCharacter.getHouseImageUrl());
        assertNull(goTCharacter.getHouseName());
    }

    @Test
    public void
    should_return_correct_BddGoTCharacter_list_when_map() {
        List<GoTCharacter> goTCharacterList = getGoTCharacterList(10);
        List<BddGoTCharacter> bddGoTCharacter = mapper.map(goTCharacterList);

        assertThat(10, is(bddGoTCharacter.size()));
        for (BddGoTCharacter goTCharacter : bddGoTCharacter) {
            assertThat(NAME, is(goTCharacter.getName()));
            assertThat(IMAGE_URL, is(goTCharacter.getImageUrl()));
            assertThat(DES, is(goTCharacter.getDescription()));
            assertThat(HOUSE_ID, is(goTCharacter.getHouseId()));
        }
    }

    private List<GoTCharacter> getGoTCharacterList(int numberCharacter){
        GoTCharacter character = getGoTCharacter();
        ArrayList<GoTCharacter> characterList = new ArrayList();

        for (int i = 0; i < numberCharacter; i++) {
            characterList.add(character);
        }

        return characterList;
    }

    @NonNull
    private GoTCharacter getGoTCharacter() {
        return new GoTCharacter(NAME, IMAGE_URL, DES, HOUSE_IMAGE_URL, HOUSE_NAME, HOUSE_ID);
    }
}