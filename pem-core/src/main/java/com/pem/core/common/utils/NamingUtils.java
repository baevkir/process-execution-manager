package com.pem.core.common.utils;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * Utility class that helps to build names for objects using name conventions.
 */
public class NamingUtils {

    public static String getHumanReadableName(String name, CaseFormat caseFormat) {
        Assert.notNull(caseFormat, "Case format can't be NULL.");
        String nameToConvert = caseFormat.to(CaseFormat.LOWER_CAMEL, name);

        String[] splittedName = StringUtils.splitByCharacterTypeCamelCase(nameToConvert);

        String humanReadableName = StringUtils.join(splittedName, ' ');
        return StringUtils.capitalize(humanReadableName);
    }

    public static String getHumanReadableName(String name) {
        return getHumanReadableName(name, CaseFormat.LOWER_CAMEL);
    }
}
