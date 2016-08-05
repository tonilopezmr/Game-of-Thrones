package es.npatarino.android.gotchallenge.chat.message.presenter;

import android.util.Log;
import es.npatarino.android.gotchallenge.base.list.presenter.BasePresenter;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.message.domain.interactor.SubscribeToMessage;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.view.MessageView;

public class MessagePresenter extends BasePresenter<MessageView> {

    private final String TAG = MessagePresenter.class.getSimpleName();

    private SubscribeToMessage subscribeToMessageUseCase;

    public MessagePresenter(SubscribeToMessage subscribeToMessageUseCase) {
        this.subscribeToMessageUseCase = subscribeToMessageUseCase;
    }

    public void init(Conversation conversation){
        addSubscription(subscribeToMessageUseCase.execute(conversation)
                .subscribe(this::showMessage, this::onError));
        init();
    }

    private void onError(Throwable throwable) {
        view.error();
        Log.e(TAG, "onError: " + throwable.getMessage());
    }

    private void showMessage(Message message){
        view.showMessage(message);
    }
}
