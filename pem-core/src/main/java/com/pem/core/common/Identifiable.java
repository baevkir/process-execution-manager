package com.pem.core.common;

import java.math.BigInteger;

/**
 * Interface marker. Marks that implementations of this interface have Id support.
 */
public interface Identifiable {
    BigInteger getId();
    void setId(BigInteger id);
}
