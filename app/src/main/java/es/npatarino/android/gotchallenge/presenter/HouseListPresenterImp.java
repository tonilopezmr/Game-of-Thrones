package es.npatarino.android.gotchallenge.presenter;

import es.npatarino.android.gotchallenge.houses.domain.model.House;
import es.npatarino.android.gotchallenge.common.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.presenter.common.ListPresenterImp;

public class HouseListPresenterImp extends ListPresenterImp<House> implements HouseListPresenter{
    public HouseListPresenterImp(GetListUseCase<House> listUseCase) {
        super(listUseCase);
    }
}
