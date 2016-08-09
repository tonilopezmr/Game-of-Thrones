package es.npatarino.android.gotchallenge.testingtools.viewassertions.toolbar;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.Toolbar;
import android.view.View;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ToolbarSubtitleViewAssertion implements ViewAssertion {

    private final String toolbarSubtitle;

    public ToolbarSubtitleViewAssertion(String toolbarSubtitle) {
        this.toolbarSubtitle = toolbarSubtitle;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        Toolbar toolbar = ((Toolbar) view);

        assertThat(toolbarSubtitle, is(toolbar.getSubtitle()));
    }

    public static ToolbarSubtitleViewAssertion withSubtitle(String subtitle) {
        return new ToolbarSubtitleViewAssertion(subtitle);
    }
}
