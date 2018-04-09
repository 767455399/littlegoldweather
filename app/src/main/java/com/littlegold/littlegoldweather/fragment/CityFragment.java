package com.littlegold.littlegoldweather.fragment;

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
 */

public class CityFragment extends BaseFragment {
    private static final String TAG = "CityFragment";
    private String path = "http://guolin.tech/api/china";
    private RecyclerView recyclerView;
    private CityAdapter adapter;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CityAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        if (!TextUtils.isEmpty(getArguments().getString("id"))) {
            loadData(getArguments().getString("id"));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    private void loadData(String id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(path + "/" + id)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                provinceList = new Gson().fromJson(response.body().string(), new TypeToken<List<ProvinceModel>>() {
                }.getType());
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public static CityFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id", id);
        CityFragment fragment = new CityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    class CityAdapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.fragment_item_province, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.contentTextView.setText(provinceList.get(position).name);
            holder.contentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragmentNeedToBack(AreaFragment.newInstance(getArguments().getString("id")+"/"+String.valueOf(provinceList.get(position).id)));
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
