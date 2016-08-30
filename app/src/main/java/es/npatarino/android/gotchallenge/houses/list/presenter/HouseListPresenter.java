package es.npatarino.android.gotchallenge.houses.list.presenter;

import es.npatarino.android.gotchallenge.base.list.usecases.GetListUseCase;
import es.npatarino.android.gotchallenge.base.list.presenter.DefaultListPresenter;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;

public class HouseListPresenter extends DefaultListPresenter<GoTHouse> {
    public HouseListPresenter(GetListUseCase<GoTHouse> listUseCase) {
        super(listUseCase);
    }
}
