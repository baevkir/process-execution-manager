package com.pem.ui.presentation.common.navigator;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class NavigationParams {
    private static final Logger LOGGER = LoggerFactory.getLogger(NavigationParams.class);
    private static final Pattern PARAMS_REGEXP = Pattern.compile("(\\w*)(=(\\w*))?");

    private String viewName;
    private Map<String, String> urlParams;

    private NavigationParams() {
        LOGGER.trace("Create new instance of NavigationParams for view {} and {}.", viewName, urlParams);
    }

    public static Builder builder() {
        return new NavigationParams.Builder();
    }

    public String getViewName() {
        return viewName;
    }

    public Optional<String> getUrlParam(String key) {
        return Optional.ofNullable(urlParams.get(key));
    }

    public boolean hasUrlParam(String key) {
        return urlParams.containsKey(key);
    }

    public Map<String, String> getUrlParams() {
        return urlParams;
    }

    public String getPath() {
        return Optional.ofNullable(viewName)
                .map(view -> view + "/" + createParamString(urlParams))
                .orElse("");
    }

    private String createParamString(Map<String, String> params) {
        StringBuilder builder = new StringBuilder();

        params.entrySet().forEach(entry -> {
            LOGGER.debug("Append params: {}", entry);

            if (builder.length() != 0) {
                builder.append("&");
            }
            builder.append(entry.getKey());
            Optional.ofNullable(entry.getValue())
                    .ifPresent(value -> builder.append("=").append(value));
        });

        return builder.toString();
    }

    public static class Builder {
        private String viewName;
        private Map<String, String> urlParams = new HashMap<>();

        public Builder setViewName(String viewName) {
            this.viewName = Preconditions.checkNotNull(viewName);
            return this;
        }

        public Builder setUrlParams(Map<String, String> urlParams) {
            this.urlParams = Preconditions.checkNotNull(urlParams);
            return this;
        }

        public Builder addUrlParam(String param, String value) {
            urlParams.put(Preconditions.checkNotNull(param), value);
            return this;
        }

        public Builder addUrlParam(String param) {
            urlParams.put(Preconditions.checkNotNull(param), null);
            return this;
        }

        public Builder removeUrlParam(String param) {
            urlParams.remove(Preconditions.checkNotNull(param));
            return this;
        }

        public Builder setFromNavigationParams(NavigationParams params) {
            viewName = params.getViewName();
            urlParams.putAll(params.getUrlParams());
            return this;
        }

        public Builder setFromNavigationState(String navigationState) {
            if (StringUtils.isEmpty(navigationState)) {
                LOGGER.debug("Navigation state is Empty. Nothing to parse.");
                return this;
            }
            String view = navigationState;
            if (navigationState.contains("/")) {
                int slashIndex = navigationState.indexOf("/");
                view = navigationState.substring(0, slashIndex);
                Assert.hasText(view, "Cannot find view in URL.");
            }
            viewName = view;

            if (navigationState.length() > view.length() + 1) {
                String paramsString = navigationState.substring(viewName.length() + 1);
                setFromNavigationParams(paramsString);
            }
            return this;
        }

        public Builder setFromNavigationParams(String navigationParams) {
            if (StringUtils.isEmpty(navigationParams)) {
                LOGGER.debug("Navigation params is Empty. Nothing to parse.");
                return this;
            }
            HashMap<String, String> params = new HashMap<>();
            Arrays.stream(navigationParams.split("&"))
                    .filter(paramPair -> StringUtils.isNotEmpty(paramPair))
                    .map(paramPair -> PARAMS_REGEXP.matcher(paramPair))
                    .peek(matcher -> matcher.find())
                    .peek(matcher -> LOGGER.debug("Find params matches param: {} value: {}.", matcher.group(1), matcher.group(3)))
                    .forEach(matcher -> params.put(matcher.group(1), matcher.group(3)));

            urlParams = params;
            return this;
        }

        public NavigationParams build() {
            NavigationParams params = new NavigationParams();
            params.viewName = viewName;
            params.urlParams = urlParams;

            return params;
        }
    }
}
