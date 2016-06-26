package es.npatarino.android.gotchallenge.view.activities;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import es.npatarino.android.gotchallenge.GotChallengeApplication;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.TestUtils;
import es.npatarino.android.gotchallenge.characters.domain.Characters;
import es.npatarino.android.gotchallenge.di.AppComponent;
import es.npatarino.android.gotchallenge.di.AppModule;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.houses.domain.model.House;
import it.cosenonjaviste.daggermock.DaggerMockRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static es.npatarino.android.gotchallenge.matchers.RecyclerViewItemsCountMatcher.recyclerViewHasItemCount;
import static es.npatarino.android.gotchallenge.matchers.ToolbarMatcher.onToolbarWithTitle;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class) @LargeTest
public class DetailActivityTest {

    private static final int NUMBER_OF_CHARACTERS = 3;

    @Rule public DaggerMockRule<AppComponent> daggerRule =
            new DaggerMockRule<>(AppComponent.class,
                    new AppModule(InstrumentationRegistry.getInstrumentation()
                    .getTargetContext())).set(
                    component -> {
                        GotChallengeApplication app =
                                (GotChallengeApplication) InstrumentationRegistry.getInstrumentation()
                                        .getTargetContext()
                                        .getApplicationContext();
                        app.setComponent(component);
                    });

    @Rule public ActivityTestRule<DetailActivity> activityTestRule =
            new ActivityTestRule<>(DetailActivity.class,true, false);

    @Mock
    Characters.Repository repository;

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
        House house = TestUtils.defaultGotHouse();
        when(repository.read(house)).thenReturn(TestUtils.getCharacters(NUMBER_OF_CHARACTERS));

        startActivity(house);

        onView(withId(R.id.content_loading_progress_bar)).check(matches(not(isDisplayed())));
    }

    @Test public void
    should_show_an_specific_number_of_characters() throws Exception{
        House house = TestUtils.defaultGotHouse();
        when(repository.read(house)).thenReturn(TestUtils.getCharacters(NUMBER_OF_CHARACTERS));

        startActivity(house);

        onView(withId(R.id.recycler_view)).check(matches(recyclerViewHasItemCount(NUMBER_OF_CHARACTERS)));
    }

    @Test public void
    should_display_list_when_is_house_with_characters() throws Exception {
        House house = TestUtils.defaultGotHouse();
        when(repository.read(house)).thenReturn(TestUtils.getCharacters(NUMBER_OF_CHARACTERS));

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

    private DetailActivity startActivity(House house){
        Intent intent = new Intent();
        intent.putExtra(DetailActivity.HOUSE_ID, house.getHouseId());
        intent.putExtra(DetailActivity.NAME, house.getHouseName());
        intent.putExtra(DetailActivity.IMAGE_URL, house.getHouseImageUrl());
        return activityTestRule.launchActivity(intent);
    }
}