package com.mavenMVC.util;

import net.sf.json.JSONObject;

/**
 * Created by lizai on 15/6/13.
 */
public class RequestManager {

    private int ERROR_CODE = 0;

    private String ERROR_MESSAGE = "";

    public RequestManager(){
        this.ERROR_CODE = 0;
        this.ERROR_MESSAGE = "";
    }

    public RequestManager(int ERROR_CODE, String ERROR_MESSAGE) {
        this.ERROR_CODE = ERROR_CODE;
        this.ERROR_MESSAGE = ERROR_MESSAGE;
    }

    public JSONObject printJson(Object stringer){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error_code", ERROR_CODE);
        jsonObject.put("error_message", ERROR_MESSAGE);
        jsonObject.put("data",stringer);
        return jsonObject;
    }

    public void putErrorMessage(String error){
        ERROR_CODE = 1;
        ERROR_MESSAGE = error;
    }

    public int getERROR_CODE() {
        return ERROR_CODE;
    }

    public void setERROR_CODE(int ERROR_CODE) {
        this.ERROR_CODE = ERROR_CODE;
    }

    public String getERROR_MESSAGE() {
        return ERROR_MESSAGE;
    }

    public void setERROR_MESSAGE(String ERROR_MESSAGE) {
        this.ERROR_MESSAGE = ERROR_MESSAGE;
    }
}
