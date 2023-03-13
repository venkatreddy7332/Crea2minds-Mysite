package com.adobe.aem.crea2minds.core.services;


import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.Map;

@Component(service = SystemUserResolver.class,immediate = true,name = "Resource Resolver getting with System User")
public class SystemUserResolver {

    @Reference
    private ResourceResolverFactory factory;

    private  ResourceResolver resolver;
    public ResourceResolver getResourceResolver() throws LoginException {
        Map<String,Object> map=new HashMap<>();
        map.put(factory.SUBSERVICE,"crea2minds");
        resolver= factory.getServiceResourceResolver(map);
        return resolver;
    }
}
