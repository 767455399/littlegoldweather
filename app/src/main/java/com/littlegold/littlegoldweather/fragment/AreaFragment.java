package com.littlegold.littlegoldweather.fragment;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.littlegold.littlegoldweather.R;
import com.littlegold.littlegoldweather.base.BaseFragment;
import com.littlegold.littlegoldweather.dialog.SureDialog;
import com.littlegold.littlegoldweather.model.AreaModel;
import com.littlegold.littlegoldweather.sqlite.MyDatabaseHelper;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangqing on 2018/4/6.
 */

public class AreaFragment extends BaseFragment {
    private static final String TAG = "AreaFragment";
    private String path = "http://guolin.tech/api/china";
    private RecyclerView recyclerView;
    private AreaAdapter areaAdapter;
    List<AreaModel> areaList = new ArrayList<>();
    private MyDatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_province, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        areaAdapter = new AreaAdapter();
        recyclerView.setAdapter(areaAdapter);
        databaseHelper=new MyDatabaseHelper(getContext(),"wq.db",null,1);
    }

    public static AreaFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id", id);
        AreaFragment fragment = new AreaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        loadData();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    private void loadData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(path + "/" + getArguments().getString("id"))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                areaList = new Gson().fromJson(response.body().string(), new TypeToken<List<AreaModel>>() {
                }.getType());
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        areaAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    class AreaAdapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.fragment_item_province, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.contentTextView.setText(areaList.get(position).name);
            holder.contentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String name=areaList.get(position).name;
                    final String cityCode=areaList.get(position).weather_id;
                    if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(cityCode)){
//                        if (getActivity().hasWindowFocus()) {
                            new SureDialog(getContext(), new SureDialog.Listener() {
                                @Override
                                public void sure() {
                                    insertData(name,cityCode);
                                }
                            }).show();
//                        }
                    }else {
                        Toast.makeText(getContext(), "缺少必要信息", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return areaList.size();
        }
    }

    private void insertData(String name,String cityCode) {
        databaseHelper.getWritableDatabase();
        SQLiteDatabase sqLiteDatabase=databaseHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("citycode",cityCode);
        sqLiteDatabase.insert("CityCode",null,values);
        values.clear();
        getActivity().finish();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView contentTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            contentTextView = (TextView) itemView.findViewById(R.id.contentTextView);
        }
    }
}
