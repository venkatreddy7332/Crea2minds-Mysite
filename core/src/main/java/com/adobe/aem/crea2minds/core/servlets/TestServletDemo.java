package com.adobe.aem.crea2minds.core.servlets;

import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;

@Component(service = Servlet.class)
@SlingServletPaths({"/bin/demo.json"})
public class TestServletDemo extends SlingSafeMethodsServlet {
    @Override
    protected void doGet( final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        final Resource mainresource = req.getResource();
       resp.setContentType("application/json");
       Resource resource=req.getResourceResolver().getResource("/content/crea2minds/us/en/homepage");
      Page page= resource.adaptTo(Page.class);
     Iterator<Page> pageiteraor= page.listChildren();
     JsonObject obj = new JsonObject();
     JsonArray array1 =new JsonArray();
     if (pageiteraor.hasNext()) {
         while (pageiteraor.hasNext()) {
             Page childpage =pageiteraor.next();
             JsonObject jsonobj=childjson(childpage);
             Iterator<Page> nestedpageiterator = childpage.listChildren();

             if(nestedpageiterator.hasNext()){
                 JsonObject nesobj =new JsonObject();
                 JsonArray nesarray=new JsonArray();
                 while(nestedpageiterator.hasNext()){
                   Page nestedpage = nestedpageiterator.next();
                   nesobj=childjson(nestedpage);
                     nesarray.add(nesobj);
                 }

                 jsonobj.add("nested",nesarray);
             }
            array1.add(jsonobj);
         }
     }
        obj.add("result",array1);
     resp.getWriter().println(obj);
        }

    private JsonObject childjson(Page childpage) {
        JsonObject obj=new JsonObject();
        obj.addProperty("name",childpage.getName());
        obj.addProperty("title",childpage.getTitle());
        obj.addProperty("path",childpage.getPath());
        return obj;
    }
}
