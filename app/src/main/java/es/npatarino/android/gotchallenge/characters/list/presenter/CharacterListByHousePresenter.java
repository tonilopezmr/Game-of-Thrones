package es.npatarino.android.gotchallenge.characters.list.presenter;

import es.npatarino.android.gotchallenge.base.Mvp;
import es.npatarino.android.gotchallenge.base.detail.view.DetailView;
import es.npatarino.android.gotchallenge.characters.domain.interactor.GetCharactersByHouseUseCase;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import rx.Subscription;

import java.util.List;

public class CharacterListByHousePresenter implements Mvp.Presenter<DetailView<List<GoTCharacter>>> {

    private DetailView<List<GoTCharacter>> view;
    private GetCharactersByHouseUseCase useCase;

    private Subscription charactersSubscription;

    public CharacterListByHousePresenter(GetCharactersByHouseUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void init() {
        view.initUi();
    }

    @Override
    public void setView(DetailView<List<GoTCharacter>> view) {
        if (view == null) {
            new IllegalArgumentException("oh my god... you are **");
        }
        this.view = view;
    }

    @Override
    public void onDestroy() {
        charactersSubscription.unsubscribe();
    }

    public void init(GoTHouse viewModel) {
        init();
        askForCharacters(viewModel);
    }

    private void askForCharacters(GoTHouse viewModel) {
        charactersSubscription = useCase.execute(viewModel)
                .subscribe(this::onCharactersReceived, this::onError);
    }

    private void onCharactersReceived(List<GoTCharacter> characters) {
        view.show(characters);
    }

    private void onError(Throwable error) {
        view.error();
    }
}
