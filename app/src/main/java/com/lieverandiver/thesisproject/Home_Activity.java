package com.lieverandiver.thesisproject;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.lieverandiver.thesisproject.fragment.Home_Class_Slidebar_Fragment;
import com.lieverandiver.thesisproject.fragment.Home_Logs_Slidebar_Fragment;
import com.lieverandiver.thesisproject.fragment.Home_Schedule_Slidebar_Fragment;
import com.lieverandiver.thesisproject.fragment.Home_Student_Slidebar_Fragment;
import com.lieverandiver.thesisproject.fragment.SlidebarClazz_Fragment_View;
import com.lieverandiver.thesisproject.fragment.SlidebarSchedule_Fragment_View;
import com.lieverandiver.thesisproject.fragment.SlidebarStudent_Fragment_View;
import com.lieverandiver.thesisproject.helper.TeacherHelper;

import java.util.ArrayList;
import java.util.List;



public class Home_Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        Bundle bundle = getIntent().getExtras();
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Home_Schedule_Slidebar_Fragment(), "Shedule");
        adapter.addFragment(new Home_Class_Slidebar_Fragment(), "Class");
        adapter.addFragment(new Home_Student_Slidebar_Fragment(), "Student");
        adapter.addFragment(new Home_Logs_Slidebar_Fragment(), "|||");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}