package com.pem.ui.presentation.process;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = ProcessMainView.VIEW_NAME)
public class ProcessMainViewImpl extends HorizontalLayout implements ProcessMainView {

    @Autowired
    private ProcessMainPresenter presenter;

    @Autowired
    private ProcessesList processesList;

    private Subject<ViewChangeListener.ViewChangeEvent> mainViewSubject;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        mainViewSubject.onNext(event);
    }

    @Override
    public Observable<ViewChangeListener.ViewChangeEvent> getMainViewObservable() {
        return mainViewSubject;
    }

    @Override
    public ProcessesList getProcessesList() {
        return processesList;
    }

    @PostConstruct
    void init() {
        mainViewSubject = PublishSubject.<ViewChangeListener.ViewChangeEvent>create().toSerialized();
        presenter.bind(this);
        setSizeFull();

        addComponent(processesList);
    }
}
