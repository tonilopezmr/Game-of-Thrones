package es.npatarino.android.gotchallenge.characters.list;

import java.util.List;

import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.common.Mvp;
import es.npatarino.android.gotchallenge.houses.domain.model.House;
import es.npatarino.android.gotchallenge.common.detail.view.DetailView;

public interface CharacterList {

    interface Presenter extends es.npatarino.android.gotchallenge.common.list.presenter.ListPresenter<GoTCharacter> {
    }

    interface ByHousePresenter extends Mvp.Presenter<DetailView<List<GoTCharacter>>> {
        void init(House viewModel);
    }
}
