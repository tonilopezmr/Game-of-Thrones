package es.npatarino.android.gotchallenge.testingtools.viewassertions.toolbar;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.Toolbar;
import android.view.View;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ToolbarTitleViewAssertion implements ViewAssertion {

    private final String toolbarTitle;

    public ToolbarTitleViewAssertion(String toolbarTitle) {
        this.toolbarTitle = toolbarTitle;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        Toolbar toolbar = ((Toolbar) view);

        assertThat(toolbarTitle, is(toolbar.getTitle()));
    }

    public static ToolbarTitleViewAssertion withTitle(String toolbarTitle) {
        return new ToolbarTitleViewAssertion(toolbarTitle);
    }
}
