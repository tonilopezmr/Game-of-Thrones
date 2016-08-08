package es.npatarino.android.gotchallenge.chat.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;
import es.npatarino.android.gotchallenge.GotChallengeApplication;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.base.di.modules.ActivityModule;
import es.npatarino.android.gotchallenge.base.ui.messages.ErrorManager;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.di.DaggerChatComponent;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.presenter.MessagePresenter;
import es.npatarino.android.gotchallenge.chat.message.view.MessageView;
import es.npatarino.android.gotchallenge.chat.message.view.viewmodel.TextPayLoad;
import net.mobindustry.emojilib.EmojiPanel;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class ChatFragment extends Fragment implements MessageView {

    private RecyclerView messageRecyclerView;
    private LinearLayoutManager recyclerViewManager;
    private ChatAdapter adapter;
    private View rootView;

    //emoji section
    private EmojiPanel emojiPanel;
    private FrameLayout rootEmojiKeyBoard;

    @Inject
    ErrorManager errorManager;
    @Inject
    MessagePresenter messagePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initDagger();
        View rootView = inflater.inflate(R.layout.chat_fragment, container, false);
        initFragment(rootView);

        return rootView;
    }

    private void initDagger() {
        GotChallengeApplication app = (GotChallengeApplication) getActivity().getApplication();
        DaggerChatComponent.builder()
                .appComponent(app.getAppComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build().inject(this);
    }

    private void initFragment(View rootView) {
        this.rootView = rootView;
        messageRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        initEmojiPanel(rootView);

        String id = getArguments().getString(ChatActivity.CONVER_ID_KEY);
        messagePresenter.setView(this);
        messagePresenter.init(new Conversation(id, "", Collections.emptyList(), null, null));
    }

    private void initEmojiPanel(View rootView) {
        rootEmojiKeyBoard = (FrameLayout) rootView.findViewById(R.id.root_frame_layout);
        emojiPanel = new EmojiPanel(getActivity(), rootEmojiKeyBoard, new EmojiPanel.EmojiClickCallback() {
            @Override
            public void sendClicked(Spannable span) {
                if (span.length() != 0) {
                    long timestamp = System.currentTimeMillis();
                    messagePresenter.send(new Message("" + timestamp, null, timestamp, true, new TextPayLoad(span)));
                    scrollToBottom();
                }
            }

            @Override
            public void stickerClicked(String path) {
                //Todo do nothing, show sticker
            }
        });

        emojiPanel.iconsInit(R.drawable.ic_send_smile_levels_default, R.drawable.ic_send);
        emojiPanel.init();

        initOnBackPressed(rootView);
    }

    private void initOnBackPressed(View rootView) {
        rootView.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (emojiPanel.isEmojiAttached()) {
                    emojiPanel.dissmissEmojiPopup();
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        });
    }

    private void initRecyclerView(ChatAdapter chatAdapter) {
        messageRecyclerView.setAdapter(chatAdapter);

        recyclerViewManager = new LinearLayoutManager(getActivity());
        recyclerViewManager.setStackFromEnd(true);

        messageRecyclerView.setLayoutManager(recyclerViewManager);
        messageRecyclerView.setHasFixedSize(true);
    }

    private void scrollToBottom() {
        messageRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void onPause() {
        super.onPause();
        emojiPanel.dissmissEmojiPopup();
    }


    @Override
    public void showMessage(Message message) {
        adapter.add(message);
        scrollToBottom();
    }

    @Override
    public void showMessages(List<Message> messages) {
        adapter.addAll(messages);
        scrollToBottom();
    }

    @Override
    public void errorOnShowMessage() {
        errorManager.showError(rootView, "Error on show a message");
    }

    @Override
    public void errorOnShowMessages() {
        errorManager.showError(rootView, "Error on show a message list");
    }

    @Override
    public void initUi() {
        adapter = new ChatAdapter();
        initRecyclerView(adapter);
        hideKeyboard();
    }

    private void hideKeyboard() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void error() {
        Toast.makeText(getContext(), "Some error", Toast.LENGTH_LONG).show();
    }
}
