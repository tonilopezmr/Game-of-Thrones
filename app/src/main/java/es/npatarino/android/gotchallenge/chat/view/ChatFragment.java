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
import es.npatarino.android.gotchallenge.chat.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.domain.model.User;
import es.npatarino.android.gotchallenge.chat.view.viewmodel.TextPayload;
import net.mobindustry.emojilib.EmojiPanel;

public class ChatFragment extends Fragment {

    private RecyclerView messageRecyclerView;
    private LinearLayoutManager recyclerViewManager;
    private ChatAdapter adapter;

    //emoji section
    private EmojiPanel emojiPanel;
    private FrameLayout rootEmojiKeyBoard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chat_fragment, container, false);


        initUI(rootView);

        return rootView;
    }

    public void initUI(View rootView) {
        messageRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        initEmojiPanel(rootView);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //hide keyboard start

        adapter = new ChatAdapter();
        adapter.add(new Message("asdf", new User("asdf", "Sergio",
                "https://lh3.googleusercontent.com/-jDZYxFuC6X8/AAAAAAAAAAI/AAAAAAAAA-s/Wch5ttRQSQ0/s120-c/photo.jpg"),
                234234234, false, new TextPayload("Hey buddy!")));
        adapter.add(new Message("asdf", new User("asdf", "Celia",
                "https://lh3.googleusercontent.com/-wF2cRii1VJM/AAAAAAAAAAI/AAAAAAAAAEI/ouEFoadEehk/s120-c/photo.jpg"),
                234234234, false, new TextPayload("Vamoh cohonee vente ya para malaga\nQue tengo una terraza bien hermosa como yo :D")));
        adapter.add(new Message("asdf", new User("asdf", "Cantero",
                "https://lh3.googleusercontent.com/-8980rlwDIss/AAAAAAAAAAI/AAAAAAAAAAA/dRMUwVLajoc/s120-c/photo.jpg"),
                234234234, false, new TextPayload("Vamonoh illoh")));

        initRecyclerView(adapter);
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


}
