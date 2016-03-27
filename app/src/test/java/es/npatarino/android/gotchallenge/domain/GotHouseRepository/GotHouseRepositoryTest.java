package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.repository.GotCharacterRepositoryImp;
import es.npatarino.android.gotchallenge.domain.repository.GotHouseRepositoryImp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Antonio López.
 */
public class GotHouseRepositoryTest {

    private static final String EMPTY_DATA_ENDPOINT = "empty_data.json";
    private static final String VALID_DATA_END_POINT = "normal_data.json";

    GotCharacterRepositoryImp EMPTY_CHARACTER_REPOSITORY = new TestableGotCharacterRepository(EMPTY_DATA_ENDPOINT);
    GotHouseRepositoryImp repository;

    @Before
    public void setUp() throws Exception {
        repository = new GotHouseRepositoryImp(TestableGotCharacterRepository.provideTestableGotCharacterRepository(VALID_DATA_END_POINT));
    }

    @Test public void
    should_not_return_any_house() throws Exception {
        repository = new GotHouseRepositoryImp(EMPTY_CHARACTER_REPOSITORY);
        List<GoTHouse> list = repository.getList();

        assertThat(list.size(), is(0));
    }

    @Test public void
    should_return_all_houses() throws Exception {
        List<GoTHouse> list = repository.getList();

        assertThat(list.size(), is(7));
    }
}