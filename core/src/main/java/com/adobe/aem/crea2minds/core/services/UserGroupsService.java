package com.adobe.aem.crea2minds.core.services;

import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Component(service =UserGroupsService.class,immediate = true,name = "my-usergroup-service")
public class UserGroupsService {

     static final Logger log= LoggerFactory.getLogger(UserGroupsService.class);

    public Boolean isAccessable(String groupname, ResourceResolver resolver){
        boolean isAccessable=false;
        List <String> groups=new ArrayList<>();
       String id= resolver.adaptTo(Session.class).getUserID();
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
    public String createuser(String userid,String password,ResourceResolver resolver) throws RepositoryException {
      String status="";

        UserManager usermanager=resolver.adaptTo(UserManager.class);
      User user=usermanager.createUser(userid,password);
      if(user !=null){
          status="user added Successfully";
      }else{
          status="User not added";
      }
        return status;
    }

    public String changepassword(String userid,String newpassword,ResourceResolver resolver) throws RepositoryException {
        String status="";
       UserManager userManager = resolver.adaptTo(UserManager.class);
      User user =  (User) userManager.getAuthorizable(userid);
      user.changePassword(newpassword);
        return status;
    }
}
