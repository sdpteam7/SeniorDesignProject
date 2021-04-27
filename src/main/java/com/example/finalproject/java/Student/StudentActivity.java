package com.example.finalproject.java.Student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.finalproject.R;
import com.example.finalproject.java.Common.AllCoursesFragment;
import com.example.finalproject.java.Common.AllLecturesFragment;
import com.google.android.material.navigation.NavigationView;

public class StudentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    public static String username = null;
    public static String email = null;

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_layout);
        Intent intent = getIntent();
        username = intent.getStringExtra("name");
        email = intent.getStringExtra("mail");

        contextOfApplication = getApplicationContext();

        initializeViews();
        toggleDrawer();
        initializeDefaultFragment(savedInstanceState,0);
    }
    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar_id);
        toolbar.setTitle(R.string.toolbar_title);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_id);
        navigationView = findViewById(R.id.navigationview_id);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void initializeDefaultFragment(Bundle savedInstanceState, int itemIndex){
        if (savedInstanceState == null){
            MenuItem menuItem = navigationView.getMenu().getItem(itemIndex).setChecked(true);
            toolbar.setNavigationIcon(R.drawable.ic_navigation_20_filled);
            onNavigationItemSelected(menuItem);
        }
    }
    private void toggleDrawer() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.nav_home_id:
                getFragmentManager().beginTransaction().replace(R.id.framelayout_id, new HomeFragment())
                        .commit();
                closeDrawer();
                break;
            case R.id.nav_courses_id:
                getFragmentManager().beginTransaction().replace(R.id.framelayout_id, new AllCoursesFragment())
                        .commit();
                closeDrawer();
                break;
            case R.id.nav_lectures_id:
                getFragmentManager().beginTransaction().replace(R.id.framelayout_id, new AllLecturesFragment())
                        .commit();
                closeDrawer();
                break;
            case R.id.nav_assigment_id:
                startActivity(new Intent(StudentActivity.this, AssignmentsActivity.class));
                closeDrawer();
                break;
        }
        return true;
    }
    private void closeDrawer(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    private void deSelectCheckedState(){
        int noOfItems = navigationView.getMenu().size();
        for (int i=0; i<noOfItems;i++){
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }
}
