package com.woleapp.network.soap.reference_models.request;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Get the parameters that need to be passed for specific information.
 * Created by Jeeson on 16/7/15.
 */

public class RequestModel_Ref {
    @Attribute(name = "xmlns")
    public String cityNameAttribute;

    @Element(name = "theCityName", required = false)
    public String theCityName;     //城市名字

}
