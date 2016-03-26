package es.npatarino.android.gotchallenge.presenter.common;

import es.npatarino.android.gotchallenge.view.ViewList;

/**
 * @author Antonio López.
 */
public interface ListPresenter<T> extends Presenter<ViewList<T>>{
    void loadList();
}
