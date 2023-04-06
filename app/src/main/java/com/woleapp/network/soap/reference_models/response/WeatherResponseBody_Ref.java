package com.woleapp.network.soap.reference_models.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 用户角色返回body
 * Created by Jeeson on 16/7/15.
 */
@Root(name = "Body")
public class WeatherResponseBody_Ref {

    @Element(name = "getWeatherbyCityNameResponse", required = false)
    public WeatherResponseModel_Ref getWeatherbyCityNameResponse;

    @Override
    public String toString() {
        return super.toString();
    }
}
