package com.example.manishaagro;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.manishaagro.utils.Constants.EMPI_USER;
import static com.example.manishaagro.utils.Constants.LOGIN_EMPLOYEE;
import static com.example.manishaagro.utils.Constants.PROFILE;
import static com.example.manishaagro.utils.Constants.STATUS;

public class EmployeeActivity extends AppCompatActivity {
    Toolbar employeeToolbar;
    ViewPager employeeViewPager;
    ViewPager.OnPageChangeListener mpage;
    TabLayout employeeTabLayout;
    String tempEmployeeValue = "";
    String tempEmployeeIDValue = "";


    com.getbase.floatingactionbutton.FloatingActionButton fabbtn1,fabbtn2;
    FloatingActionsMenu fabbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp);

        employeeToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(employeeToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        employeeViewPager = findViewById(R.id.viewpager);
        SetUpPager(employeeViewPager);
        employeeTabLayout = findViewById(R.id.tab1);
       fabbtn=findViewById(R.id.fabEmpActivity);
        fabbtn1=findViewById(R.id.fabEmpActivity1);
        fabbtn2=findViewById(R.id.fabEmpActivity2);
        employeeTabLayout.setupWithViewPager(employeeViewPager);

        Intent intent = getIntent();
        tempEmployeeValue = intent.getStringExtra(LOGIN_EMPLOYEE);
        tempEmployeeIDValue = intent.getStringExtra(EMPI_USER);
        Log.v("yek", "keyyy" + tempEmployeeValue);

      //  fabbtn.setVisibility(View.INVISIBLE);



        employeeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        fabbtn.setVisibility(View.INVISIBLE);
                        fabbtn1.setVisibility(View.INVISIBLE);
                        fabbtn2.setVisibility(View.INVISIBLE);
                    //    fabbtn.hide();
                      //  fabbtn1.hide();
                        //fabbtn2.hide();
                        break;
                    case 1:
                        fabbtn.setVisibility(View.VISIBLE);
                        fabbtn1.setVisibility(View.VISIBLE);

                        //fabbtn1.show();
                        fabbtn2.setVisibility(View.VISIBLE);

                        //fabbtn2.show();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

  public com.getbase.floatingactionbutton.FloatingActionsMenu geFloatingButton()
    {
        return fabbtn;
    }
   public com.getbase.floatingactionbutton.FloatingActionButton getFloatingButton1()
   {
       return fabbtn1;
   }
    public com.getbase.floatingactionbutton.FloatingActionButton geFloatingButton2()
    {
        return fabbtn2;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeActivity.this);
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

    public Bundle getEmpData() {
        Bundle hm = new Bundle();
        hm.putString("tempval1", tempEmployeeValue);
        hm.putString("tempval2EMPID", tempEmployeeIDValue);
        return hm;
    }

    private void SetUpPager(ViewPager viewPager) {
        ViewPagerAdapter adp = new ViewPagerAdapter(getSupportFragmentManager());
        adp.addFragment(new ProfileFragment(), PROFILE);
        adp.addFragment(new EmployeeStatusFragment(), STATUS);



        viewPager.setAdapter(adp);
    }



    private static class ViewPagerAdapter extends FragmentPagerAdapter  {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> namesList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
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

        public void addFragment(Fragment fragment, String name) {
            fragmentList.add(fragment);
            namesList.add(name);
        }



        @Override
        public CharSequence getPageTitle(int position) {
            return namesList.get(position);
        }
    }





}