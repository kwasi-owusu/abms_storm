package com.woleapp.network.soap.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * User role body
 * Created by Jeeson on 16/7/15.
 */

@Root(name = "soapenv:Body", strict = false)
/*@Root(name = "Body", strict = false)*/
public class RequestBody {

    @Element(name = "ws:process", required = false)
    /*@Element(name = "process", required = false)*/
    /*@Namespace(reference = "http://ws.fundgate.etranzact.com/", prefix = "ws")*/
    public ProcessModel processModel;
}
