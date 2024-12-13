package com.example.quiz11.vo;

public class Options {

    private String code;

    private String optionName;

    public Options() {
    }

    public Options(String code, String optionName) {
        this.code = code;
        this.optionName = optionName;
    }

    public String getCode() {
        return code;
    }

    public String getOptionName() {
        return optionName;
    }
}
