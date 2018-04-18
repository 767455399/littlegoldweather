package com.littlegold.littlegoldweather.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.littlegold.littlegoldweather.R;
import com.littlegold.littlegoldweather.base.BaseFragment;
import com.littlegold.littlegoldweather.model.ProvinceModel;
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
 * 省
 */

public class ProvinceFragment extends BaseFragment {
    private static final String TAG = "ProvinceFragment";
    private String path = "http://guolin.tech/api/china";
    private RecyclerView recyclerView;
    private ProvinceAdapter adapter;
    List<ProvinceModel> provinceList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_province, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapter = new ProvinceAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        loadData();
    }

    public static ProvinceFragment newInstance() {
        Bundle args = new Bundle();
        ProvinceFragment fragment = new ProvinceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    private void loadData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(path)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getContext(), "获取省份失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                    provinceList = new Gson().fromJson(response.body().string(), new TypeToken<List<ProvinceModel>>() {}.getType());
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
            }
        });

    }

    class ProvinceAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_item_province, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.contentTextView.setText(provinceList.get(position).name);
            holder.contentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragmentNeedToBack(CityFragment.newInstance(String.valueOf(provinceList.get(position).id)));
                }
            });
        }


        @Override
        public int getItemCount() {
            return provinceList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView contentTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            contentTextView = (TextView) itemView.findViewById(R.id.contentTextView);
        }
    }
}
