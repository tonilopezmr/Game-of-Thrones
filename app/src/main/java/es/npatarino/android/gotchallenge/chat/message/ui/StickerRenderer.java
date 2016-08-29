package es.npatarino.android.gotchallenge.chat.message.ui;

import android.net.Uri;
import android.support.v7.widget.AppCompatDrawableManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.pedrogomez.renderers.Renderer;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.base.ui.imageloader.ImageLoader;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.view.viewmodel.StickerPayLoad;

public class StickerRenderer extends Renderer<Message> {

    private final ImageLoader imageLoader;
    private ImageView stickerImageView;
    private ImageView avatarImageView;

    private LinearLayout rootView;

    public StickerRenderer(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    @Override
    protected void setUpView(View rootView) {
        this.rootView = (LinearLayout) rootView;
        stickerImageView = ((ImageView) rootView.findViewById(R.id.sticker_image));
        avatarImageView = (ImageView) rootView.findViewById(R.id.avatar_image);
    }

    @Override
    protected void hookListeners(View rootView) {

    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cell_sticker_message, parent, false);
    }

    @Override
    public void render() {
        Message message = getContent();

        Uri sticker = ((StickerPayLoad) message.getPayload()).getSticker();
        stickerImageView.setImageURI(sticker);

        if (message.isFromMe()) {
            avatarImageView.setVisibility(View.GONE);
            rootView.setGravity(Gravity.BOTTOM | Gravity.END);
        } else {
            avatarImageView.setVisibility(View.VISIBLE);
            rootView.setGravity(Gravity.BOTTOM | Gravity.START);

            imageLoader.builder()
                    .load(message.getUser().getImageUrl())
                    .placeHolder(AppCompatDrawableManager.get().getDrawable(avatarImageView.getContext(),
                            R.drawable.ned_head_light))
                    .into(avatarImageView)
                    .circle()
                    .show();
        }
    }
}
