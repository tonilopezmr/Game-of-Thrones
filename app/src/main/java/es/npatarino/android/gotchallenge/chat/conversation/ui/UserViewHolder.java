package es.npatarino.android.gotchallenge.chat.conversation.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.base.ui.CircleTransform;
import es.npatarino.android.gotchallenge.chat.conversation.model.User;

public class UserViewHolder extends RecyclerView.ViewHolder {

  private TextView displayNameTextView;
  private TextView stateTextView;
  private ImageView avatarImageView;

  public UserViewHolder(View itemView) {
    super(itemView);
    displayNameTextView = (TextView) itemView.findViewById(R.id.display_name);
    stateTextView = (TextView) itemView.findViewById(R.id.display_state);
    avatarImageView = (ImageView) itemView.findViewById(R.id.avatar_image);
  }

  public void render(User user) {
    displayNameTextView.setText(user.getName());
    stateTextView.setText(user.getState());
    Picasso.with(avatarImageView.getContext())
        .load(user.getImageUrl())
        .transform(new CircleTransform())
//                .placeholder(R.drawable.ned_head_light)
        .into(avatarImageView);
  }
}
