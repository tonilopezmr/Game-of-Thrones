package es.npatarino.android.gotchallenge.presenter;

import es.npatarino.android.gotchallenge.view.DetailView;

/**
 * @author Antonio López.
 */
public interface DetailPresenter<T> extends Presenter<DetailView<T>>{
    void init(T viewModel);
}
