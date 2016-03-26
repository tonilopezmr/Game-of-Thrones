package es.npatarino.android.gotchallenge.presenter;

import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCase;
import es.npatarino.android.gotchallenge.presenter.common.GotListPresenterImp;

/**
 * @author Antonio López.
 */
public class HouseListPresenterImp extends GotListPresenterImp<GoTHouse> implements HouseListPresenter{
    public HouseListPresenterImp(GetListUseCase<GoTHouse> listUseCase) {
        super(listUseCase);
    }
}
