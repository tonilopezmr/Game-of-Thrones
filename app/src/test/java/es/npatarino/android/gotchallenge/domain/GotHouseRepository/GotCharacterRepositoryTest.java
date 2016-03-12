package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import org.junit.Test;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;

import static org.junit.Assert.*;

/**
 * @author Antonio López.
 */
public class GotCharacterRepositoryTest {
    @Test
    public void get_some_characters() throws Exception {
        GotCharacterRepository repository = new GotCharacterRepository();
        List<GoTCharacter> list = repository.getList();
        assertTrue(list.size() > 0);
    }

    @Test
    public void get_characters_by_house() throws Exception {
        GoTHouse starkHouse = new GoTHouse();
        starkHouse.setHouseId("f96537a9");
        starkHouse.setHouseName("House Stark");
        
        GotCharacterRepository repository = new GotCharacterRepository();
        List<GoTCharacter> list = repository.read(starkHouse);
        assertEquals(4, list.size());
    }

    @Test
    public void get_character() throws Exception {
        GoTCharacter goTCharacter = new GoTCharacter();
        goTCharacter.setImageUrl("https://s3-eu-west-1.amazonaws.com/npatarino/got/1807e12a-b95b-44c4-a750-044d8b0a87ae.jpg");
        goTCharacter.setName("Oberyn Martell");

        GotCharacterRepository repository = new GotCharacterRepository();
        GoTCharacter character = repository.read(goTCharacter);
        assertEquals(character.getName(), goTCharacter.getName());
        assertEquals(character.getImageUrl(), goTCharacter.getImageUrl());
    }

}