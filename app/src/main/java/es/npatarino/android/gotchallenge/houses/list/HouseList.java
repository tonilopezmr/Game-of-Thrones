package es.npatarino.android.gotchallenge.houses.list;

import es.npatarino.android.gotchallenge.common.list.presenter.ListPresenter;
import es.npatarino.android.gotchallenge.houses.domain.model.House;

public interface HouseList {

    interface Presenter extends ListPresenter<House> {
    }

}
