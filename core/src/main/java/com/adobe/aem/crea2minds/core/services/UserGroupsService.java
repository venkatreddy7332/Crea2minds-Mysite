package com.adobe.aem.crea2minds.core.services;

import com.adobe.granite.security.user.UserManagementService;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import java.util.*;

@Component(service =UserGroupsService.class,immediate = true,name = "my-usergroup-service")
public class UserGroupsService {

     static final Logger log= LoggerFactory.getLogger(UserGroupsService.class);

     @Reference
     private ResourceResolverFactory factory;

     @Reference
     private SystemUserResolver systemresolver;

    public Boolean isAccessable(String groupname,ResourceResolver resolver) throws LoginException {
        String useridd=resolver.adaptTo(Session.class).getUserID();
        boolean isAccessable=false;
        List <String> groups=new ArrayList<>();
       String id=resolver.adaptTo(Session.class).getUserID();
       log.info("User id is--{}"+id);
        UserManager usm=resolver.adaptTo(UserManager.class);
        User usser;
        try {
            usser=(User) usm.getAuthorizable(id);
            Iterator<Group> groupIterator= usser.memberOf();
            if(groupIterator.hasNext()) {
                while (groupIterator.hasNext()) {
                    String actualgroupname = groupIterator.next().getID();
                    groups.add(actualgroupname);
                }
            }
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
        log.info("Groups with ID-"+id+"are-----{}"+groups.toString());
        if(groups.contains(groupname)){
            isAccessable=true;
        }
        log.info("isauthor ----{}"+isAccessable);
        return isAccessable;
    }
    public String createuser(String username,String password) throws RepositoryException, LoginException {
      String status="";
     Session session= systemresolver.getResourceResolver().adaptTo(Session.class);
        UserManagementService ums = systemresolver.getResourceResolver().adaptTo(UserManagementService.class);
        UserManager userManager = ums.getUserManager(session);
       User user= userManager.createUser(username,password);
       log.info(user+"==has created");
        return status;
    }

    public String changepassword(String userid,String newpassword) throws RepositoryException {
        String status="";
        ResourceResolver resolver= null;
        resolver = systemresolver.getResourceResolver();
        UserManager userManager = resolver.adaptTo(UserManager.class);
      User user =  (User) userManager.getAuthorizable(userid);
      String aa =user.getCredentials().toString();
      log.info(aa);
      user.changePassword(newpassword);
        return status;
    }
}
