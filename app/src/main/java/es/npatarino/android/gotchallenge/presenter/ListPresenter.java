package es.npatarino.android.gotchallenge.presenter;

import es.npatarino.android.gotchallenge.view.ViewList;

/**
 * @author Antonio López.
 */
public interface ListPresenter<T> extends Presenter<ViewList<T>>{
    void loadList();
}
