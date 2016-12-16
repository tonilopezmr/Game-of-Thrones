package es.npatarino.android.gotchallenge.base.list.presenter;

import es.npatarino.android.gotchallenge.base.Mvp;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<T extends Mvp.View> implements Mvp.Presenter<T> {

  private final CompositeSubscription subscriptions = new CompositeSubscription();
  protected T view;

  @Override
  public void init() {
    checkView(view);
    view.initUi();
  }

  @Override
  public void setView(T view) {
    checkView(view);
    this.view = view;
  }

  @Override
  public void onDestroy() {
    subscriptions.unsubscribe();
  }

  public void addSubscription(Subscription subscription) {
    subscriptions.add(subscription);
  }

  private void checkView(T view) {
    if (view == null) new IllegalArgumentException("oh my god... must view not null");
  }
}
