package es.npatarino.android.gotchallenge.chat.message.presenter;

import android.util.Log;
import es.npatarino.android.gotchallenge.base.list.presenter.BasePresenter;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.message.domain.interactor.GetMessages;
import es.npatarino.android.gotchallenge.chat.message.domain.interactor.SendMessage;
import es.npatarino.android.gotchallenge.chat.message.domain.interactor.SubscribeToMessage;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.view.MessageView;

import java.util.List;

public class MessagePresenter extends BasePresenter<MessageView> {

    private final String TAG = MessagePresenter.class.getSimpleName();

    private SubscribeToMessage subscribeToMessageUseCase;
    private GetMessages getMessages;
    private SendMessage sendMessage;

    private Conversation conversation;

    public MessagePresenter(SubscribeToMessage subscribeToMessageUseCase,
                            GetMessages getMessages,
                            SendMessage sendMessage) {
        this.subscribeToMessageUseCase = subscribeToMessageUseCase;
        this.getMessages = getMessages;
        this.sendMessage = sendMessage;
    }

    public void init(Conversation conversation) {
        super.init();
        this.conversation = conversation;

        addSubscription(subscribeToMessageUseCase.execute(conversation)
                .subscribe(this::showMessage, this::onShowMessageError));
        addSubscription(getMessages.execute(conversation)
                .subscribe(this::showMessages, this::onShowMessagesError));
    }

    public void send(Message message) {
        sendMessage.execute(message, conversation);
    }

    private void onShowMessageError(Throwable throwable) {
        view.errorOnShowMessage();
        Log.e(TAG, "onShowMessageError: " + throwable.getMessage(), throwable);
    }

    private void onShowMessagesError(Throwable throwable) {
        view.errorOnShowMessages();
        Log.e(TAG, "onShowMessagesError: " + throwable.getMessage(), throwable);
    }

    private void showMessages(List<Message> messages) {
        view.showMessages(messages);
        Log.i(TAG, "showMessages: "+ messages.size());
    }

    private void showMessage(Message message) {
        message.setTimestamp(System.currentTimeMillis());
        view.showMessage(message);
        Log.i(TAG, "showMessage message id: " + message.getId());
    }
}
