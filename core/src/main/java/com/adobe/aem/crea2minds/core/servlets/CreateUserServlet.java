package com.adobe.aem.crea2minds.core.servlets;

import com.adobe.aem.crea2minds.core.services.SystemUserResolver;
import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Value;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

import org.osgi.service.component.ComponentContext;
import org.apache.sling.jcr.api.SlingRepository;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.User;
@Component(service = Servlet.class)
@SlingServletPaths({"/bin/changepassword.json"})
public class CreateUserServlet extends SlingSafeMethodsServlet {


   @Reference
    private SystemUserResolver sysresoler;

    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        if (password1==password2) {
            ResourceResolver resolver = null;
            try {
                resolver = sysresoler.getResourceResolver();
                UserManager userManager = resolver.adaptTo(UserManager.class);
                User user = (User) userManager.getAuthorizable(username);
                if (user != null) {
                    user.changePassword(password1);
                    resolver.commit();
                    resolver.adaptTo(Session.class).save();
                    response.getWriter().println("user password was changed");
                }

            } catch (LoginException e) {
                response.getWriter().println(username + "--" + "error changing password");
                throw new RuntimeException(e);

            } catch (RepositoryException e) {
                response.getWriter().println(username + "--" + "error changing password");
                throw new RuntimeException(e);
            } finally {
                resolver.adaptTo(Session.class).logout();
            }

        }else {
            response.getWriter().println("make sure two passwords are same");
        }

    }
}
