package es.npatarino.android.gotchallenge.presenter;

import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCase;
import es.npatarino.android.gotchallenge.presenter.common.ListPresenterImp;

public class HouseListPresenterImp extends ListPresenterImp<House> implements HouseListPresenter{
    public HouseListPresenterImp(GetListUseCase<House> listUseCase) {
        super(listUseCase);
    }
}
