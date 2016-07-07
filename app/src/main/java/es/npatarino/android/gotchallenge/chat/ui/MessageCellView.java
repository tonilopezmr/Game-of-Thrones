package es.npatarino.android.gotchallenge.chat.ui;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.chat.domain.model.ImagePayload;
import es.npatarino.android.gotchallenge.chat.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.domain.model.Payload;
import es.npatarino.android.gotchallenge.chat.domain.model.TextPayload;

public class MessageCellView extends RecyclerView.ViewHolder {

    TextView displayNameTextView;
    TextView messageTextView;
    ImageView avatarImageView;
    View messageContainer;

    public MessageCellView(View itemView) {
        super(itemView);
        displayNameTextView = (TextView) itemView.findViewById(R.id.display_name);
        messageTextView = (TextView) itemView.findViewById(R.id.message_text);
        avatarImageView = (ImageView) itemView.findViewById(R.id.avatar_image);
        messageContainer = itemView.findViewById(R.id.message_container);
    }

    public void render(Message message) {
        if (message.isFromMe()) {
            renderMessageForMe(message);
        } else {
            renderMessageFromOthers(message);
        }
    }

    private void renderMessageFromOthers(Message message) {
        displayNameTextView.setText(message.getFrom());
        displayPayLoad(message.getPayload());
        GradientDrawable bgShape = (GradientDrawable) messageContainer.getBackground();
        bgShape.setCornerRadii(new float[]{0f, 10f, 10f, 10f});
    }

    private void displayPayLoad(Payload payload) {
        if (payload instanceof TextPayload){
            TextPayload textPayload = (TextPayload) payload;
            messageTextView.setText(textPayload.getMessageText());
        } else if (payload instanceof ImagePayload) {
            //TODO continue
        }
    }

    private void renderMessageForMe(Message message) {
        displayNameTextView.setVisibility(View.GONE);
        avatarImageView.setVisibility(View.GONE);
        GradientDrawable bgShape = (GradientDrawable) messageContainer.getBackground();
        bgShape.setCornerRadii(new float[]{10f, 0f, 10f, 10f});

        displayPayLoad(message.getPayload());
    }

}
