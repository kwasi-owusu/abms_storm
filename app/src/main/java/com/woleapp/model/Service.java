package com.woleapp.model;

public class Service
{
    int service_id = 0;
    String service_name = "";
    int service_drawable = 0;
    String service_providers="";

    public Service(int service_id, String service_name, int service_drawable) {
        this.service_id = service_id;
        this.service_name = service_name;
        this.service_drawable = service_drawable;
    }

    public Service(int service_id, String service_name, int service_drawable,String service_providers) {
        this.service_id = service_id;
        this.service_name = service_name;
        this.service_drawable = service_drawable;
        this.service_providers = service_providers;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public int getService_drawable() {
        return service_drawable;
    }

    public void setService_drawable(int service_drawable) {
        this.service_drawable = service_drawable;
    }

    public String getService_providers() {
        return service_providers;
    }

    public void setService_providers(String service_providers) {
        this.service_providers = service_providers;
    }
}
