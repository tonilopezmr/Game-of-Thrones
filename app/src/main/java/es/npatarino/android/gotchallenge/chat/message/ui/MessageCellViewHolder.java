package es.npatarino.android.gotchallenge.chat.message.ui;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.base.ui.CircleTransform;
import es.npatarino.android.gotchallenge.chat.message.domain.model.ImagePayload;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Payload;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.User;
import es.npatarino.android.gotchallenge.chat.message.view.viewmodel.TextPayLoad;

public class MessageCellViewHolder extends RecyclerView.ViewHolder {

    private TextView displayNameTextView;
    private TextView messageTextView;
    private ImageView avatarImageView;
    private LinearLayout messageContainer;

    private LinearLayout rootView;

    public MessageCellViewHolder(View itemView) {
        super(itemView);
        this.rootView = (LinearLayout) itemView;
        displayNameTextView = (TextView) itemView.findViewById(R.id.display_name);
        messageTextView = (TextView) itemView.findViewById(R.id.message_text);
        avatarImageView = (ImageView) itemView.findViewById(R.id.avatar_image);
        messageContainer = (LinearLayout) itemView.findViewById(R.id.message_container);
    }

    public void render(Message message) {
        if (message.isFromMe()) {
            renderMessageForMe(message);
        } else {
            renderMessageFromOthers(message);
        }
    }

    private void renderMessageFromOthers(Message message) {
        User user = message.getUser();
        rootView.setGravity(Gravity.BOTTOM|Gravity.START|Gravity.LEFT);
        displayNameTextView.setVisibility(View.VISIBLE);
        avatarImageView.setVisibility(View.VISIBLE);

        Picasso.with(avatarImageView.getContext())
                .load(user.getImageUrl())
                .transform(new CircleTransform())
                .placeholder(R.drawable.ned_head_light)
                .into(avatarImageView);


        displayNameTextView.setText(user.getName());
        displayPayLoad(message.getPayload());
        messageContainer.setBackgroundResource(R.drawable.background_message_from_others);
    }

    private void displayPayLoad(Payload payload) {
        if (payload instanceof TextPayLoad){
            TextPayLoad textPayload = (TextPayLoad) payload;
            messageTextView.setText(textPayload.getMessage());
        } else if (payload instanceof ImagePayload) {
            //TODO continue
        }
    }

    private void renderMessageForMe(Message message) {
        rootView.setGravity(Gravity.BOTTOM|Gravity.END|Gravity.RIGHT);

        displayNameTextView.setVisibility(View.GONE);
        avatarImageView.setVisibility(View.GONE);
        messageContainer.setBackgroundResource(R.drawable.background_message_from_me);

        displayPayLoad(message.getPayload());
    }

}
