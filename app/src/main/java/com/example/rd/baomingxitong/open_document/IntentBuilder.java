package com.example.rd.baomingxitong.open_document;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

public class IntentBuilder {

    public static void startIntent(Context context, File file,String type) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(context, "net.csdn.blog.ruancoder.servicetest.example.com.myapplication", file);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data,type);
        context.startActivity(intent);
    }
    public static void viewFile(final Context context, final String filePath) {
        String type = getMimeType(filePath);

        if (!TextUtils.isEmpty(type) && !TextUtils.equals(type, "*/*")) {
            /* 设置intent的file与MimeType */
             startIntent(context,new File(filePath),type);
        } else {
            // unknown MimeType
            Log.i("lei", "le");
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setTitle("选择文件类型");

             CharSequence[] menuItemArray = new CharSequence[] {
             "文本","音频","视频","图像"
              };
             
   
            //CharSequence[] menuItemArray = new CharSequence[] {"文本", "音频", "视频", "图像"};
            dialogBuilder.setItems(menuItemArray, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String selectType = "*/*";
                    switch (which) {
                        case 0:
                            selectType = "text/plain";
                            break;
                        case 1:
                            selectType = "audio/*";
                            break;
                        case 2:
                            selectType = "video/*";
                            break;
                        case 3:
                            selectType = "image/*";
                            break;
                    }
                    startIntent(context,new File(filePath),selectType);

                }
            });
            dialogBuilder.show();
        }
    }


    private static String getMimeType(String filePath) {
        int dotPosition = filePath.lastIndexOf('.');
        if (dotPosition == -1)
            return "*/*";

        String ext = filePath.substring(dotPosition + 1, filePath.length()).toLowerCase();
        String mimeType = MimeUtils.guessMimeTypeFromExtension(ext);;
        if (ext.equals("mtz")) {
            mimeType = "application/miui-mtz";
        }

        return mimeType != null ? mimeType : "*/*";
    }
}