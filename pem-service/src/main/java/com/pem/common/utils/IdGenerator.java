package com.pem.common.utils;

import org.bson.codecs.ObjectIdGenerator;
import org.bson.types.ObjectId;

import java.math.BigInteger;

public class IdGenerator {
    private static final ObjectIdGenerator OBJECT_ID_GENERATOR = new ObjectIdGenerator();

    public static BigInteger generateId(){
        ObjectId objectId = (ObjectId) OBJECT_ID_GENERATOR.generate();
        return new BigInteger(objectId.toString(), 16);
    }
}
