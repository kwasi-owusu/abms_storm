package com.woleapp.network.soap.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * 用户角色返回总信息
 * Created by Jeeson on 16/7/15.
 */
@Root(name = "soapenv:Envelope")
@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi"),
        @Namespace(reference = "http://www.w3.org/2001/XMLSchema", prefix = "xsd"),
        @Namespace(reference = "http://schemas.xmlsoap.org/soap/encoding/", prefix = "enc"),
        @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/", prefix = "soapenv"),
        @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/", prefix = "S")
})
public class ResponseEnvelope {
    /*@Element(name = "S:Body")*/
    @Element(name = "Body")
    public ResponseBody body;

}
