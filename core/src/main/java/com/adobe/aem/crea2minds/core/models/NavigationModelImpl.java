package com.adobe.aem.crea2minds.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NavigationModelImpl {

    @ValueMapValue
    private String navigationRoot;


    public String getNavigationRoot() {
        return navigationRoot;
    }

}
