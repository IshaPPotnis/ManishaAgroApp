package com.example.manishaagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class EmpActivity extends AppCompatActivity {
    public ApiInterface apiInterface;

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    String tempEmpval;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#00A5FF"));
        actionBar.setBackgroundDrawable(colorDrawable);



        viewPager=findViewById(R.id.viewpager);
        SetUpPager(viewPager);
        tabLayout=findViewById(R.id.tab1);

        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        tempEmpval = intent.getStringExtra("Login Employee");
        Log.v("yek", "keyyy" + tempEmpval);

    }

    public Bundle getEmpData() {
        Bundle hm = new Bundle();
        hm.putString("tempval1",tempEmpval);
        return hm;
    }

    private void SetUpPager(ViewPager viewPager)
    {
        ViewPagerAdp adp=new ViewPagerAdp(getSupportFragmentManager());
        adp.addFragment(new ProfileFrag(),"Profile");
        adp.addFragment(new EmployeeFrag(),"Employee");
        viewPager.setAdapter(adp);
    }
    private class ViewPagerAdp extends FragmentPagerAdapter
    {
        private final List<Fragment> Fraglist = new ArrayList<>();
        private final List<String> Names = new ArrayList<>();

        public ViewPagerAdp(FragmentManager fm) {
            super(fm);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return  Fraglist.get(position);
        }

        @Override
        public int getCount() {
            return Fraglist.size();
        }

        public void addFragment(Fragment fragment, String name) {
            Fraglist.add(fragment);
            Names.add(name);

        }
        @Override
        public CharSequence getPageTitle(int position) {
            return  Names.get(position);
        }

    }




}
