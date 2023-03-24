package com.adobe.aem.crea2minds.core.services;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.*;

@Component(service = ChildPagesService.class,name = "childjsonservice",immediate = true)
public class ChildPagesService {

    @Reference
    private SystemUserResolver systemUserResolver;

    public List<Map<String, String>> getChildJson(String root) throws LoginException {
        List<Map<String, String>> result = new ArrayList<>();
        ResourceResolver resolver = systemUserResolver.getResourceResolver();
        Page mainpage = resolver.getResource(root).adaptTo(Page.class);
        Iterator<Page> pageitr = mainpage.listChildren();
        if (pageitr != null) {
            while (pageitr.hasNext()) {
                Map<String, String> child = new HashMap<>();
                Page childpage = pageitr.next();
                if (childpage.getName() != null) {
                    child.put("name", childpage.getName());
                }
                if (childpage.getTitle() != null) {
                    child.put("title", childpage.getTitle());
                }
                if (childpage.getPath() != null) {
                    child.put("path", childpage.getPath());
                }
                if (childpage.getDescription() != null) {
                    child.put("description", childpage.getDescription());
                }
                Resource imgresource = resolver.getResource(childpage.getPath() + "/jcr:content/image");
                if (imgresource != null) {
                    String image = imgresource.getValueMap().get("fileReference", String.class);
                    if (image != null) {
                        child.put("image", image);
                    }
                }
                result.add(child);

            }
        } else {
            Map<String, String> child = new HashMap<>();
            child.put("error", "pages are not available");
            result.add(child);
        }
        return result;
    }


}
