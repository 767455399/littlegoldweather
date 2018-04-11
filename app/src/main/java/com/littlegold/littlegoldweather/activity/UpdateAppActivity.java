package com.littlegold.littlegoldweather.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.littlegold.littlegoldweather.R;
import com.littlegold.littlegoldweather.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class UpdateAppActivity extends BaseActivity {
    private String downLoadUrl = "www.baidu.apk";
    private ProgressBar updateProgressBar;
    private TextView downTextView,progress;
    private TextView fileNameTextView;
    private DownLoadCompleteReceiver receiver;
     DownloadManager downloadManager;
    DownloadManager.Request request;
    private Timer timer;
    TimerTask task;
    long id;


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle=msg.getData();
            int pro=bundle.getInt("pro");
            String name=bundle.getString("name");
            updateProgressBar.setProgress(pro);
            progress.setText(String.valueOf(pro)+"%");
            fileNameTextView.setText(name);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_update_app);
        updateProgressBar = f(R.id.updateProgressBar);
        downTextView=f(R.id.downTextView);
        progress=f(R.id.progress);
        fileNameTextView=f(R.id.fileNameTextView);
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        filter.addCategory("android.intent.category.DEFAULT");
        receiver = new DownLoadCompleteReceiver();
        registerReceiver(receiver, filter);
        init();
        downTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=downloadManager.enqueue(request);
                task.run();
                downTextView.setClickable(false);

            }
        });

    }

    @Override
    protected void loadData() {

    }

    public class DownLoadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            }
        }
    }

    public void init() {
//        SharedPreferences sp = getSharedPreferences("downloadapp", Activity.MODE_PRIVATE);
//        final long id = sp.getLong("downloadid", 0);
//        if (downLoadApkId == id) {
            downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
            request = new DownloadManager.Request(Uri.parse(downLoadUrl));
            request.setTitle("即时天气");
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE + DownloadManager.Request.NETWORK_WIFI);
            request.setAllowedOverRoaming(false);
            request.setMimeType("application/vnd.android.package-archive");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            //创建目录
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();
            //设置文件存放路径
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "little_gold_weather.apk");
            updateProgressBar.setMax(100);
            final DownloadManager.Query query = new DownloadManager.Query();
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    Cursor cursor = downloadManager.query(query.setFilterById(id));
                    if (cursor != null && cursor.moveToFirst()) {
                        if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                            updateProgressBar.setProgress(100);
                            install(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/little_gold_weather.apk");
                            task.cancel();
                        }
                        String title=cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
                        String address = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                        int pro =  (bytes_downloaded * 100) / bytes_total;
                        Message message=Message.obtain();
                        Bundle bundle=new Bundle();
                        bundle.putInt("pro",pro);
                        bundle.putString("name",title);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                    cursor.close();
                }
            };
//            timer.schedule(task,0,1000);
//            Uri downLoadFileUri = downloadManager.getUriForDownloadedFile(downLoadApkId);
//            if (downLoadFileUri != null) {
//                Intent install = new Intent(Intent.ACTION_VIEW);
//                File apkFile = this.getExternalFilesDir("DownLoad/downapp");
//                //对安卓版本进行判断
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    Uri contentUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", apkFile);
//                    install.setDataAndType(contentUri, "application/vnd.android.package-archive");
//                } else {
//                    install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
//                }
//                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(install);
//            } else {
//                Toast.makeText(this, "下载失败", Toast.LENGTH_SHORT).show();
//            }
//        }
    }

    private void install(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//4.0以上系统弹出安装成功打开界面
        startActivity(intent);
    }



}
