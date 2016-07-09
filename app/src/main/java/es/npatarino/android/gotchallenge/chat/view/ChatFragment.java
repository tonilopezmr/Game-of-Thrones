package es.npatarino.android.gotchallenge.chat.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.view.*;
import android.widget.FrameLayout;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.base.di.ExecutorThread;
import es.npatarino.android.gotchallenge.base.di.UiThread;
import es.npatarino.android.gotchallenge.chat.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.data.MessageRepository;
import es.npatarino.android.gotchallenge.chat.message.interactor.SubscribeToMessage;
import es.npatarino.android.gotchallenge.chat.message.presenter.MessagePresenter;
import es.npatarino.android.gotchallenge.chat.message.view.MessageView;
import es.npatarino.android.gotchallenge.chat.view.viewmodel.TextPayload;
import net.mobindustry.emojilib.EmojiPanel;
import rx.Scheduler;

import javax.inject.Inject;
import java.util.Collections;

public class ChatFragment extends Fragment implements MessageView {

    private RecyclerView messageRecyclerView;
    private LinearLayoutManager recyclerViewManager;
    private ChatAdapter adapter;

    //emoji section
    private EmojiPanel emojiPanel;
    private FrameLayout rootEmojiKeyBoard;

    @Inject @UiThread
    Scheduler uiThread;
    @Inject @ExecutorThread
    Scheduler executorThread;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chat_fragment, container, false);

        initFragment(rootView);

        return rootView;
    }

    private void initFragment(View rootView) {
        messageRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        initEmojiPanel(rootView);

        MessageRepository messageRepository = new MessageRepository();
        SubscribeToMessage subscribeToMessage = new SubscribeToMessage(messageRepository, uiThread, executorThread);
        MessagePresenter messagePresenter = new MessagePresenter(subscribeToMessage);
        messagePresenter.init(new Conversation("1", "con", Collections.emptyList(), null, null));
    }

    private void initEmojiPanel(View rootView) {
        rootEmojiKeyBoard = (FrameLayout) rootView.findViewById(R.id.root_frame_layout);
        emojiPanel = new EmojiPanel(getActivity(), rootEmojiKeyBoard, new EmojiPanel.EmojiClickCallback() {
            @Override
            public void sendClicked(Spannable span) {
                if (span.length() != 0){
                    adapter.add(new Message("asdf", null, 234234234, true, new TextPayload(span)));
                    scrollToBottom();
                }
            }

            @Override
            public void stickerClicked(String path) {
                //Todo do nothing
            }
        });

        emojiPanel.iconsInit(R.drawable.ic_send_smile_levels_default, R.drawable.ic_send);
        emojiPanel.init();

        initOnBackPressed(rootView);
    }

    private void initOnBackPressed(View rootView) {
        rootView.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK){
                if (emojiPanel.isEmojiAttached()) {
                    emojiPanel.dissmissEmojiPopup();
                    return true;
                }else{
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
    }

    @Override
    public void initUi() {
        adapter = new ChatAdapter();
        initRecyclerView(adapter);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //hide keyboard start
    }

    @Override
    public void error() {

    }
}
