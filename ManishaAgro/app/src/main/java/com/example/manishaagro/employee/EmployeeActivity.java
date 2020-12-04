package com.example.manishaagro.employee;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.manishaagro.ConnectionDetector;
import com.example.manishaagro.OfflinrDataActivity;
import com.example.manishaagro.PendingFragment;
import com.example.manishaagro.Profile;
import com.example.manishaagro.R;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.manishaagro.utils.Constants.DEALER;
import static com.example.manishaagro.utils.Constants.EMPI_USER;
import static com.example.manishaagro.utils.Constants.LOGIN_EMPLOYEE;
import static com.example.manishaagro.utils.Constants.PENDING;
import static com.example.manishaagro.utils.Constants.STATUS;

public class EmployeeActivity extends AppCompatActivity {
    Toolbar employeeToolbar;
    ViewPager employeeViewPager;
    TabLayout employeeTabLayout;
    String tempEmployeeValue = "";
    String tempEmployeeIDValue = "";
    ConnectionDetector connectionDetector;
    com.getbase.floatingactionbutton.FloatingActionButton fabbtn1, fabbtn2, fabbtn3, fabbtn4, fabbtn5;
    FloatingActionsMenu fabActionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp);
        connectionDetector = new ConnectionDetector();
        employeeToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(employeeToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false);// Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        employeeViewPager = findViewById(R.id.viewpager);
        SetUpPager(employeeViewPager);
        employeeTabLayout = findViewById(R.id.tab1);
        fabActionMenu = findViewById(R.id.fabEmpActivity);
        fabbtn1 = findViewById(R.id.fabEmpActivity1);
        fabbtn2 = findViewById(R.id.fabEmpActivity2);
        fabbtn3 = findViewById(R.id.fabEmpActivity3);
        fabbtn4 = findViewById(R.id.fabEmpActivity4);
        fabbtn5 = findViewById(R.id.fabEmpActivity5);
        employeeTabLayout.setupWithViewPager(employeeViewPager);

        Intent intent = getIntent();
        tempEmployeeValue = intent.getStringExtra(LOGIN_EMPLOYEE);
        tempEmployeeIDValue = intent.getStringExtra(EMPI_USER);
        Log.v("yek", "keyyy" + tempEmployeeValue);

        employeeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        fabActionMenu.setVisibility(View.VISIBLE);
                        fabbtn1.setVisibility(View.VISIBLE);
                        fabbtn2.setVisibility(View.VISIBLE);
                        fabbtn3.setVisibility(View.VISIBLE);
                        fabbtn4.setVisibility(View.VISIBLE);
                        fabbtn5.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        fabActionMenu.setVisibility(View.INVISIBLE);
                        fabbtn1.setVisibility(View.INVISIBLE);
                        fabbtn2.setVisibility(View.INVISIBLE);
                        fabbtn3.setVisibility(View.INVISIBLE);
                        fabbtn4.setVisibility(View.INVISIBLE);
                        fabbtn5.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        fabActionMenu.setVisibility(View.INVISIBLE);
                        fabbtn1.setVisibility(View.INVISIBLE);
                        fabbtn2.setVisibility(View.INVISIBLE);
                        fabbtn3.setVisibility(View.INVISIBLE);
                        fabbtn4.setVisibility(View.INVISIBLE);
                        fabbtn5.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.right_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.right_round_acount_circle) {
            if (connectionDetector.isConnected(this)) {
                Intent demoIntent = new Intent(getApplicationContext(), Profile.class);
                demoIntent.putExtra("visitedEmployeeProfilePage", tempEmployeeIDValue);
                startActivity(demoIntent);
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
                Intent demoIntent = new Intent(getApplicationContext(), Profile.class);
                demoIntent.putExtra("visitedEmployeeProfilePage", tempEmployeeIDValue);
                startActivity(demoIntent);
            }
            return true;
        }
        if(item.getItemId()==R.id.offline_data)
        {
            Intent offIntent = new Intent(getApplicationContext(), OfflinrDataActivity.class);
           // offIntent.putExtra("visitedEmployeeProfilePage", tempEmployeeIDValue);
            startActivity(offIntent);
           // Intent intentnoteuri=new Intent(Intent.ACTION_GET_CONTENT);
            //Uri selectNoteUri=Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/ManishaAgroData/");
            //intentnoteuri.setDataAndType(selectNoteUri,"text");
            //startActivity(Intent.createChooser(intentnoteuri,"Open folder"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public com.getbase.floatingactionbutton.FloatingActionsMenu getFloatingActionMenu() {
        return fabActionMenu;
    }

    public com.getbase.floatingactionbutton.FloatingActionButton getFloatingButton1() {
        return fabbtn1;
    }

    public com.getbase.floatingactionbutton.FloatingActionButton geFloatingButton2() {
        return fabbtn2;
    }

    public com.getbase.floatingactionbutton.FloatingActionButton geFloatingButton3() {
        return fabbtn3;
    }

    public com.getbase.floatingactionbutton.FloatingActionButton geFloatingButton4() {
        return fabbtn4;
    }

    public com.getbase.floatingactionbutton.FloatingActionButton geFloatingButton5() {
        return fabbtn5;
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
        adp.addFragment(new EmployeeStatusFragment(), STATUS);
        adp.addFragment(new DealerFragment(), DEALER);
        adp.addFragment(new PendingFragment(), PENDING);
        viewPager.setAdapter(adp);
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {
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