package es.npatarino.android.gotchallenge.chat.conversation.presenter;

import android.util.Log;
import es.npatarino.android.gotchallenge.base.Mvp;
import es.npatarino.android.gotchallenge.base.list.presenter.BasePresenter;
import es.npatarino.android.gotchallenge.chat.conversation.usecases.GetConversation;
import es.npatarino.android.gotchallenge.chat.conversation.model.Conversation;

public class ConversationPresenter extends BasePresenter<ConversationPresenter.View> {

    public interface View extends Mvp.View {

        void show(Conversation conversation);
        void initChat();
    }

    private final String TAG = ConversationPresenter.class.getSimpleName();

    private GetConversation getConversation;

    public ConversationPresenter(GetConversation getConversation) {
        this.getConversation = getConversation;
    }

    public void init(Conversation conversation) {
        super.init();

        get(conversation);
    }

    private void get(Conversation conversation) {
        addSubscription(getConversation.execute(conversation)
                .subscribe(this::show, this::onError));
    }

    private void onError(Throwable throwable) {
        view.error();
        Log.e(TAG, "onError: " + throwable.getMessage(), throwable);
    }

    private void show(Conversation conversation) {
        view.show(conversation);
        view.initChat();
    }
}
