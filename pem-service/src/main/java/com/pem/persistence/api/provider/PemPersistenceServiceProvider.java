package com.pem.persistence.api.provider;

import com.pem.persistence.api.manager.PersistenceManager;

public interface PemPersistenceServiceProvider {
    PersistenceManager getPersistenceManager();
}
