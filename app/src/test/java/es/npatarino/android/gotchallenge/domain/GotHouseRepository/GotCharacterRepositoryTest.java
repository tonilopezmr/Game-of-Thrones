package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.data.GotCharacterRepositoryImp;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.repository.GotCharacterRepository;
import rx.Observable;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

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
    private static final GoTHouse INVENTED_HOUSE = new GoTHouse();
    private static final String INVALID_DATA_ENDPOINT = "invalid_data.json";
    private static final String VALID_DATA_ENDPOINT = "normal_data.json";

    @Mock
    GotCharacterRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = TestableGotCharacterRepository.provideTestableGotCharacterRepository(VALID_DATA_ENDPOINT);
    }

    private Observable<List<GoTCharacter>> getFakeObservableCharacters(){
        return Observable.just(new ArrayList<>());
    }

    @Test public void
    should_return_all_characters() throws Exception {
        repository.getList()
                .subscribe(list -> assertThat(list.size(), is(10)), throwable -> fail());
    }

    @Test(expected = Exception.class)
    public void
    should_throw_an_exception_when_the_data_is_not_well() throws Exception {
        GotCharacterRepositoryImp repository = TestableGotCharacterRepository.provideTestableGotCharacterRepository(INVALID_DATA_ENDPOINT);

        repository.getList().toBlocking().single();
    }

    @Test
    public void
    should_return_characters_by_house() throws Exception {
        GoTHouse house = STARK_HOUSE;
        house.setHouseId(STARK_ID);
        house.setHouseName(STARK_NAME);

        repository.read(house)
                .subscribe(list -> assertThat(list.size(), is(2)), throwable -> fail());
    }

    @Test
    public void
    should_not_return_any_character_when_house_are_not() throws Exception {
        GoTHouse house = INVENTED_HOUSE;

        repository.read(house)
                .subscribe(list -> assertThat(list.size(), is(0)), throwable -> fail());
    }

    @Test
    public void
    should_return_character() throws Exception {
        GoTCharacter gotCharacter = KHAL_DROGO;
        gotCharacter.setName(KHAL_DROGO_NAME);
        gotCharacter.setImageUrl(KHAL_DROGO_URL);

        repository.read(gotCharacter).subscribe(character -> {
            assertNotNull(character);
            assertEquals(character.getName(), gotCharacter.getName());
            assertEquals(character.getImageUrl(), gotCharacter.getImageUrl());
        }, throwable -> fail());
    }

    @Test
    public void
    should_not_return_any_character() throws Exception {
        GoTCharacter gotCharacter = ANYONE;

        repository.read(gotCharacter).subscribe(Assert::assertNull, throwable -> fail());
    }

}