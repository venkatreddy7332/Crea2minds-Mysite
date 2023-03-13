package com.adobe.aem.crea2minds.core.services;

import com.adobe.aem.crea2minds.core.config.PageAccessibleGroupConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = PageAccessibleGroupServiceImpl.class,immediate = true)
@Designate(ocd= PageAccessibleGroupConfig.class)
public class PageAccessibleGroupServiceImpl {

    private String groupname;

    @Activate
    public void activate(PageAccessibleGroupConfig config){

        groupname=config.groupname();

    }

    public String getGroupname() {
        return groupname;
    }
}
