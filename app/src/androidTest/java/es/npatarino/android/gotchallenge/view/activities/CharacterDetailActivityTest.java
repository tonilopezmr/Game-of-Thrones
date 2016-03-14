package es.npatarino.android.gotchallenge.view.activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static es.npatarino.android.gotchallenge.matchers.ToolbarMatcher.onToolbarWithTitle;
import static org.hamcrest.core.AllOf.allOf;

/**
 * @author Antonio López.
 */

@RunWith(AndroidJUnit4.class) @LargeTest
public class CharacterDetailActivityTest {

    private static final GoTCharacter KHAL_DROGO = new GoTCharacter();
    private static final String KHAL_DROGO_NAME = "Khal Drogo";
    private static final String KHAL_DROGO_URL = "https://s3-eu-west-1.amazonaws.com/npatarino/got/8310ebeb-cdda-4095-bd5b-f59266d44677.jpg";
    private static final String KHAL_DROGO_DESCRIPTION = "Any description is good";

    @Rule public ActivityTestRule<CharacterDetailActivity> activityTestRule =
            new ActivityTestRule<>(CharacterDetailActivity.class,true, false);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void
    should_character_name_as_toolbar_tittle() throws Exception {
        GoTCharacter character = defaultGotCharacter();

        startActivity(character);

        onToolbarWithTitle(character.getName()).check(matches(isDisplayed()));
    }

    @Test public void
    should_display_description() throws Exception {
        GoTCharacter character = defaultGotCharacter();

        startActivity(character);

        onView(allOf(withId(R.id.tv_description), withText(character.getDescription()))).check(matches(isDisplayed()));
    }

    @Test public void
    should_display_name() throws Exception {
        GoTCharacter character = defaultGotCharacter();

        startActivity(character);

        onView(allOf(withId(R.id.tv_name), withText(character.getName()))).check(matches(isDisplayed()));
    }

    private GoTCharacter defaultGotCharacter(){
        GoTCharacter gotCharacter = KHAL_DROGO;
        gotCharacter.setName(KHAL_DROGO_NAME);
        gotCharacter.setImageUrl(KHAL_DROGO_URL);
        gotCharacter.setDescription(KHAL_DROGO_DESCRIPTION);
        return gotCharacter;
    }

    private CharacterDetailActivity startActivity(GoTCharacter character) {
        Intent intent = new Intent();
        intent.putExtra("description", character.getDescription());
        intent.putExtra("name", character.getName());
        intent.putExtra("imageUrl", character.getImageUrl());
        return activityTestRule.launchActivity(intent);
    }

}