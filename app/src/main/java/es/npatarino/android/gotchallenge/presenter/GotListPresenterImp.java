package es.npatarino.android.gotchallenge.presenter;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.view.ViewList;

/**
 * @author Antonio López.
 */
public class GotListPresenterImp<T> implements ListPresenter<T> {

    private ViewList<T> view;
    private GetListUseCase<T> listUseCase;

    public GotListPresenterImp(GetListUseCase<T> listUseCase) {
        this.listUseCase = listUseCase;
    }

    @Override
    public void loadList() {
        listUseCase.execute(new GetListUseCase.Callback<T>() {
            @Override
            public void onListLoaded(List<T> entityList) {
                view.showList(entityList);
            }

            @Override
            public void onError(Exception exception) {
                view.error();
            }
        });
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
}
