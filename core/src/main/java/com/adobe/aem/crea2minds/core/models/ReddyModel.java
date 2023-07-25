package com.adobe.aem.crea2minds.core.models;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ReddyModel {

    @ValueMapValue
    private String text;

    @ValueMapValue
    private String text1;

    @RequestAttribute(name = "attr")
    private String attribute;

    @PostConstruct
    public void init(){
        attribute = attribute+"--hello";
    }

    public String getText() {
        return text;
    }

    public String getText1() {
        return text1;
    }

    public String getAttribute() {
        return attribute;
    }
}
