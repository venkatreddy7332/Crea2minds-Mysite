package com.adobe.aem.crea2minds.core.models.impl;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,adapters = {ComponentExporter.class, NavigationModel.class},resourceType = NavigationModel.Resource_Type)
@Exporter(name= ExporterConstants.SLING_MODEL_EXPORTER_NAME,extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class NavigationModel implements ComponentExporter {

    static final String Resource_Type="crea2minds/components/navigation";

    private List<String> result;

    @PostConstruct
    public void init(){

    }

    @Override
    public String getExportedType() {
        return NavigationModel.Resource_Type;
    }
}
