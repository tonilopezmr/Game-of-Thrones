package es.npatarino.android.gotchallenge.testingtools.idlingResource;

import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.Toolbar;

public class WaitForToolbarLogo implements IdlingResource {

  private Toolbar toolbar;
  private ResourceCallback callback;

  public WaitForToolbarLogo(Toolbar toolbar) {
    this.toolbar = toolbar;
  }

  @Override
  public String getName() {
    return getClass().getSimpleName();
  }

  @Override
  public boolean isIdleNow() {
    boolean isIdle = toolbar.getLogoDescription() != null;

    if (isIdle) {
      this.callback.onTransitionToIdle();
    }

    return isIdle;
  }

  @Override
  public void registerIdleTransitionCallback(ResourceCallback callback) {
    this.callback = callback;
  }
}
