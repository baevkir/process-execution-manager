package com.pem.ui.presentation.common.presenter;

import com.pem.ui.common.binder.PemBeanFieldGroup;
import com.pem.ui.presentation.common.view.BeanView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public abstract class BaseBeanPresenter<B, V extends BeanView<B>> extends BasePresenter<V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasePresenter.class);

    private PemBeanFieldGroup<B> binder;

    public Mono<PemBeanFieldGroup<B>> bindBean(B sourceBean) {
        return Mono.just(sourceBean)
                .doOnNext(bean -> LOGGER.debug("Start to bind bean {}.", bean))
                .map(bean -> initBinder(bean));
    }

    protected B getBean() {
        return binder.getItemDataSource().getBean();
    }

    private PemBeanFieldGroup<B> initBinder(B bean) {
        binder = new PemBeanFieldGroup<>((Class<B>) bean.getClass());
        binder.setItemDataSource(bean);
        binder.setBuffered(true);
        binder.bindMemberFields(getView());
        return binder;
    }
}
