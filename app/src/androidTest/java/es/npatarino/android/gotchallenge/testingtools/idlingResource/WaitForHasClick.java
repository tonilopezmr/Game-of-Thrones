package es.npatarino.android.gotchallenge.testingtools.idlingResource;

import android.support.test.espresso.IdlingResource;
import android.view.View;

public class WaitForHasClick implements IdlingResource {

  private View view;
  private ResourceCallback callback;

  public WaitForHasClick(View view) {
    this.view = view;
  }

  @Override
  public String getName() {
    return getClass().getSimpleName();
  }

  @Override
  public boolean isIdleNow() {
    boolean isIdle = true;

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
      isIdle = view.hasOnClickListeners();
    }

    if (isIdle) {
      callback.onTransitionToIdle();
    }
    return isIdle;
  }

  @Override
  public void registerIdleTransitionCallback(ResourceCallback callback) {
    this.callback = callback;
  }
}
