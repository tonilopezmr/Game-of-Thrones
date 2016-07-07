package es.npatarino.android.gotchallenge.chat.domain.model;

public class TextPayload implements Payload {

    private String message;

    public TextPayload(String message) {
        this.message = message;
    }

    public String getMessageText(){
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
