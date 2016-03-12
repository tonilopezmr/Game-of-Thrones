package es.npatarino.android.gotchallenge.presenter;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.interactor.GetCharactersByHouseUseCase;
import es.npatarino.android.gotchallenge.domain.interactor.common.UseCase;
import es.npatarino.android.gotchallenge.view.DetailView;

/**
 * @author Antonio López.
 */
public class GotCharacterListByHousePresenterImp implements GotCharacterListByHousePresenter {

    private DetailView<List<GoTCharacter>> view;
    private UseCase<GoTHouse, List<GoTCharacter>> useCase;

    public GotCharacterListByHousePresenterImp(GetCharactersByHouseUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void init() {
        view.initUi();
    }

    @Override
    public void setView(DetailView<List<GoTCharacter>> view) {
        if (view == null) new IllegalArgumentException("oh my god... you are **");
        this.view = view;
    }


    @Override
    public void init(GoTHouse viewModel) {
        init();
        useCase.execute(viewModel, new UseCase.Callback<List<GoTCharacter>>() {
            @Override
            public void onSuccess(List<GoTCharacter> entity) {
                view.show(entity);
            }

            @Override
            public void onError(Exception exception) {
                view.error();
            }
        });
    }
}
