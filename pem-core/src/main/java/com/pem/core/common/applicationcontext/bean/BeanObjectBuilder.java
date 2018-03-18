package com.pem.core.common.applicationcontext.bean;

import com.pem.core.common.utils.NamingUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Builder that helps to create {@link BeanObject}
 */
public class BeanObjectBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanObjectBuilder.class);

    private String name;
    private String beanName;

    private BeanObjectBuilder(){
    }
    public static BeanObjectBuilder newInstance() {
        return new BeanObjectBuilder();
    }

    public BeanObject build() {
        Assert.notNull(beanName, "Bean name can`t be NULL.");
        BeanObject beanObject = new BeanObject();
        beanObject.setBeanName(beanName);
        if (StringUtils.isEmpty(name)) {
            name = NamingUtils.getHumanReadableName(beanName);
        }

        beanObject.setName(name);

        return beanObject;
    }

    public BeanObjectBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public BeanObjectBuilder setBeanName(String beanName) {
        this.beanName = beanName;
        return this;
    }
}
