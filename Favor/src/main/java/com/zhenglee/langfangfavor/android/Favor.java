package com.zhenglee.langfangfavor.android;

import android.content.SharedPreferences;

import com.zhenglee.framework.ApplicationContext;
import com.zhenglee.framework.business.BusinessContext;
import com.zhenglee.framework.network.OkRequest;
import com.zhenglee.framework.network.model.HttpHeaders;
import com.zhenglee.langfangfavor.android.modules.persistence.FavorPersistenceManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Created by zhenglee on 15/12/25.
 */
public class Favor extends ApplicationContext implements BusinessContext, SharedPreferences.OnSharedPreferenceChangeListener{

    public static boolean isDebug = true;

    private static final String TAG = "BeastBikes";

    private static final Logger logger = LoggerFactory.getLogger(TAG);



    private FavorPersistenceManager persistenceManager;

    private SharedPreferences defaultSp;

    private SharedPreferences packageSp;

    public FavorPersistenceManager getPersistenceManager() {
        return this.persistenceManager;
    }

    public Favor(Object cache) {
        super(cache);
    }

    @Override
    public void onCreate() {
//        PreferenceManager.setDefaultValues(this, R.xml.favor, true);

        super.onCreate();

        HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");
        headers.put("commonHeaderKey2", "commonHeaderValue2");

        OkRequest.init(this);
        OkRequest.getInstance().debug("OkRequest").setConnectTimeout(10000).setReadTimeOut(10000).setWriteTimeOut(10000).addCommonHeaders(headers);
    }

    @Override
    public String getErrorMessage(int errorCode) {
        return null;
    }

    @Override
    public String getErrorMessage(Locale locale, int errorCode) {
        return null;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        final Object value = sp.getAll().get(key);
        logger.trace("onSharedPreferenceChanged: " + key + " ="
                + String.valueOf(value));
    }

}
