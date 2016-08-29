package es.npatarino.android.gotchallenge.chat.message.view.viewmodel;

import android.net.Uri;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Payload;

public class StickerPayLoad implements Payload {

    private String stickerFilePath;

    public StickerPayLoad(String stickerFilePath) {
        this.stickerFilePath = stickerFilePath;
    }

    public String getStickerFilePath() {
        return stickerFilePath;
    }

    public Uri getSticker() {
        return Uri.parse(stickerFilePath);
    }
}
