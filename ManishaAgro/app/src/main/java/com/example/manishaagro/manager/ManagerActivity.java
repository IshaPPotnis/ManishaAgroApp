package com.example.manishaagro.manager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.manishaagro.R;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.manishaagro.utils.Constants.EMPI_USER;
import static com.example.manishaagro.utils.Constants.EMPLOYEE;
import static com.example.manishaagro.utils.Constants.LOGIN_MANAGER;
import static com.example.manishaagro.utils.Constants.PROFILE;

public class ManagerActivity extends AppCompatActivity {
    Toolbar managerToolbar;
    ViewPager managerViewPager;
    TabLayout managerTabLayout;
    String tempManagerValue = "";
    String tempManagerIDValue = "";

    com.getbase.floatingactionbutton.FloatingActionButton fabMgrbtn1, fabMgrbtn2;
    FloatingActionsMenu fabActionMenuMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mngr);

        managerToolbar = findViewById(R.id.Mngrtoolbar);
        setSupportActionBar(managerToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }

        managerViewPager = findViewById(R.id.viewpager1);
        SetUpPager(managerViewPager);
        managerTabLayout = findViewById(R.id.tab2);

        fabActionMenuMgr = findViewById(R.id.fabMgrActivity);
        fabMgrbtn1 = findViewById(R.id.fabMgrActivity1);
        fabMgrbtn2 = findViewById(R.id.fabMgrActivity2);

        managerTabLayout.setupWithViewPager(managerViewPager);
        Intent intent = getIntent();
        tempManagerValue = intent.getStringExtra(LOGIN_MANAGER);
        tempManagerIDValue = intent.getStringExtra(EMPI_USER);


        managerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:

                        //fabbtn2.show();

                        fabActionMenuMgr.setVisibility(View.INVISIBLE);
                        fabMgrbtn1.setVisibility(View.INVISIBLE);
                        fabMgrbtn2.setVisibility(View.INVISIBLE);



                        break;
                    case 1:

                        fabActionMenuMgr.setVisibility(View.VISIBLE);
                        fabMgrbtn1.setVisibility(View.VISIBLE);

                        //fabbtn1.show();
                        fabMgrbtn2.setVisibility(View.VISIBLE);


                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Log.v("yek", "keyyy" + tempManagerValue);
        Log.v("ddd", "ddd" + tempManagerIDValue);
    }

    public Bundle getMgrData() {
        Bundle hm = new Bundle();
        hm.putString("tempval2", tempManagerValue);
        hm.putString("tempManagerIDval2", tempManagerIDValue);
        return hm;
    }

    public com.getbase.floatingactionbutton.FloatingActionsMenu getFloatingActionMenuMgr() {
        return fabActionMenuMgr;
    }

    public com.getbase.floatingactionbutton.FloatingActionButton getFloatingButton1Mgr() {
        return fabMgrbtn1;
    }

    public com.getbase.floatingactionbutton.FloatingActionButton geFloatingButton2Mgr() {
        return fabMgrbtn2;
    }


    private void SetUpPager(ViewPager viewPager) {
        ViewPagerAdapter1 adp = new ViewPagerAdapter1(getSupportFragmentManager());
        adp.addFragment(new ManagerProfileFragment(), PROFILE);
        adp.addFragment(new EmployeeFragment(), EMPLOYEE);
        viewPager.setAdapter(adp);
    }

    private static class ViewPagerAdapter1 extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> namesList = new ArrayList<>();

        public ViewPagerAdapter1(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return namesList.get(position);
        }

        public void addFragment(Fragment fragment, String name) {
            fragmentList.add(fragment);
            namesList.add(name);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManagerActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}