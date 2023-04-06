package com.woleapp.network.soap.reference_models.response;



import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * 用户角色返回
 * Created by Jeeson on 16/7/15.
 */

@Root(name = "getWeatherbyCityNameResponse")
@Namespace(reference = "http://WebXml.com.cn/")
public class WeatherResponseModel_Ref {

    @ElementList(name = "getWeatherbyCityNameResult")
    public List<String> result;

}
