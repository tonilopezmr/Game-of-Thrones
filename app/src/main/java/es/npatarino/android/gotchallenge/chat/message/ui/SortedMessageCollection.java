package es.npatarino.android.gotchallenge.chat.message.ui;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import com.pedrogomez.renderers.AdapteeCollection;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;

import java.util.Collection;
import java.util.List;

public class SortedMessageCollection implements AdapteeCollection<Message> {

    private SortedList<Message> messageList;

    public SortedMessageCollection() {
    }

    public void init(RecyclerView.Adapter adapter) {
        messageList = new SortedList<Message>(Message.class, new SortedListAdapterCallback<Message>(adapter) {
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

    @Override
    public int size() {
        return messageList.size();
    }

    @Override
    public Message get(int index) {
        return messageList.get(index);
    }

    @Override
    public boolean add(Message element) {
        messageList.add(element);
        return true;
    }

    @Override
    public boolean remove(Object element) {
        return messageList.remove(((Message) element));
    }

    @Override
    public boolean addAll(Collection<? extends Message> elements) {
        messageList.addAll(((List<Message>) elements));
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> elements) {
        for (int i = 0; i < elements.size(); i++) {
            messageList.removeItemAt(i);
        }
        return true;
    }

    @Override
    public void clear() {
        messageList.clear();
    }
}
