package es.npatarino.android.gotchallenge.chat.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import es.npatarino.android.gotchallenge.R;

public class ChatFragment extends Fragment {

    private EditText newTextMessageEditText;
    private View sendButton;
    private RecyclerView messageRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chat_fragment, container, false);

        newTextMessageEditText = (EditText) rootView.findViewById(R.id.new_message_edittext);
        sendButton = rootView.findViewById(R.id.send_message_button);
        messageRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        initUI();

        return rootView;
    }

    public void initUI() {
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        messageRecyclerView.setHasFixedSize(true);
    }


}
