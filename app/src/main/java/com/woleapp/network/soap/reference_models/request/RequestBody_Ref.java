package com.woleapp.network.soap.reference_models.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * User role body
 * Created by Jeeson on 16/7/15.
 */
@Root(name = "soapenv:Body", strict = false)
public class RequestBody_Ref {

    @Element(name = "getWeatherbyCityName", required = false)
    public RequestModel_Ref getWeatherbyCityName;
}
