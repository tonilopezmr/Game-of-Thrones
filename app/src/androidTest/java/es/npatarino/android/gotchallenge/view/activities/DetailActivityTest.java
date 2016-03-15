package es.npatarino.android.gotchallenge.view.activities;

import android.content.Intent;
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
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.GotHouseRepository.GotCharacterRepositoryImp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static es.npatarino.android.gotchallenge.matchers.ToolbarMatcher.onToolbarWithTitle;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.when;

/**
 * @author Antonio López.
 */

@RunWith(AndroidJUnit4.class) @LargeTest
public class DetailActivityTest {

    private static final int NUMBER_OF_CHARACTERS = 3;

    @Rule public ActivityTestRule<DetailActivity> activityTestRule =
            new ActivityTestRule<>(DetailActivity.class,true, false);

    @Mock GotCharacterRepositoryImp repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void
    should_character_name_as_toolbar_tittle() throws Exception {
        GoTCharacter character = TestUtils.defaultGotCharacter();

        startActivity(character);

        onToolbarWithTitle(character.getName()).check(matches(isDisplayed()));
    }

    @Test public void
    should_display_description_when_is_character() throws Exception {
        GoTCharacter character = TestUtils.defaultGotCharacter();

        startActivity(character);

        onView(allOf(withId(R.id.tv_description), withText(character.getDescription()))).check(matches(isDisplayed()));
    }

    @Test public void
    should_display_name_when_is_character() throws Exception {
        GoTCharacter character = TestUtils.defaultGotCharacter();

        startActivity(character);

        onView(allOf(withId(R.id.tv_name), withText(character.getName()))).check(matches(isDisplayed()));
    }

    @Test public void
    should_does_not_show_loading_view_once_character_are_shown() throws Exception {
        GoTHouse house = TestUtils.defaultGotHouse();
        when(repository.read(house)).thenReturn(TestUtils.getCharacters(NUMBER_OF_CHARACTERS));

        startActivity(house);

        onView(withId(R.id.content_loading_progress_bar)).check(matches(not(isDisplayed())));
    }

    @Test public void
    should_display_list_when_is_house_with_characters() throws Exception {
        GoTHouse house = TestUtils.defaultGotHouse();

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

    private DetailActivity startActivity(GoTHouse house){
        Intent intent = new Intent();
        intent.putExtra(DetailActivity.HOUSE_ID, house.getHouseId());
        intent.putExtra(DetailActivity.NAME, house.getHouseName());
        intent.putExtra(DetailActivity.IMAGE_URL, house.getHouseImageUrl());
        return activityTestRule.launchActivity(intent);
    }
}