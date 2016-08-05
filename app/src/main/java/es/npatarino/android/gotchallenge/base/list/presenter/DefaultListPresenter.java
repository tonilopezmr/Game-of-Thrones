package es.npatarino.android.gotchallenge.base.list.presenter;

import es.npatarino.android.gotchallenge.base.list.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.base.list.view.ViewList;

import java.util.List;

public class DefaultListPresenter<T> extends BasePresenter<ViewList<T>> {

    private GetListUseCase<T> listUseCase;
    public DefaultListPresenter(GetListUseCase<T> listUseCase) {
        this.listUseCase = listUseCase;
    }

    public void loadList() {
        addSubscription(listUseCase.execute()
                .subscribe(this::onListReceived, this::onError));
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
}
