package es.npatarino.android.gotchallenge.chat.message.viewmodel;

import android.text.Spannable;
import android.text.SpannableString;
import es.npatarino.android.gotchallenge.chat.message.model.Payload;

public class TextPayLoad implements Payload {

  private Spannable message;

  public TextPayLoad(Spannable message) {
    this.message = message;
  }

  public TextPayLoad(String message) {
    this.message = new SpannableString(message);
  }

  public Spannable getTextMessage() {
    return message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TextPayLoad that = (TextPayLoad) o;

    return message.equals(that.message);

  }

  @Override
  public int hashCode() {
    return message.hashCode();
  }
}
