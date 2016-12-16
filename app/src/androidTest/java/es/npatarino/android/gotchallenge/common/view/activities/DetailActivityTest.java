package es.npatarino.android.gotchallenge.common.view.activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.squareup.spoon.Spoon;
import es.npatarino.android.gotchallenge.GotChallengeApplication;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.TestUtils;
import es.npatarino.android.gotchallenge.characters.domain.CharactersDomain;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.common.ui.activities.DetailActivity;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import es.npatarino.android.gotchallenge.testingtools.EspressoDaggerMockRule;
import es.npatarino.android.gotchallenge.testingtools.viewassertions.toolbar.ToolbarTitleViewAssertion;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import rx.Observable;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static es.npatarino.android.gotchallenge.testingtools.matchers.RecyclerViewItemsCountMatcher.recyclerViewHasItemCount;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    private static final int NUMBER_OF_CHARACTERS = 3;

    @Rule
    public EspressoDaggerMockRule espressoDaggerMockRule = new EspressoDaggerMockRule();

    @Rule
    public ActivityTestRule<DetailActivity> activityTestRule =
            new ActivityTestRule<>(DetailActivity.class, true, false);

    @Mock
    CharactersDomain.Repository repository;

    @After
    public void tearDown() throws Exception {
        GotChallengeApplication app = EspressoDaggerMockRule.getApp();
        app.releaseCharacterComponent();
    }

    @Test
    public void
    should_character_name_as_toolbar_tittle() throws Exception {
        GoTCharacter character = TestUtils.defaultGotCharacter(0);

        startActivity(character);

        Spoon.screenshot(activityTestRule.getActivity(), "init_state_character_detail");

        onView(withId(R.id.toolbar))
                .check(ToolbarTitleViewAssertion.withTitle(character.getName()));
    }

    @Test
    public void
    should_display_description_given_is_character() throws Exception {
        GoTCharacter character = TestUtils.defaultGotCharacter(0);

        startActivity(character);

        onView(allOf(withId(R.id.tv_description), withText(character.getDescription()))).check(matches(isDisplayed()));
    }

    @Test
    public void
    should_display_name_given_is_character() throws Exception {
        GoTCharacter character = TestUtils.defaultGotCharacter(0);

        startActivity(character);

        onView(allOf(withId(R.id.tv_name), withText(character.getName()))).check(matches(isDisplayed()));
    }

    @Test
    public void
    should_does_not_show_loading_view_once_character_are_shown() throws Exception {
        GoTHouse house = TestUtils.defaultGotHouse();
        Observable<List<GoTCharacter>> characters = TestUtils.getCharacters(NUMBER_OF_CHARACTERS);
        given(repository.read(house)).willReturn(characters);

        startActivity(house);

        Spoon.screenshot(activityTestRule.getActivity(), "init_state_house_detail");

        onView(withId(R.id.content_loading_progress_bar)).check(matches(not(isDisplayed())));
    }

    @Test
    public void
    should_show_an_specific_number_of_characters() throws Exception {
        GoTHouse house = TestUtils.defaultGotHouse();
        given(repository.read(any(GoTHouse.class))).willReturn(TestUtils.getCharacters(NUMBER_OF_CHARACTERS));

        startActivity(house);

        onView(withId(R.id.recycler_view)).check(matches(recyclerViewHasItemCount(NUMBER_OF_CHARACTERS)));
    }

    @Test
    public void
    should_display_list_given_is_house_with_characters() throws Exception {
        GoTHouse house = TestUtils.defaultGotHouse();
        given(repository.read(house)).willReturn(TestUtils.getCharacters(NUMBER_OF_CHARACTERS));

        startActivity(house);

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
    }

    private DetailActivity startActivity(GoTCharacter character) {
        Intent intent = new Intent();
        intent.putExtra(DetailActivity.DESCRIPTION, character.getDescription());
        intent.putExtra(DetailActivity.NAME, character.getName());
        intent.putExtra(DetailActivity.IMAGE_URL, character.getImageUrl());
        return activityTestRule.launchActivity(intent);
    }

    private DetailActivity startActivity(GoTHouse house) {
        Intent intent = new Intent();
        intent.putExtra(DetailActivity.HOUSE_ID, house.getId());
        intent.putExtra(DetailActivity.NAME, house.getName());
        intent.putExtra(DetailActivity.IMAGE_URL, house.getImageUrl());
        return activityTestRule.launchActivity(intent);
    }

}
