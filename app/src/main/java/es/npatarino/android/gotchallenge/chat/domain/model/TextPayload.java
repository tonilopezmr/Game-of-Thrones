package es.npatarino.android.gotchallenge.chat.domain.model;

public class TextPayload implements Payload {

    private String message;

    public TextPayload(String message) {
        this.message = message;
    }

    public String getMessageText(){
        return message;
    }

}
