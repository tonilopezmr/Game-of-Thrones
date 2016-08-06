package es.npatarino.android.gotchallenge.chat.conversation.presenter;

import android.util.Log;
import es.npatarino.android.gotchallenge.base.list.presenter.BasePresenter;
import es.npatarino.android.gotchallenge.chat.conversation.domain.interactor.GetConversation;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.conversation.view.ConversationView;

public class ConversationPresenter extends BasePresenter<ConversationView> {

    private final String TAG = ConversationDetailPresenter.class.getSimpleName();

    private GetConversation getConversation;

    public ConversationPresenter(GetConversation getConversation) {
        this.getConversation = getConversation;
    }

    public void init(Conversation conversation) {
        super.init();

        get(conversation);
        view.initChat();
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
    }

}
