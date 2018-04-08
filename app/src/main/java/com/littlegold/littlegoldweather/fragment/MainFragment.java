package com.littlegold.littlegoldweather.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littlegold.littlegoldweather.R;
import com.littlegold.littlegoldweather.activity.FragmentHostActivity;
import com.littlegold.littlegoldweather.base.BaseFragment;
import com.littlegold.littlegoldweather.model.ListDataModel;
import com.littlegold.littlegoldweather.sqlite.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangqing on 2018/4/5.
 */

public class MainFragment extends BaseFragment {
    private MyDatabaseHelper databaseHelper;
    private List<ListDataModel> list=new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout contentLinearLayout;
    private TextView nullTextView;
    private MainViewPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contentLinearLayout=(LinearLayout)view.findViewById(R.id.contentLinearLayout);
        nullTextView=(TextView)view.findViewById(R.id.nullTextView);
        nullTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FragmentHostActivity.class));
            }
        });
        /**
         * 初始化数据库
         */
        databaseHelper=new MyDatabaseHelper(getContext(),"wq.db",null,1);
        /**
         * 如果数据库已存在，则直接打开，否则创建一个新的数据库。
         */
        databaseHelper.getWritableDatabase();
        tabLayout=(TabLayout)view.findViewById(R.id.tabLayout);
        viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        adapter=new MainViewPagerAdapter(getChildFragmentManager());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        queryData();
    }

    class MainViewPagerAdapter extends FragmentPagerAdapter{
        public MainViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(list.size()>0){
                return WeatherFragment.newInstance(list.get(position).citycode);
            }else{
                return null;
            }

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).name;
        }
    }

    private void queryData() {
        SQLiteDatabase sqLiteDatabase=databaseHelper.getWritableDatabase();
        //查询表中所有数据
        Cursor cursor=sqLiteDatabase.query("CityCode",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                ListDataModel listDataModel=new ListDataModel();
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String cityCode=cursor.getString(cursor.getColumnIndex("citycode"));
                listDataModel.name=name;
                listDataModel.citycode=cityCode;
                list.add(listDataModel);
            }while (cursor.moveToNext());
        }
        cursor.close();
        if(list.size()==0){
            nullTextView.setVisibility(View.VISIBLE);
            contentLinearLayout.setVisibility(View.GONE);
        }else{
            nullTextView.setVisibility(View.GONE);
            contentLinearLayout.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }


}
