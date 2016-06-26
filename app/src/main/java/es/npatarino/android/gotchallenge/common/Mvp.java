package es.npatarino.android.gotchallenge.common;

public interface Mvp {

    interface View {
        void initUi();
        void error();
    }

    interface Presenter<T extends View> {
        void init();
        void setView(T view);
        void onDestroy();
    }

}
