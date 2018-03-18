package com.pem.persistence.mongo.provider;

import com.pem.core.common.applicationcontext.ApplicationContextBuilder;
import com.pem.persistence.api.manager.PersistenceManager;
import com.pem.persistence.api.provider.PemPersistenceServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

public class MongoPersistenceServiceProvider implements PemPersistenceServiceProvider, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoPersistenceServiceProvider.class);
    private static final String MONGO_DB_FACTORY_NAME = "pem.mongoDbFactory";
    private ApplicationContext parentContext;
    private ApplicationContext applicationContext;
    private MongoDbFactory mongoDbFactory;

    @Override
    public PersistenceManager getPersistenceManager() {
        return applicationContext.getBean(PersistenceManager.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.parentContext = applicationContext;
    }

    public void setMongoDbFactory(MongoDbFactory mongoDbFactory) {
        this.mongoDbFactory = mongoDbFactory;
    }

    @PostConstruct
    void initPersistenceApplicationContext() {
        Assert.notNull(parentContext, "Can't internalCreate PersistenceApplicationContext without parent context.");
        Assert.notNull(mongoDbFactory, "Can't internalCreate PersistenceApplicationContext without mongoDbFactory.");

        ApplicationContextBuilder contextBuilder = new ApplicationContextBuilder()
                .setParentContext(parentContext)
                .addXMLConfiguration("config/mongo-database-config.xml")
                .addSingletonBean(MONGO_DB_FACTORY_NAME, mongoDbFactory);

        applicationContext = contextBuilder.build();
    }

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
