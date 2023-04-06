package com.woleapp.network.soap.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * 用户角色返回body
 * Created by Jeeson on 16/7/15.
 */
@Root(name = "Body")//S:Body
/*@Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/", prefix = "S")*/
public class ResponseBody {

    @Element(name = "processResponse", required = false)//ns2:
    /*@Namespace(reference = "http://ws.fundgate.etranzact.com/", prefix = "ns2")// xmlns:ns2=*/
    //@Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/", prefix = "S")

    //@Element(name = "processResponse", required = false)
    public ProcessResponse response;

    @Override
    public String toString() {
        return super.toString();
    }
}
