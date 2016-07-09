package es.npatarino.android.gotchallenge.chat.message.presenter;

import es.npatarino.android.gotchallenge.base.Mvp;
import es.npatarino.android.gotchallenge.chat.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.interactor.SubscribeToMessage;
import es.npatarino.android.gotchallenge.chat.message.view.MessageView;
import rx.Subscription;

public class MessagePresenter implements Mvp.Presenter<MessageView> {


    private SubscribeToMessage subscribeToMessageUseCase;
    private Subscription subscription;
    private MessageView view;

    public MessagePresenter(SubscribeToMessage subscribeToMessageUseCase) {
        this.subscribeToMessageUseCase = subscribeToMessageUseCase;
    }

    @Override
    public void init() {
        view.initUi();
    }

    public void init(Conversation conversation){
        subscription = subscribeToMessageUseCase.execute(conversation)
                .subscribe(this::showMessage, this::onError);
        init();
    }

    private void onError(Throwable throwable) {

    }

    @Override
    public void setView(MessageView view) {
        this.view = view;
    }

    private void showMessage(Message message){
        view.showMessage(message);
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
    }
}
