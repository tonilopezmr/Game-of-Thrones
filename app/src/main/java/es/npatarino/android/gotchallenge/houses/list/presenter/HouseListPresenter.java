package es.npatarino.android.gotchallenge.houses.list.presenter;

import es.npatarino.android.gotchallenge.houses.domain.model.House;
import es.npatarino.android.gotchallenge.common.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.common.list.presenter.DefaultListPresenter;
import es.npatarino.android.gotchallenge.houses.list.HouseList;

public class HouseListPresenter extends DefaultListPresenter<House> implements HouseList.Presenter {
    public HouseListPresenter(GetListUseCase<House> listUseCase) {
        super(listUseCase);
    }
}
