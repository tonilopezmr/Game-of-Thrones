package es.npatarino.android.gotchallenge.base.interactor;

import rx.Observable;
import rx.Scheduler;

public abstract class UseCase<T> {

    protected final Scheduler uiThread;
    protected final Scheduler executorThread;

    protected UseCase(Scheduler uiThread, Scheduler executorThread) {
        this.uiThread = uiThread;
        this.executorThread = executorThread;
    }

    public Observable<T> execute(){
        return buildUseCaseObservable();
    }

    public Observable<T> ScheduleOn(Observable<T> observable){
        return observable
                .observeOn(uiThread)
                .subscribeOn(executorThread);
    }

    protected abstract Observable<T> buildUseCaseObservable();
}
