package com.adobe.aem.crea2minds.core.servlets;

import com.adobe.aem.crea2minds.core.services.SystemUserResolver;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.*;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletPaths({"/bin/login122.json"})
public class Login extends SlingAllMethodsServlet {


    @Reference
    private SystemUserResolver systemUserResolver;

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Session session = null;


        Repository repository = null;
        try {
            repository = systemUserResolver.getResourceResolver().adaptTo(Repository.class);
            Credentials credentials = new SimpleCredentials(username, password.toCharArray());
            session = repository.login(credentials);
            if (session != null) {
                session.logout();

            }
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }


    }
}