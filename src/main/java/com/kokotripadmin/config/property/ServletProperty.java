package com.kokotripadmin.config.property;


import org.springframework.stereotype.Component;

@Component
public class ServletProperty {
    private String viewResolverPrefix;
    private String viewResolverSuffix;


    public String getViewResolverPrefix() {
        return viewResolverPrefix;
    }

    public void setViewResolverPrefix(String viewResolverPrefix) {
        this.viewResolverPrefix = viewResolverPrefix;
    }

    public String getViewResolverSuffix() {
        return viewResolverSuffix;
    }

    public void setViewResolverSuffix(String viewResolverSuffix) {
        this.viewResolverSuffix = viewResolverSuffix;
    }


}
