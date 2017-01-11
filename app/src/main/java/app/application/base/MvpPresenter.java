package app.application.base;

/**
 * Interface representing a Presenter in a model view presenter (MVP) pattern.
 */
public interface MvpPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}

