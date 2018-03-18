package com.pem.ui.presentation.common.presenter;

public abstract class BaseBeanPresenter {

//}<B, V extends BeanView<B>> extends BasePresenter<V> {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(BasePresenter.class);
//
//    private PemBeanFieldGroup<B> binder;
//
//    public Mono<PemBeanFieldGroup<B>> bindBean(B sourceBean) {
//        return Mono.just(sourceBean)
//                .doOnNext(bean -> LOGGER.debug("Start to bind bean {}.", bean))
//                .map(bean -> initBinder(bean));
//    }
//
//    protected B getBean() {
//        return binder.getItemDataSource().getBean();
//    }
//
//    private PemBeanFieldGroup<B> initBinder(B bean) {
//        binder = new PemBeanFieldGroup<>((Class<B>) bean.getClass());
//        binder.setItemDataSource(bean);
//        binder.setBuffered(true);
//        binder.bindMemberFields(getView());
//        return binder;
//    }
}
