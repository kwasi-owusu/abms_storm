package com.woleapp.model;

public class Color
{
    String name;
    String color_code;

    public Color(String name, String color_code) {
        this.name = name;
        this.color_code = color_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }
}
