package com.adobe.aem.crea2minds.core.models;

import com.adobe.aem.crea2minds.core.services.PageAccessibleGroupServiceImpl;
import com.adobe.aem.crea2minds.core.services.UserGroupsService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Session;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Model(adaptables = SlingHttpServletRequest.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class WelcomeComponentModel {

    @Inject
    private SlingHttpServletRequest req;

    @OSGiService
    private UserGroupsService userGroupsService;

    @OSGiService
    private PageAccessibleGroupServiceImpl pageAccessibleGroupService;

    @ValueMapValue
    private String welcometext;

    @ValueMapValue
    private String redirectpath;

    private String date;

    private String groupname;

    private String username;
    private boolean isAccessable;

    @PostConstruct
    public void init() throws LoginException {
        if(pageAccessibleGroupService !=null){
            groupname=pageAccessibleGroupService.getGroupname();
        }

        if(req !=null) {
            ResourceResolver resolver =req.getResourceResolver();
            isAccessable = userGroupsService.isAccessable(groupname,req.getResourceResolver());
            if(resolver != null) {
               username= resolver.adaptTo(Session.class).getUserID();
            }
        }
        redirectpath=redirectpath+".html";

        date = new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime());



    }

    public String getWelcometext() {
        return welcometext;
    }

    public String getGroupname() {
        return groupname;
    }

    public String getDate() {

        return date;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAccessable() {
        return isAccessable;
    }

    public String getRedirectpath() {
        return redirectpath;
    }
}
