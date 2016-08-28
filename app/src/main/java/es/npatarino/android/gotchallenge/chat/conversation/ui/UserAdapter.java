package es.npatarino.android.gotchallenge.chat.conversation.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<User> userList;

    public UserAdapter() {
        userList = new ArrayList<>();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cell_user, parent, false));
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.render(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void addAll(List<User> list) {
        userList.addAll(list);
        notifyDataSetChanged();
    }
}
