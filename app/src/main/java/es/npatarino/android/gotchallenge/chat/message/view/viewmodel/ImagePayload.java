package es.npatarino.android.gotchallenge.chat.message.view.viewmodel;

import android.text.Spannable;

public class ImagePayload extends TextPayLoad {

    private String imageUrl;

    public ImagePayload(String imageUrl, Spannable message) {
        super(message);
        this.imageUrl = imageUrl;
    }

    public ImagePayload(String imageUrl, String message) {
        super(message);
        this.imageUrl = imageUrl;
    }

    public String getImageMessage(){
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImagePayload that = (ImagePayload) o;

        return imageUrl.equals(that.imageUrl);

    }

    @Override
    public int hashCode() {
        return imageUrl.hashCode();
    }
}
