package es.npatarino.android.gotchallenge.presenter;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.interactor.GetCharactersByHouseUseCase;
import es.npatarino.android.gotchallenge.view.DetailView;
import rx.Subscription;

/**
 * @author Antonio López.
 */
public class GotCharacterListByHousePresenterImp implements GotCharacterListByHousePresenter {

    private DetailView<List<GoTCharacter>> view;
    private GetCharactersByHouseUseCase useCase;

    private Subscription charactersSubscription;

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
    public void onDestroy() {
        charactersSubscription.unsubscribe();
    }

    @Override
    public void init(GoTHouse viewModel) {
        init();
        askForCharacters(viewModel);
    }

    private void askForCharacters(GoTHouse viewModel) {
        charactersSubscription = useCase.execute(viewModel)
                .subscribe(this::onCharactersReceived, this::onError);
    }

    private void onCharactersReceived(List<GoTCharacter> characters){
        view.show(characters);
    }

    private void onError(Throwable error){
        view.error();
    }
}
