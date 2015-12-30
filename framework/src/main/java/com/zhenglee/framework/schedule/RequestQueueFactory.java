package com.zhenglee.framework.schedule;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by zhenglee on 15/10/21.
 */
public class RequestQueueFactory {

    private RequestQueueFactory(){
    }

    public static RequestQueue newRequestQueue(Context context){
        return Volley.newRequestQueue(context);
    }

}
