package com.woleapp.network.soap.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

@Root(name = "soapenv:Envelope")
@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi"),
        @Namespace(reference = "http://www.w3.org/2001/XMLSchema", prefix = "xsd"),
        @Namespace(reference = "http://schemas.xmlsoap.org/soap/encoding/", prefix = "enc"),
        @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/", prefix = "soapenv"),
        @Namespace(reference = "http://ws.fundgate.etranzact.com/", prefix = "ws")
})
/*xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
        xmlns:ws="http://ws.fundgate.etranzact.com/*/
public class RequestEnvelope {
    @Element(name = "soapenv:Body", required = false)
    /*@Element(name = "Body", required = false)*/
    public RequestBody body;

}
