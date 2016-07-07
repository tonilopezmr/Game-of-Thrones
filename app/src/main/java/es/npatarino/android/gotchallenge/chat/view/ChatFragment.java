package es.npatarino.android.gotchallenge.chat.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.chat.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.domain.model.TextPayload;
import es.npatarino.android.gotchallenge.chat.domain.model.User;

public class ChatFragment extends Fragment {

    private EditText newTextMessageEditText;
    private ImageView sendButton;
    private RecyclerView messageRecyclerView;
    private LinearLayoutManager recyclerViewManager;
    private ChatAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chat_fragment, container, false);

        newTextMessageEditText = (EditText) rootView.findViewById(R.id.new_message_edittext);
        sendButton = (ImageView) rootView.findViewById(R.id.send_message_button);
        sendButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_send, null));
        messageRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        initUI();

        return rootView;
    }

    public void initUI() {
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
        
        sendButton.setOnClickListener(v -> {
            String newMessage = newTextMessageEditText.getText().toString();
            if (newMessage.length() != 0 && !newMessage.trim().isEmpty()){
                adapter.add(new Message("asdf", null, 234234234, true, new TextPayload(newMessage)));
                newTextMessageEditText.setText("");
                scrollToBottom();
            }
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
}
