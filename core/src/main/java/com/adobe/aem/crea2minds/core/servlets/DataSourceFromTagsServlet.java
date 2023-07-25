package com.adobe.aem.crea2minds.core.servlets;


import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.JsonObjectBuilder;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

@Component(service= Servlet.class)
@SlingServletPaths("/bin/datasource/tags")
public class DataSourceFromTagsServlet extends SlingAllMethodsServlet {

    static  final Logger log = LoggerFactory.getLogger(DataSourceFromTagsServlet.class);
    @Override
    protected void doGet(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {

        ResourceResolver resolver = request.getResourceResolver();
        Resource resource = request.getResource();

        Gson gson = new Gson();

       GsonBuilder json = gson.newBuilder();


        DataSource dataSource = null;
        ValueMap datasourceVm = resource.getChild("datasource").getValueMap();
        String source = datasourceVm.get("source").toString();
        if (source != null && source.equalsIgnoreCase("tags")) {
            if( datasourceVm.get("path") != null && datasourceVm.get("path").toString().length() !=0) {
                String tagpath = datasourceVm.get("path").toString();
                dataSource = getDatasourceByTagsPath(resolver, tagpath);
            }
        }

        if (source != null && source.equalsIgnoreCase("pages")) {
            if( datasourceVm.get("path") != null  && datasourceVm.get("path").toString().length() !=0) {
                String pagePath = datasourceVm.get("path").toString();
                dataSource = getDatasourceByPagePath(resolver, pagePath);
            }
        }

            request.setAttribute(DataSource.class.getName(), dataSource);


    }

    private DataSource getDatasourceByTagsPath(ResourceResolver resolver,String tagpath){

        Resource tagResource = resolver.getResource(tagpath);
        List<Resource> resourceList =new ArrayList<>();
        ValueMap valueMap;

        for (Resource childtagresource : tagResource.getChildren()){
            Tag childtag = childtagresource.adaptTo(Tag.class);
            String value = childtag.getName();
            String text = childtag.getTitle();
            valueMap =new ValueMapDecorator(new HashMap<>());
            valueMap.put("value",value);
            valueMap.put("text",text);
            resourceList.add(new ValueMapResource(resolver,new ResourceMetadata(),"nt:unstructured",valueMap));
        }

        DataSource ds =new SimpleDataSource(resourceList.iterator());
        return ds;
        }


        private DataSource  getDatasourceByPagePath(ResourceResolver resolver,String pagePath){

        Resource pageResource = resolver.getResource(pagePath);
        Page mainPage = pageResource.adaptTo(Page.class);
        Iterator<Page> childpages = mainPage.listChildren();
        List<Resource> resourceList =new ArrayList<>();
        while(childpages.hasNext()){
          Page childpage = childpages.next();
          String value =childpage.getName();
          String text = childpage.getTitle();
          ValueMap vm =new ValueMapDecorator(new HashMap<>());
          vm.put("value",value);
          vm.put("text",text);
          resourceList.add(new ValueMapResource(resolver,new ResourceMetadata(), "nt:unstrutured",vm));
        }

        DataSource dataSource =new SimpleDataSource(resourceList.iterator());
        return dataSource;
        }








}
