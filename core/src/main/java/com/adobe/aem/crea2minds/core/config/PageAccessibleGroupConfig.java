package com.adobe.aem.crea2minds.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="PageAccessibleGroupConfig",description = "Page Accessible Group Configuration")
public @interface PageAccessibleGroupConfig {

    @AttributeDefinition(name="groupname",type = AttributeType.STRING)
    public String groupname();
}
