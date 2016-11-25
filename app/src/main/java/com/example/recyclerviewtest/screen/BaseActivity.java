package com.example.recyclerviewtest.screen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.recyclerviewtest.R;

/**
 * Created by Nazariy Moshenskyi on 24.11.2016.
 */

public class BaseActivity extends AppCompatActivity {

    protected DrawerLayout mDrawerLayout;

    private Toolbar mToolbar;

    private NavigationView mNavigation;

    protected FrameLayout frameLayout;

    int drawerWidth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigation = (NavigationView) findViewById(R.id.left_drawer);

        drawerWidth = getDrawerWidth();
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mNavigation.getLayoutParams();
        params.width = drawerWidth;
        mNavigation.setLayoutParams(params);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);

                mDrawerLayout.closeDrawers();

                Fragment fragment = null;
                switch (item.getItemId()) {

                    case R.id.nav_rooms:
                        //TODO: Replace Fragment by CUSTOM Frament for activity
                        fragment = new Fragment();
                        break;
                    case R.id.nav_messages:
                        fragment = new Fragment();
                        break;
                    case R.id.nav_settings:
                        fragment = new Fragment();
                        break;
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                return true;
            }
        });

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                                            this,
                                            mDrawerLayout,
                                            mToolbar,
                                            R.string.drawer_open,
                                            R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

        };

        mDrawerLayout.addDrawerListener(drawerToggle);

        //for hamburger icon
        drawerToggle.syncState();
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private int getDrawerWidth() {

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        float density = getResources().getDisplayMetrics().density;
        float dpWidth = metrics.widthPixels / density;

        /*
        *   int offset is an ideal free space in density pixels(dp) between right border and drawer
        * */
        int offset = 56;

        return (int) dpWidth - offset;
    }

}
