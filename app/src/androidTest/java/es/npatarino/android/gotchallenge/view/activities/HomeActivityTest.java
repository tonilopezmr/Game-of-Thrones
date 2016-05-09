package es.npatarino.android.gotchallenge.view.activities;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import es.npatarino.android.gotchallenge.GotChallengeApplication;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.TestUtils;
import es.npatarino.android.gotchallenge.data.GotCharacterRepositoryImp;
import es.npatarino.android.gotchallenge.di.AppComponent;
import es.npatarino.android.gotchallenge.di.AppModule;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.when;

/**
 * @author Antonio López.
 */
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
            new ActivityTestRule<>(HomeActivity.class,true, false);

    @Mock GotCharacterRepositoryImp repository;

    @Test public void
    should_show_data_in_characters_and_houses_recyclerview() throws Exception {
        when(repository.getList()).thenReturn(TestUtils.getCharacters(NUMBER_OF_CHARACTERS));

        activityTestRule.launchActivity(null);

        onView(allOf(withId(R.id.recycler_view), isDisplayed())).check(matches(isDisplayed()));
    }

    @Test public void
    should_does_not_show_loading_view_once_character_are_shown() throws Exception {
        when(repository.getList()).thenAnswer(new Answer<Observable<List<GoTCharacter>>>() {
            @Override
            public Observable<List<GoTCharacter>> answer(InvocationOnMock invocation) throws Throwable {
                Thread.sleep(2000);
                return TestUtils.getCharacters(NUMBER_OF_CHARACTERS);
            }
        });

        activityTestRule.launchActivity(null);

        onView(allOf(withId(R.id.content_loading_progress_bar), isDisplayed())).check(matches(isDisplayed()));
    }

}
