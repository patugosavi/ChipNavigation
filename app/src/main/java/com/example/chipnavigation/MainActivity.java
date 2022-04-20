package com.example.chipnavigation;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    FragmentManager fm;
    String fName;
    Fragment fragment;
    ActionBarDrawerToggle toggle;

    Context mContext;

    boolean doubleBackToExitPressedOnce = false;


    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=getApplicationContext();
        ButterKnife.bind(this);
        fName = "Dashboard";

        //set default fragment
        replaceFragment(new HomeFragment());
//        Intent i1=new Intent(mContext, InvoiceActivity.class);
//        startActivity(i1);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));

//        iv_logo.setVisibility(View.GONE);
        toolbar.setTitle(null);
//        toolbar.setNavigationIcon(null);
//        toolbar.setVisibility(View.GONE);

//        toolbar.setNavigationIcon(null);
        //hide drawer
//        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //unhide drawer
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        fm = getSupportFragmentManager();

        //bottom navigation
//        bottomNavigationView = findViewById(R.id.navigation);
//        bottomNavigationView.setVisibility(View.GONE);


        chipNavigationBar = findViewById(R.id.bottom_nav_bar);
        chipNavigationBar.setItemSelected(R.id.bottom_home,
                true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout,
                        new HomeFragment()).commit();


        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        Fragment fragment = null;
                        switch (i){
                            case R.id.bottom_home:
                                fragment = new HomeFragment();
                                break;
                            case R.id.bottom_history:
                                fragment = new HomeFragment();
                                break;
                            case R.id.bottom_account:
                                fragment = new HomeFragment();
                                break;
                            case R.id.bottom_logout:
                                fragment = new HomeFragment();
                                break;
                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout,
                                        fragment).commit();
                    }
                });




    }

    public void onClick(View v) {

        fm = getSupportFragmentManager();
        switch (v.getId()){

            case R.id.rl_home:
                replaceFragment(new HomeFragment());
                drawer.closeDrawer(GravityCompat.START);
                break;

        }
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        }else if (getSupportFragmentManager().findFragmentById(R.id.frame_layout)!=getSupportFragmentManager().findFragmentByTag("Dashboard")) {

            fm.beginTransaction().replace(R.id.frame_layout,new HomeFragment()).addToBackStack(null).commit();
            doubleBackPress();


//            username.equalsIgnoreCase("admin")
//            if (uRole==1){
//                if(!fName.getClass().getSimpleName().equalsIgnoreCase("DoctorDashboard")){
//                    fm.beginTransaction().replace(R.id.frame_layout,new DoctorDashboard()).addToBackStack(null).commit();
//                    doubleBackPress();
//                }
////username.equalsIgnoreCase("staff")
//            }else if(uRole==0) {
//                if (!fName.getClass().getSimpleName().equalsIgnoreCase("PatDashboard")) {
//                    fm.beginTransaction().replace(R.id.frame_layout, new PatDashboard()).addToBackStack(null).commit();
//                    doubleBackPress();
//                }
//            }

        } else {
            super.onBackPressed();

        }
    }

    private void doubleBackPress() {
        if (!doubleBackToExitPressedOnce){
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(MainActivity.this, "Please click back again to exit !!", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            },500);
        }else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

/*    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_logout, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        TextView btn_no = dialogView.findViewById(R.id.btn_no);
        TextView btn_yes = dialogView.findViewById(R.id.btn_yes);

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this, MainActivity.class));
//                finish();
            }
        });
    }*/

    public void replaceFragment(Fragment fragment) {
        this.fragment=fragment;
        fName = fragment.getClass().getSimpleName();
        fm = getSupportFragmentManager();
        FragmentTransaction fTransaction = fm.beginTransaction();
        fTransaction.replace(R.id.frame_layout, fragment);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    //another fragment to fragment
//     ((MainActivity) mContext).replaceFragment(new BookListFragment());


}