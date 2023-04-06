package com.woleapp.network.soap.response;



import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * 用户角色返回
 * Created by Jeeson on 16/7/15.
 */

/*@Root(name = "ns2:processResponse")*/
@Root(name = "processResponse")
/*@Namespace(reference = "http://ws.fundgate.etranzact.com/")*/
public class ProcessResponse {

    @Element(name = "response")
    public FundsTransferResponse fundsTransferResponse;



}
