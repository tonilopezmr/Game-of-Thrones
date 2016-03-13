package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import es.npatarino.android.gotchallenge.ResourceHelper;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * @author Antonio López.
 */
public class GotCharacterRepositoryTest {

    private static final GoTHouse STARK_HOUSE = new GoTHouse();
    private static final String STARK_ID = "f96537a9";
    private static final String STARK_NAME = "House Stark";
    private static final GoTCharacter ANYONE = new GoTCharacter();
    private static final GoTCharacter KHAL_DROGO = new GoTCharacter();
    private static final String KHAL_DROGO_NAME = "Khal Drogo";
    private static final String KHAL_DROGO_URL = "https://s3-eu-west-1.amazonaws.com/npatarino/got/8310ebeb-cdda-4095-bd5b-f59266d44677.jpg";

    GotCharacterRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new TestableGotCharacterRepository();
    }

    @Test public void
    should_get_all_characters() throws Exception {
        List<GoTCharacter> list = repository.getList();

        assertThat(list.size(), is(10));
    }

    @Test
    public void
    should_get_characters_by_house() throws Exception {
        GoTHouse house = STARK_HOUSE;
        house.setHouseId(STARK_ID);
        house.setHouseName(STARK_NAME);

        List<GoTCharacter> list = repository.read(house);
        assertThat(list.size(), is(2));
    }

    @Test
    public void
    should_get_character() throws Exception {
        GoTCharacter gotCharacter = KHAL_DROGO;
        gotCharacter.setName(KHAL_DROGO_NAME);
        gotCharacter.setImageUrl(KHAL_DROGO_URL);

        GoTCharacter character = repository.read(gotCharacter);
        assertEquals(character.getName(), gotCharacter.getName());
        assertEquals(character.getImageUrl(), gotCharacter.getImageUrl());
    }

    @Test
    public void
    should_not_get_any_character() throws Exception {
        GoTCharacter gotCharacter = ANYONE;

        GoTCharacter character = repository.read(gotCharacter);
        assertNull(character);
    }

    private class TestableGotCharacterRepository extends GotCharacterRepository {

        @Override
        protected StringBuffer getCharactersFromUrl() throws Exception {
            ResourceHelper resHelper = new ResourceHelper();
            return resHelper.getContentFromFile("data.json");
        }

    }

}