package com.woleapp.network.soap.request;

import com.woleapp.util.Constants;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * User role body
 * Created by Jeeson on 16/7/15.
 */

@Root(name = "ws:process", strict = false)
/*@Root(name = "process", strict = false)*/
public class ProcessModel {

    @Element(name = Constants.PN_REQUEST, required = false)
    public FundsTransferRequestModel fundsTransferRequestModel;
}
