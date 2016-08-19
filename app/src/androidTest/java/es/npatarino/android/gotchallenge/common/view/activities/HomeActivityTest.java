package es.npatarino.android.gotchallenge.common.view.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.TestUtils;
import es.npatarino.android.gotchallenge.characters.domain.CharactersDomain;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.testingtools.EspressoDaggerMockRule;
import es.npatarino.android.gotchallenge.testingtools.viewassertions.recyclerview.RecyclerViewInteraction;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import rx.Observable;

import java.util.List;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityTest {

    private static final int NUMBER_OF_CHARACTERS = 4;

    @Rule
    public EspressoDaggerMockRule daggerMockRule = new EspressoDaggerMockRule();

    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule =
            new ActivityTestRule<>(HomeActivity.class, true, false);

    @Mock
    CharactersDomain.NetworkDataSource remote;

    @Mock
    CharactersDomain.LocalDataSource local;

    @Test
    public void
    show_characters_name() throws Exception {
        Observable<List<GoTCharacter>> charactersObservable = TestUtils.getCharacters(NUMBER_OF_CHARACTERS);
        List<GoTCharacter> characterList = charactersObservable.toBlocking().first();

        when(remote.getAll()).thenReturn(charactersObservable);
        when(local.getAll()).thenReturn(charactersObservable);

        activityTestRule.launchActivity(null);

        RecyclerViewInteraction.<GoTCharacter>onRecyclerView(allOf(withId(R.id.recycler_view), isDisplayed()))
                .withItems(characterList)
                .check((character, view, e) -> matches(hasDescendant(withText(character.getName()))).check(view, e));
    }

}
