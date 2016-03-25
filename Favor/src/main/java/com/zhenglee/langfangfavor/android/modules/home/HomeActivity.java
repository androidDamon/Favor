package com.zhenglee.langfangfavor.android.modules.home;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.zhenglee.framework.http.api.RpcCallback;
import com.zhenglee.framework.http.api.RpcServiceFactory;
import com.zhenglee.framework.res.annotation.LayoutResource;
import com.zhenglee.framework.ui.BaseActivity;
import com.zhenglee.framework.ui.utils.Toasts;
import com.zhenglee.langfangfavor.R;
import com.zhenglee.langfangfavor.android.modules.test.NewsBean;
import com.zhenglee.langfangfavor.android.modules.test.NewsListService;
import com.zhenglee.langfangfavor.android.modules.test.NewsService;
import com.zhenglee.langfangfavor.android.modules.test.Repository;
import com.zhenglee.langfangfavor.android.modules.test.UserService;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;
import com.zhy.m.permission.ShowRequestPermissionRationale;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@LayoutResource(R.layout.activity_home)
public class HomeActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    private static final int REQUEST_CAMERA = 0;

    private static final int REQUEST_CALL = 1;

    private NewsListService service;

    private UserService usrService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        showCamera();

        final RpcServiceFactory factory = new RpcServiceFactory(this);
        this.service = factory.newRpcService(NewsListService.class, NewsService.API);

//        final RpcServiceFactory factory = new RpcServiceFactory(this);
//        this.usrService = factory.newRpcService(UserService.class, GitHubService.API);

//        showCamera();
        showNews();
    }

    public void showNews(){
        final Thread thread = Thread.currentThread();
        final CountDownLatch signal = new CountDownLatch(1);
        this.service.getNewsList(new RpcCallback<NewsBean>() {

            private void done() {
                if (Thread.currentThread() == thread) {
                    new Thread(new Runnable() {
                        public void run() {
                            signal.countDown();
                        }
                    }).start();
                } else {
                    signal.countDown();
                }
            }

            @Override
            public void onSuccess(NewsBean news) {
                news.getT1348647909107().size();
            }

            @Override
            public void onFailure(Throwable t) {
                String a = t.toString();
                Log.e("asdfasdfasdf", a);
            }
        });
    }

    public void showRepositories() {
        final Thread thread = Thread.currentThread();
        final CountDownLatch signal = new CountDownLatch(1);

        this.usrService.getRepositories("johnsonlee", new RpcCallback<List<Repository>>() {

            private void done() {
                if (Thread.currentThread() == thread) {
                    new Thread(new Runnable() {
                        public void run() {
                            signal.countDown();
                        }
                    }).start();
                } else {
                    signal.countDown();
                }
            }

            @Override
            public void onSuccess(final List<Repository> repos) {
                try {
                    Log.i("asdasd", repos.toString());
                } finally {
                    done();
                }
            }

            @Override
            public void onFailure(final Throwable t) {
                try {
                    Log.e("asdasd", "Retreive repos failed", t);
                } finally {
                    done();
                }
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNews();
//        showRepositories();
    }

    private void showCamera() {
        if(!MPermissions.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA,REQUEST_CAMERA)){
            MPermissions.requestPermissions(this,REQUEST_CAMERA,Manifest.permission.CAMERA);
        }
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            requestCameraPermission();
//
//        } else {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivity(intent);
//        }

    }




    private void requestCameraPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == REQUEST_CAMERA) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toasts.show(this, "camera open", Toast.LENGTH_SHORT);
//            } else {
//                Toasts.show(this, "camera close", Toast.LENGTH_SHORT);
//
//            }
//        } else if (requestCode == REQUEST_CALL) {
//            if (PermissionUtil.verifyPermissions(grantResults)) {
//                Toasts.show(this, "call open", Toast.LENGTH_SHORT);
//            } else {
//                Toasts.show(this, "camera close", Toast.LENGTH_SHORT);
//            }
//
//        }
        MPermissions.onRequestPermissionsResult(this, REQUEST_CAMERA, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @ShowRequestPermissionRationale(REQUEST_CAMERA)
    public void whyNeedCamera(){
        Toasts.show(this,"我要拍照!!!", Toast.LENGTH_SHORT);
    }

    @PermissionGrant(REQUEST_CAMERA)
    public void grant(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }

    @PermissionDenied(REQUEST_CAMERA)
    public void denied(){
        Toasts.show(this, "你不能拍照了!!!", Toast.LENGTH_SHORT);
    }
}
