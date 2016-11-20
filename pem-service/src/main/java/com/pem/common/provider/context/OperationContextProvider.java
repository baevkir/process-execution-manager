package com.pem.common.provider.context;

import com.pem.context.OperationContext;

public interface OperationContextProvider {
    OperationContext createContext();
}
