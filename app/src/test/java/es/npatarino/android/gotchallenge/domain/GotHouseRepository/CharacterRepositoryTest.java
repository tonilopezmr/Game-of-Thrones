package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.data.CharacterRepositoryImp;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.datasource.local.CharacterLocalDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.CharacterRemoteDataSource;
import es.npatarino.android.gotchallenge.domain.repository.CharacterRepository;
import rx.Observable;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class CharacterRepositoryTest {

    private static final House STARK_HOUSE = new House();
    private static final String STARK_ID = "f96537a9";
    private static final String STARK_NAME = "House Stark";
    private static final GoTCharacter ANYONE = new GoTCharacter();
    private static final GoTCharacter KHAL_DROGO = new GoTCharacter();
    private static final String KHAL_DROGO_NAME = "Khal Drogo";
    private static final String KHAL_DROGO_URL = "https://s3-eu-west-1.amazonaws.com/npatarino/got/8310ebeb-cdda-4095-bd5b-f59266d44677.jpg";
    private static final House INVENTED_HOUSE = new House();

    @Mock
    CharacterRemoteDataSource remoteDataSource;
    @Mock
    CharacterLocalDataSource localDataSource;

    CharacterRepository repository;

    @Before
    public void setUp() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        repository = new CharacterRepositoryImp(remoteDataSource, localDataSource);
    }

    @Test public void
    should_return_all_characters() throws Exception {
        when(remoteDataSource.getAll()).thenReturn(getCharactersObservable(10));

        repository.getList()
                .subscribe(list -> assertThat(list.size(), is(10)), throwable -> fail());
    }

    @Test(expected = Exception.class)
    public void
    should_throw_an_exception_when_the_data_is_not_well() throws Exception {
        when(remoteDataSource.getAll()).thenThrow(new Exception());

        repository.getList().toBlocking().single();
    }

    @Test
    public void
    should_return_characters_by_house() throws Exception {
        House house = STARK_HOUSE;
        house.setHouseId(STARK_ID);
        house.setHouseName(STARK_NAME);

        when(remoteDataSource.read(house)).thenReturn(getCharactersObservable(2));

        repository.read(house)
                .subscribe(list -> assertThat(list.size(), is(2)), throwable -> fail());
    }

    @Test
    public void
    should_not_return_any_character_when_house_are_not() throws Exception {
        House house = INVENTED_HOUSE;

        when(remoteDataSource.read(house)).thenReturn(getEmptyHouseListObservable());

        repository.read(house)
                .subscribe(list -> assertThat(list.size(), is(0)), throwable -> fail());
    }

    @Test
    public void
    should_return_character() throws Exception {
        GoTCharacter gotCharacter = KHAL_DROGO;
        gotCharacter.setName(KHAL_DROGO_NAME);
        gotCharacter.setImageUrl(KHAL_DROGO_URL);

        when(remoteDataSource.read(gotCharacter)).thenReturn(Observable.just(gotCharacter));

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

        when(remoteDataSource.read(gotCharacter)).thenReturn(Observable.just(null));

        repository.read(gotCharacter).subscribe(Assert::assertNull, throwable -> fail());
    }

    private List<GoTCharacter> getCharacters(int numCharacters){
        List<GoTCharacter> characters = new ArrayList<>();
        for (int i = 0; i < numCharacters; i++){
            characters.add(new GoTCharacter());
        }
        return characters;
    }

    private Observable<List<GoTCharacter>> getCharactersObservable(int numCharacters){
        return Observable.just(getCharacters(numCharacters));
    }

    private Observable<List<GoTCharacter>> getEmptyHouseListObservable(){
        return Observable.just(new ArrayList<>());
    }
}