package es.npatarino.android.gotchallenge.chat.view.viewmodel;

import android.text.Spannable;
import android.text.SpannableString;
import es.npatarino.android.gotchallenge.chat.domain.model.Payload;

public class TextPayload implements Payload {

    private Spannable message;

    public TextPayload(Spannable message) {
        this.message = message;
    }

    public TextPayload(String message){
        this.message = new SpannableString(message);
    }

    public Spannable getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextPayload that = (TextPayload) o;

        return message.equals(that.message);

    }

    @Override
    public int hashCode() {
        return message.hashCode();
    }
}
