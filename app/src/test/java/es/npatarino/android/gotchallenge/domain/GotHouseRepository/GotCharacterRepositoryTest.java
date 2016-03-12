package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import org.junit.Test;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;

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
}