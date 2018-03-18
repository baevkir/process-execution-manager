package com.pem.ui.presentation.common.view;

public abstract class AbstractBeanForm<B> {
//        extends CustomComponent implements BeanView<B> {
//
//    private Layout bottomToolbar;
//    private Layout topToolbar;
//    private final Panel contentPanel = new Panel();
//
//    private Button submitButton = new Button("Submit");
//    private Button cancelButton = new Button("Cancel");
//
//    protected abstract <V extends BeanView<B>> BaseBeanPresenter<B, V> getPresenter();
//
//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//    }
//
//    @Override
//    public Mono<Void> bind(B bean) {
//        return getPresenter().bindBean(bean)
//                .doOnNext(binder -> VaadinReactor.buttonClickPublisher(submitButton)
//                        .filter(event -> binder.isModified()).subscribe(clickEvent -> submit(binder)))
//
//                .doOnNext(binder -> VaadinReactor.buttonClickPublisher(cancelButton)
//                        .filter(event -> binder.isModified()).subscribe(clickEvent -> cancel(binder)))
//
//                .doOnSuccess(binder -> VaadinReactor.binderEditPublisher(binder)
//                        .subscribe(event -> bottomToolbar.setVisible(true)))
//
//                .doOnSuccess(binder -> VaadinReactor.binderEditPublisher(binder)
//                        .subscribe(event -> topToolbar.setVisible(false)))
//
//                .doOnSuccess(binder -> initFormElements())
//                .then();
//    }
//
//    protected abstract void initFormElements();
//
//    protected abstract Layout createFormComponent();
//
//    protected abstract Layout createTopToolbar();
//
//    protected Layout createBottomToolbar() {
//        HorizontalLayout bottomToolbar = new HorizontalLayout();
//        bottomToolbar.setVisible(false);
//
//        bottomToolbar.addComponent(submitButton);
//        bottomToolbar.addComponent(cancelButton);
//
//        return bottomToolbar;
//    }
//
//    @PostConstruct
//    void init() {
//        getPresenter().bind(this);
//        VerticalLayout mainLayout = new VerticalLayout();
//
//        topToolbar = createTopToolbar();
//        mainLayout.addComponent(topToolbar);
//        mainLayout.addComponent(contentPanel);
//
//        bottomToolbar = createBottomToolbar();
//        mainLayout.addComponent(bottomToolbar);
//        setCompositionRoot(mainLayout);
//        contentPanel.setContent(createFormComponent());
//    }
//
//    private void submit(BeanFieldGroup<B> binder) {
//        try {
//            binder.commit();
//            bottomToolbar.setVisible(false);
//            topToolbar.setVisible(true);
//        } catch (FieldGroup.CommitException exception) {
//            Notification.show(exception.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
//            throw new RuntimeException(exception);
//        }
//    }
//
//    private void cancel(BeanFieldGroup<B> binder) {
//        binder.discard();
//        bottomToolbar.setVisible(false);
//        topToolbar.setVisible(true);
//    }

}
