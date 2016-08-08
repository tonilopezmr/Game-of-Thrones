package es.npatarino.android.gotchallenge.chat.ui;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.ui.MessageCellViewHolder;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<MessageCellViewHolder> {

    private SortedList<Message> messageList;

    public ChatAdapter() {
        messageList = new SortedList<Message>(Message.class, new SortedListAdapterCallback<Message>(this) {
            @Override
            public int compare(Message o1, Message o2) {
                return o1.compareTo(o2);
            }

            @Override
            public boolean areContentsTheSame(Message oldItem, Message newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Message item1, Message item2) {
                return item1.getId().equals(item2.getId());
            }
        });
    }

    public void add(Message message) {
        messageList.add(message);
        notifyDataSetChanged();
    }

    public void addAll(List<Message> messages) {
        messageList.addAll(messages);
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
