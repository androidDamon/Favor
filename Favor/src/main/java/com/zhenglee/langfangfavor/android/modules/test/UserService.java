package com.zhenglee.langfangfavor.android.modules.test;

import com.zhenglee.framework.http.api.RpcCallback;
import com.zhenglee.framework.http.api.TargetThread;
import com.zhenglee.framework.http.api.ThreadType;
import com.zhenglee.framework.http.ext.JsonDeserializer;
import com.zhenglee.framework.http.ext.annotation.Path;
import com.zhenglee.framework.http.ext.annotation.PathParameter;
import com.zhenglee.framework.io.Deserialization;

import java.util.List;


@Path("/users")
public interface UserService extends GitHubService {

    @Path("/{username}")
    @Deserialization(JsonDeserializer.class)
    public void getUser(@PathParameter("username") final String username, @TargetThread(ThreadType.WORKER) final RpcCallback<User> callback);
    
    @Path("/{username}/repos")
    @Deserialization(JsonDeserializer.class)
    public void getRepositories(@PathParameter("username") final String username, @TargetThread(ThreadType.WORKER) final RpcCallback<List<Repository>> callback);

}
