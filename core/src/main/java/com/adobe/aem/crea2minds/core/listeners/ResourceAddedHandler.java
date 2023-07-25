package com.adobe.aem.crea2minds.core.listeners;

import com.adobe.aem.crea2minds.core.services.SystemUserResolver;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;
import com.day.cq.wcm.api.PageEvent;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = EventHandler.class,
        property = {
                EventConstants.EVENT_TOPIC+ "="+PageEvent.EVENT_TOPIC
        },
        immediate = true

)
public class ResourceAddedHandler implements EventHandler {



    static final private Logger log = LoggerFactory.getLogger(ResourceAddedHandler.class);
    @Reference
    SystemUserResolver systemUserResolver;


    @Override
    public void handleEvent(Event event) {
        ResourceResolver resolver = systemUserResolver.getResourceResolver();
        WorkflowSession workflowSession = resolver.adaptTo(WorkflowSession.class);
        String path ="/content/crea2minds/us/en/test";

        try {
            WorkflowModel workflowModel= workflowSession.getModel("/var/workflow/models/crea2mindsmodelactivatepage");
            WorkflowData data = workflowSession.newWorkflowData("JCR_PATH",path);
            workflowSession.startWorkflow(workflowModel, data);
            log.info("workflow started");

        } catch (WorkflowException e) {
            e.printStackTrace();

        }
    }
}
