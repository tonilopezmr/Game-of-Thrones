package es.npatarino.android.gotchallenge.base.list.presenter;

import java.util.List;

import es.npatarino.android.gotchallenge.base.Mvp;
import es.npatarino.android.gotchallenge.base.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.base.list.view.ViewList;
import rx.Subscription;

public class DefaultListPresenter<T> implements Mvp.Presenter<ViewList<T>> {

    private ViewList<T> view;
    private GetListUseCase<T> listUseCase;

    private Subscription subscription;

    public DefaultListPresenter(GetListUseCase<T> listUseCase) {
        this.listUseCase = listUseCase;
    }

    public void loadList() {
        subscription = listUseCase.execute()
                .subscribe(this::onListReceived, this::onError);
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
