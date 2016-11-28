package com.pem.common.bean.provider.context;

import com.pem.context.OperationContext;

public interface OperationContextProvider {
    OperationContext createContext();
}
