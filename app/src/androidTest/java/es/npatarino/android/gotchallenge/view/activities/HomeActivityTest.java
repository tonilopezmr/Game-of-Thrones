package es.npatarino.android.gotchallenge.view.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.TestUtils;
import es.npatarino.android.gotchallenge.domain.GotHouseRepository.GotCharacterRepositoryImp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.when;

/**
 * @author Antonio López.
 */
@RunWith(AndroidJUnit4.class) @LargeTest
public class HomeActivityTest {

    private static final int NUMBER_OF_CHARACTERS = 4;

    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule =
            new ActivityTestRule<>(HomeActivity.class,true, false);

    @Mock GotCharacterRepositoryImp repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void
    should_show_recyclerview_of_gotcharacters() throws Exception {
        when(repository.getList()).thenReturn(TestUtils.getCharacters(NUMBER_OF_CHARACTERS));

        activityTestRule.launchActivity(null);

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
    }

}
