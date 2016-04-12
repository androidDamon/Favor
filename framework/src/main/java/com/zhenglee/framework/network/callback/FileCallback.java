package com.zhenglee.framework.network.callback;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.zhenglee.framework.network.OkRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

public abstract class FileCallback extends AbsCallback<File> {

    public static final String DM_TARGET_FOLDER = File.separator + "download" + File.separator; //下载目标文件夹

    /** 目标文件存储的文件夹路径 */
    private String destFileDir;
    /** 目标文件存储的文件名 */
    private String destFileName;

    public FileCallback(String destFileName) {
        this(Environment.getExternalStorageDirectory() + DM_TARGET_FOLDER, destFileName);
    }

    /**
     * @param destFileDir  要保存的目标文件夹
     * @param destFileName 要保存的文件名
     */
    public FileCallback(@NonNull String destFileDir, @NonNull String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    @Override
    public File parseNetworkResponse(Response response) {
        try {
            return saveFile(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private File saveFile(Response response) throws IOException {
        File dir = new File(destFileDir);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, destFileName);
        if (file.exists()) file.delete();

        long previousTime = System.currentTimeMillis();
        InputStream is = null;
        byte[] buf = new byte[2048];
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;
            int len;
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;

                //计算下载速度
                long totalTime = (System.currentTimeMillis() - previousTime) / 1000;
                if (totalTime == 0) totalTime += 1;
                final long networkSpeed = finalSum / totalTime;
                OkRequest.getInstance().getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        downloadProgress(finalSum, total, finalSum * 1.0f / total, networkSpeed);   //进度回调的方法
                    }
                });
            }
            fos.flush();
            return file;
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** 通过 ‘？’ 和 ‘/’ 判断文件名 */
    private String getUrlFileName(String url) {
        int index = url.lastIndexOf('?');
        String filename;
        if (index > 1) {
            filename = url.substring(url.lastIndexOf('/') + 1, index);
        } else {
            filename = url.substring(url.lastIndexOf('/') + 1);
        }
        return filename;
    }
}
