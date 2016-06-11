package es.npatarino.android.gotchallenge.presenter.common;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCase;
import es.npatarino.android.gotchallenge.view.ViewList;
import rx.Subscription;

public class ListPresenterImp<T> implements ListPresenter<T> {

    private ViewList<T> view;
    private GetListUseCase<T> listUseCase;

    private Subscription subscription;

    public ListPresenterImp(GetListUseCase<T> listUseCase) {
        this.listUseCase = listUseCase;
    }

    @Override
    public void loadList() {
        subscription = listUseCase.execute().subscribe(this::onListReceived, this::onError);
    }

    private void onError(Throwable throwable) {
        view.error();
    }

    private void onListReceived(List<T> entityList) {
        view.showList(entityList);
    }


    @Override
    public void init() {
        loadList();
    }

    @Override
    public void setView(ViewList<T> view) {
        if (view == null) new IllegalArgumentException("oh my god... you are **");
        this.view = view;
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
    }
}
