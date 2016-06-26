package es.npatarino.android.gotchallenge.common.list.presenter;

import es.npatarino.android.gotchallenge.common.Mvp;
import es.npatarino.android.gotchallenge.common.list.view.ViewList;

public interface ListPresenter<T> extends Mvp.Presenter<ViewList<T>> {
    void loadList();
}
