package es.npatarino.android.gotchallenge.chat.message.ui;

import android.support.v7.widget.AppCompatDrawableManager;
import android.text.Spannable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.pedrogomez.renderers.Renderer;
import com.squareup.picasso.Picasso;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.base.ui.CircleTransform;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.User;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Payload;
import es.npatarino.android.gotchallenge.chat.message.view.viewmodel.ImagePayload;
import es.npatarino.android.gotchallenge.chat.message.view.viewmodel.TextPayLoad;

public class MessageRenderer extends Renderer<Message> {

    private TextView displayNameTextView;
    private TextView messageTextView;
    private ImageView avatarImageView;
    private LinearLayout messageContainer;
    private ImageView messageImageView;

    private LinearLayout rootView;

    @Override
    protected void setUpView(View rootView) {
        this.rootView = (LinearLayout) rootView;
        displayNameTextView = (TextView) rootView.findViewById(R.id.display_name);
        messageTextView = (TextView) rootView.findViewById(R.id.message_text);
        messageImageView = (ImageView) rootView.findViewById(R.id.message_image);
        avatarImageView = (ImageView) rootView.findViewById(R.id.avatar_image);
        messageContainer = (LinearLayout) rootView.findViewById(R.id.message_container);
    }

    @Override
    protected void hookListeners(View rootView) {

    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cell_message, parent, false);
    }

    @Override
    public void render() {
        Message message = getContent();

        if (message.isFromMe()) {
            renderMessageForMe(message);
        } else {
            renderMessageFromOthers(message);
        }
    }

    private void renderMessageFromOthers(Message message) {
        User user = message.getUser();
        rootView.setGravity(Gravity.BOTTOM | Gravity.START);
        displayNameTextView.setVisibility(View.VISIBLE);
        avatarImageView.setVisibility(View.VISIBLE);

        Picasso.with(avatarImageView.getContext())
                .load(user.getImageUrl())
                .transform(new CircleTransform())
                .placeholder(AppCompatDrawableManager.get().getDrawable(avatarImageView.getContext(),
                        R.drawable.ned_head_light))
                .into(avatarImageView);


        displayNameTextView.setText(user.getName());
        displayPayLoad(message.getPayload());
        messageContainer.setBackgroundResource(R.drawable.background_message_from_others);
    }

    private void displayPayLoad(Payload payload) {
        if (payload instanceof ImagePayload) {
            ImagePayload imagePayload = (ImagePayload) payload;
            Picasso.with(messageImageView.getContext())
                    .load(imagePayload.getImageMessage())
                    .placeholder(AppCompatDrawableManager.get().getDrawable(avatarImageView.getContext(),
                            R.drawable.ned_head_light))
                    .into(messageImageView);
        } else {
            messageImageView.setVisibility(View.GONE);
        }

        TextPayLoad textPayload = (TextPayLoad) payload;
        Spannable textMessage = textPayload.getTextMessage();
        messageTextView.setText(textMessage);
    }

    private void renderMessageForMe(Message message) {
        rootView.setGravity(Gravity.BOTTOM | Gravity.END);

        displayNameTextView.setVisibility(View.GONE);
        avatarImageView.setVisibility(View.GONE);
        messageContainer.setBackgroundResource(R.drawable.background_message_from_me);

        displayPayLoad(message.getPayload());
    }
}
