package com.mobilemerit.javafiles;

/**
 * Created by Abhishek jana on 7/25/2016.
 */

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    //{"id":"1","mobile":"9650368241"}
    String id ;
    String mobile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
