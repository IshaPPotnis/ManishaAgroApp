package com.example.manishaagro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MngrActivity extends AppCompatActivity {
    Toolbar mgrtoolbar;
    ViewPager mgrviewPager;
    TabLayout mgrtabLayout;
    String tempEmpval;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mngr);


        mgrtoolbar=findViewById(R.id.Mngrtoolbar);
        setSupportActionBar(mgrtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#00A5FF"));
        actionBar.setBackgroundDrawable(colorDrawable);


        mgrviewPager=findViewById(R.id.viewpager1);
        SetUpPager(mgrviewPager);
        mgrtabLayout=findViewById(R.id.tab2);

        mgrtabLayout.setupWithViewPager(mgrviewPager);
        Intent intent = getIntent();
        tempEmpval = intent.getStringExtra("Login Manager");
        Log.v("yek", "keyyy" + tempEmpval);


    }
    public Bundle getMgrData() {
        Bundle hm = new Bundle();
        hm.putString("tempval2",tempEmpval);
        return hm;
    }



    private void SetUpPager(ViewPager viewPager)
    {
        ViewPagerAdp1 adp=new ViewPagerAdp1(getSupportFragmentManager());
        adp.addFragment(new MgrProfileFrag(),"Profile");
        adp.addFragment(new StatusMgrFrag(),"Status");
        viewPager.setAdapter(adp);
    }

    private class ViewPagerAdp1 extends FragmentPagerAdapter
    {
        private final List<Fragment> Fraglist = new ArrayList<>();
        private final List<String> Names = new ArrayList<>();

        public ViewPagerAdp1(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return  Fraglist.get(position);
        }


        public void addFragment(Fragment fragment, String name) {
            Fraglist.add(fragment);
            Names.add(name);
        }

        @Override
        public int getCount() {
            return Fraglist.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return Names.get(position);
        }
    }

}
