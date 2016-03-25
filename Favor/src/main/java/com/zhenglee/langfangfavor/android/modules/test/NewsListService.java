package com.zhenglee.langfangfavor.android.modules.test;

import com.zhenglee.framework.http.api.RpcCallback;
import com.zhenglee.framework.http.api.TargetThread;
import com.zhenglee.framework.http.api.ThreadType;
import com.zhenglee.framework.http.ext.JsonDeserializer;
import com.zhenglee.framework.http.ext.annotation.Path;
import com.zhenglee.framework.io.Deserialization;

/**
 * Created by zhenglee on 16/1/5.
 */
@Path("/nc/article")
public interface NewsListService extends NewsService {

    @Path("/headline/T1348647909107/20-20.html")
    @Deserialization(JsonDeserializer.class)
    public void getNewsList(
            @TargetThread(ThreadType.WORKER)
            final RpcCallback<NewsBean> callback
    );
}
