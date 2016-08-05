package es.npatarino.android.gotchallenge.chat.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.ui.MessageCellViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<MessageCellViewHolder> {

    private List<Message> messageList;

    public ChatAdapter() {
        messageList = new ArrayList<>();
    }

    public void setAll(List<Message> messages){
        messageList = new ArrayList<>(messages);
    }

    public void add(Message message){
        messageList.add(message);
        notifyDataSetChanged();
    }

    @Override
    public MessageCellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageCellViewHolder(LayoutInflater
                                        .from(parent.getContext())
                                        .inflate(R.layout.cell_message, parent, false));
    }

    @Override
    public void onBindViewHolder(MessageCellViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.render(message);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
