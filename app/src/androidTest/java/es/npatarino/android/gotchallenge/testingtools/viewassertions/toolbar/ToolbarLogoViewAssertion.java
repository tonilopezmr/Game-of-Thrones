package es.npatarino.android.gotchallenge.testingtools.viewassertions.toolbar;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.Toolbar;
import android.view.View;

import static org.junit.Assert.assertNotNull;

public class ToolbarLogoViewAssertion implements ViewAssertion {

  public static ToolbarLogoViewAssertion hasLogo() {
    return new ToolbarLogoViewAssertion();
  }

  @Override
  public void check(View view, NoMatchingViewException noViewFoundException) {
    Toolbar toolbar = ((Toolbar) view);

    assertNotNull(toolbar.getLogo());
  }
}
