package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.npatarino.android.gotchallenge.data.GotCharacterRepositoryImp;
import es.npatarino.android.gotchallenge.data.GotHouseRepositoryImp;

import static org.junit.Assert.fail;

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
        repository.getList()
                .subscribe(list -> Assert.assertThat(list.size(), Is.is(10)), throwable -> fail());
    }

    @Test public void
    should_return_all_houses() throws Exception {
        repository.getList()
                .subscribe(list -> Assert.assertThat(list.size(), Is.is(7)), throwable -> fail());
    }
}