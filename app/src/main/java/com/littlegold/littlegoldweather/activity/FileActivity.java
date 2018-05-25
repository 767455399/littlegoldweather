package com.littlegold.littlegoldweather.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.littlegold.littlegoldweather.R;

import java.io.File;
import java.io.IOException;

public class FileActivity extends AppCompatActivity {
    private static final int CALL_PHONE_PERMISSION = 1;
    private static final String PACKAGE_URL_SCHEME = "package:";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        checkPermissions();
        initView();
    }

    private void initView() {
        textView=(TextView)findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file=new File(Environment.getExternalStorageState(),"/mnt/sdcard/tmp/one/two/three/wq");
                /**
                 * 如果文件存在
                 */
                if(file.exists()){
                    Toast.makeText(FileActivity.this, "文件已经存在", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        file.createNewFile();
                        Toast.makeText(FileActivity.this, "文件创建成功", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(FileActivity.this, "出错了", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(FileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            /**
             * 如果权限未被禁止弹框则继续申请，并告知用途以及不允许权限的弊端。
             */
            if (!ActivityCompat.shouldShowRequestPermissionRationale(FileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(FileActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CALL_PHONE_PERMISSION);
            } else {
                /**
                 * 完全禁止且无法再弹权限申请框，这时弹框提示用户去设置开启权限。
                 */
                            /**
                             * 去设置里设置权限
                             */
                            Intent intent1 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent1.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
                            startActivity(intent1);

            }
        } else {

        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(CALL_PHONE_PERMISSION==requestCode){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "申请权限成功", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "申请权限失败", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
