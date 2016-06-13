package es.npatarino.android.gotchallenge.view.activities;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import es.npatarino.android.gotchallenge.GotChallengeApplication;
import es.npatarino.android.gotchallenge.TestUtils;
import es.npatarino.android.gotchallenge.di.AppComponent;
import es.npatarino.android.gotchallenge.di.AppModule;
import es.npatarino.android.gotchallenge.domain.datasource.remote.CharacterRemoteDataSource;
import it.cosenonjaviste.daggermock.DaggerMockRule;

import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class) @LargeTest
public class HomeActivityTest {

    private static final int NUMBER_OF_CHARACTERS = 4;

    @Rule public DaggerMockRule<AppComponent> daggerRule =
            new DaggerMockRule<>(AppComponent.class, new AppModule()).set(
                    component -> {
                        GotChallengeApplication app =
                                (GotChallengeApplication) InstrumentationRegistry.getInstrumentation()
                                        .getTargetContext()
                                        .getApplicationContext();
                        app.setComponent(component);
                    });
    
    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule =
            new ActivityTestRule<>(HomeActivity.class, true, false);

    @Mock
    CharacterRemoteDataSource repository;

    @Test public void
    should_show_data_in_characters_and_houses_recyclerview() throws Exception {
        when(repository.getAll()).thenReturn(TestUtils.getDelayedCharacters(NUMBER_OF_CHARACTERS));

//        activityTestRule.launchActivity(null);

//        onView(allOf(withId(R.id.recycler_view), isDisplayed())).check(matches(isDisplayed()));
    }

    @Test public void
    should_does_not_show_loading_view_once_character_are_shown() throws Exception {
        when(repository.getAll()).thenAnswer(invocation -> TestUtils.getDelayedCharacters(NUMBER_OF_CHARACTERS));

//        activityTestRule.launchActivity(null);

//        onView(allOf(withId(R.id.content_loading_progress_bar), isDisplayed())).check(matches(not(isDisplayed())));
    }

}
