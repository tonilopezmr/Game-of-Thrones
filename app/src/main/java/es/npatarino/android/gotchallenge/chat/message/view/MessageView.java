package es.npatarino.android.gotchallenge.chat.message.view;

import es.npatarino.android.gotchallenge.base.Mvp;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;

public interface MessageView extends Mvp.View {

    void showMessage(Message message);
}
