package com.adobe.aem.crea2minds.core.services;


import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Credentials;
import javax.jcr.Node;
import javax.jcr.Session;
import java.net.CacheRequest;
import java.util.HashMap;
import java.util.Map;

@Component(service = SystemUserResolver.class,immediate = true,name = "Resource Resolver getting with System User")
public class SystemUserResolver {

    @Reference
    private ResourceResolverFactory factory;


    public ResourceResolver getResourceResolver(){
        ResourceResolver resolver;
        Map<String,Object> map=new HashMap<>();
        map.put(factory.SUBSERVICE,"crea2minds");
        try {
            resolver= factory.getServiceResourceResolver(map);
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
        return resolver;
    }
}
