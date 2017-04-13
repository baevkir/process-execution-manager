package com.pem.ui.presentation.common.navigator;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NavigationParams {
    private static final Logger LOGGER = LoggerFactory.getLogger(NavigationParams.class);
    public static final String ID_PARAM = "id";
    public static final String REFRESH_LIST_PARAM = "refreshList";

    private String viewName;
    private Map<String, String> urlParams;

    private NavigationParams() {
        LOGGER.trace("Create new instance of NavigationParams for view {} and {}.", viewName, urlParams);
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

    public static Builder builder() {
        return new NavigationParams.Builder();
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

        public NavigationParams build() {
            NavigationParams params = new NavigationParams();
            params.viewName = Preconditions.checkNotNull(viewName, "View Name must be filed.");
            params.urlParams = urlParams;

            return params;
        }
    }
}
