package com.adobe.aem.crea2minds.core.models;

import com.adobe.aem.crea2minds.core.services.UserGroupsService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.osgi.service.component.annotations.Reference;
import sun.util.calendar.BaseCalendar;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Session;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Model(adaptables = SlingHttpServletRequest.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class WelcomeComponentModel {

    @Inject
    private SlingHttpServletRequest req;

    @OSGiService
    private UserGroupsService userGroupsService;

    @ValueMapValue
    private String welcometext;

    @ValueMapValue
    private String groupname;

    private String date;

    private String username;
    private boolean isAccessable;

    @PostConstruct
    public void init(){
        if(req !=null) {
            ResourceResolver resolver =req.getResourceResolver();
            isAccessable = userGroupsService.isAccessable(groupname, resolver);
            if(resolver != null) {
               username= resolver.adaptTo(Session.class).getUserID();
            }
        }

        date=new Date().toString();
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
}
