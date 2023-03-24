package com.adobe.aem.crea2minds.core.models;

import com.adobe.aem.crea2minds.core.services.ChildPagesService;
import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;


@Model(adaptables = SlingHttpServletRequest.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,adapters = {ComponentExporter.class, NavigationModelImpl.class},resourceType = NavigationModelImpl.Resource_Type)
@Exporter(name= ExporterConstants.SLING_MODEL_EXPORTER_NAME,extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class NavigationModelImpl implements ComponentExporter {

    static final String Resource_Type="crea2minds/components/navigation";

    @OSGiService
    private ChildPagesService childPagesService;

    @ValueMapValue
    private String navigationRoot;

    private List<Map<String,String>> result;



    @PostConstruct
    public void init() throws LoginException {
    result=childPagesService.getChildJson(navigationRoot);


    }
    public String getNavigationRoot() {
        return navigationRoot;
    }

    public List<Map<String, String>> getResult() {
        return result;
    }

    @Override
    public String getExportedType() {
        return NavigationModelImpl.Resource_Type;
    }
}
