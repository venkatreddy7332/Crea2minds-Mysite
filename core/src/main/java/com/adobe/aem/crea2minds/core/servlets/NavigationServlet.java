package com.adobe.aem.crea2minds.core.servlets;

import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;

@Component(service = Servlet.class)
@SlingServletPaths({"/bin/navigation.json"})
public class NavigationServlet extends SlingSafeMethodsServlet {

    static final Logger log = LoggerFactory.getLogger(NavigationServlet.class);
    @Override
    protected void doGet(  SlingHttpServletRequest req,SlingHttpServletResponse resp) throws ServletException, IOException {
        final Resource mainresource = req.getResource();
      ResourceResolver resolver=  req.getResourceResolver();
       resp.setContentType("application/json");
       String path=req.getParameter("path");
       if(path ==null){
           log.info("please add the path in the dialog to get data ----");
       }
       Resource resource=req.getResourceResolver().getResource(path);
      Page page= resource.adaptTo(Page.class);
     Iterator<Page> pageiteraor= page.listChildren();
     log.info(pageiteraor.toString());
     JsonObject obj = new JsonObject();
     JsonArray array1 =new JsonArray();
     array1.add(childjson(page,resolver));
     if (pageiteraor.hasNext()) {
         while (pageiteraor.hasNext()) {
             Page childpage =pageiteraor.next();
             JsonObject jsonobj=childjson(childpage,resolver);
             Iterator<Page> nestedpageiterator = childpage.listChildren();
             log.info(nestedpageiterator.toString());
             if(nestedpageiterator.hasNext()){
                 JsonObject nesobj =new JsonObject();
                 JsonArray nesarray=new JsonArray();
                 while(nestedpageiterator.hasNext()){
                   Page nestedpage = nestedpageiterator.next();
                   nesobj=childjson(nestedpage,resolver);
                     nesarray.add(nesobj);
                 }

                 jsonobj.add("nested",nesarray);
                 log.info(nesarray.toString());
             }else{
                 log.info("nested pages not avalable for this page");
             }
            array1.add(jsonobj);
             log.info(jsonobj.toString());
         }
     }else{
         resp.getWriter().println("child pages are not available");
         log.info("child pages are not available");
     }
        obj.add("result",array1);
     resp.getWriter().println(obj);
     log.info(array1.toString());
        }

    private JsonObject childjson(Page childpage ,ResourceResolver resolver) {
        JsonObject obj=new JsonObject();
       if(childpage.getName() != null){ obj.addProperty("name",childpage.getName());}
       if(childpage.getTitle() != null){ obj.addProperty("title",childpage.getTitle());}
       if(childpage.getPath() != null){ obj.addProperty("path",childpage.getPath());}
       if(childpage.getDescription() != null){ obj.addProperty("description",childpage.getDescription());}
      Resource imgresource= resolver.getResource(childpage.getPath()+"/jcr:content/image");
       if (imgresource !=null) {
           String image = imgresource.getValueMap().get("fileReference", String.class);
           if(image!= null){
               obj.addProperty("image",image);
           }
       }
        log.info(obj.toString());
        return obj;
    }
}
