package es.npatarino.android.gotchallenge.chat.message.usecases;

import es.npatarino.android.gotchallenge.base.detail.usecases.UseCase;
import es.npatarino.android.gotchallenge.chat.conversation.model.Conversation;
import es.npatarino.android.gotchallenge.chat.message.MessageDomain;
import es.npatarino.android.gotchallenge.chat.message.model.Message;
import rx.Observable;
import rx.Scheduler;

public class SubscribeToMessage extends UseCase<Message> {

  private MessageDomain.Repository repository;
  private Conversation conversation;

  public SubscribeToMessage(MessageDomain.Repository repository,
                            Scheduler uiThread,
                            Scheduler executorThread) {
    super(uiThread, executorThread);
    this.repository = repository;
  }

  public Observable<Message> execute(Conversation conversation) {
    this.conversation = conversation;
    return execute();
  }

  @Override
  protected Observable<Message> buildUseCaseObservable() {
    return scheduleOn(repository.subscribeToMessages(conversation));
  }
}
