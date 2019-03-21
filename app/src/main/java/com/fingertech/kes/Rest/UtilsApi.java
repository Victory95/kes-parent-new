package com.fingertech.kes.Rest;

import com.fingertech.kes.Controller.Auth;

public class UtilsApi {
    private static final String BASE_URL_API = "https://kes.co.id/api/";

    public static Auth getAPIService(){
        return ClientApi.getClient(BASE_URL_API).create(Auth.class);
    }
}
