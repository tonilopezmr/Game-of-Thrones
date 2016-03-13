package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTHouse;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Antonio López.
 */
public class GotHouseRepositoryTest {


    private static final String EMPTY_DATA_ENDPOINT = "empty_data.json";

    GotCharacterRepository EMPTY_CHARACTER_REPOSITORY = new TestableGotCharacterRepository(EMPTY_DATA_ENDPOINT);
    GotHouseRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new GotHouseRepository(TestableGotCharacterRepository.provideTestableGotCharacterRepository());
    }

    @Test public void
    should_not_return_any_house() throws Exception {
        repository = new GotHouseRepository(EMPTY_CHARACTER_REPOSITORY);
        List<GoTHouse> list = repository.getList();

        assertThat(list.size(), is(0));
    }

    @Test public void
    should_return_all_houses() throws Exception {
        List<GoTHouse> list = repository.getList();

        assertThat(list.size(), is(7));
    }
}