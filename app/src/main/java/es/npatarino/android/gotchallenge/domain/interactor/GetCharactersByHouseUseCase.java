package es.npatarino.android.gotchallenge.domain.interactor;

import com.tonilopezmr.interactorexecutor.Executor;
import com.tonilopezmr.interactorexecutor.MainThread;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.interactor.common.AbstractUseCase;
import es.npatarino.android.gotchallenge.domain.repository.GotCharacterRepository;

/**
 * @author Antonio López.
*/

public class GetCharactersByHouseUseCase extends AbstractUseCase<GoTHouse, List<GoTCharacter>> {

    private GotCharacterRepository repository;

    public GetCharactersByHouseUseCase(Executor executor, MainThread mainThread, GotCharacterRepository repository) {
        super(executor, mainThread);
        this.repository = repository;
    }

    @Override
    public void run() {
        try {
            final List<GoTCharacter> character = repository.read(entity);
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onSuccess(character);
                }
            });
        } catch (final Exception e) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onError(e);
                }
            });
            e.printStackTrace();
        }
    }
}
