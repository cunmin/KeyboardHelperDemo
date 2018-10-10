package com.littleyellow.keyboardhelperdemo.util;

import android.os.Bundle;
import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * Created by 小黄 on 2018/7/26.
 */

public class TabFragmentHelper {

    private ViewPager mMainViewPager;
    private TabLayout mTabLayout;
    private ArrayList<TabInfo> tabs;
    private FragmentManager fm;
    private int defPosition;
    private int pageLimit;
    private boolean isPost;

    private TabFragmentHelper(Builder builder) {
        mMainViewPager = builder.mMainViewPager;
        mTabLayout = builder.mTabLayout;
        tabs = builder.tabs;
        fm = builder.fm;
        defPosition = builder.defPosition;
        pageLimit = builder.pageLimit;
        isPost = builder.isPost;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public TabFragmentHelper setupViewPager() {
//        FragmentTransaction ft = fm.beginTransaction();
//        List<Fragment> fragments = fm.getFragments();
//        if(null!=fragments){
//            for(Fragment f:fragments){
//                ft.remove(f);
//            }
//            ft.commitAllowingStateLoss();
//            fm.executePendingTransactions();
//            fragments.clear();
//        }
        return setupViewPager(buildPagerAdapter());
    }

    public TabFragmentHelper setupViewPager(final PagerAdapter adapter) {
        if(isPost) {
            mMainViewPager.post(new Runnable() {
                @Override
                public void run() {
                    mMainViewPager.setAdapter(adapter);
                    if (0 == pageLimit) {
                        mMainViewPager.setOffscreenPageLimit(mMainViewPager.getAdapter().getCount());
                    } else {
                        mMainViewPager.setOffscreenPageLimit(pageLimit);
                    }
                    mMainViewPager.setCurrentItem(defPosition);
                }
            });
        }else{
            mMainViewPager.setAdapter(adapter);
            if (0 == pageLimit) {
                mMainViewPager.setOffscreenPageLimit(mMainViewPager.getAdapter().getCount());
            } else {
                mMainViewPager.setOffscreenPageLimit(pageLimit);
            }
            mMainViewPager.setCurrentItem(defPosition);
        }
        mTabLayout.setupWithViewPager(mMainViewPager);
        return this;
    }

    public FragmentStatePagerAdapter getAdapter(){
        FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) mMainViewPager.getAdapter();
        return adapter;
    }

    public <T extends Fragment> T getCurrentFragment(){
        try {
            return (T) fm.getFragments().get(mMainViewPager.getCurrentItem());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T extends Fragment> T getFragment(int position){
        try {
            return (T) fm.getFragments().get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private FragmentStatePagerAdapter buildPagerAdapter() {
        return new TabPagerAdapter(fm,tabs);
    }

    private class TabPagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<TabInfo> tabInfos = new ArrayList<>();

        public TabPagerAdapter(FragmentManager fm, ArrayList<TabInfo> tabInfos) {
            super(fm);
            this.tabInfos = tabInfos;
        }

        @Override
        public Fragment getItem(int position) {
            try {
                Class<?> clazz = tabInfos.get(position).fragmentClass;
                Fragment fragment = (Fragment) clazz.newInstance();

                if (tabInfos.get(position).arguments != null) {
                    fragment.setArguments(tabInfos.get(position).arguments);
                }

                return fragment;
            } catch (Exception e) {
                throw new RuntimeException("Cannot construct fragment", e);
            }
        }

        @Override
        public int getCount() {
            return tabInfos.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabInfos.get(position).title;
        }
    }

    private final static class TabInfo implements Parcelable {
        private final String title;
        private final Bundle arguments;
        private final Class<?> fragmentClass;

        public TabInfo(@NonNull String title, @NonNull Class<? extends Fragment> clazz, @Nullable Bundle arguments) {
            this.title = title;
            this.arguments = arguments;
            this.fragmentClass = clazz;
        }

        private TabInfo(Parcel parcel) throws ParcelFormatException {
            try {
                this.title = parcel.readString();
                this.arguments = parcel.readBundle();
                this.fragmentClass = getClass().getClassLoader().loadClass(parcel.readString());
            } catch (Exception e) {
                throw new ParcelFormatException();
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(title);
            parcel.writeString(fragmentClass.getCanonicalName());
            parcel.writeBundle(arguments);
        }

        public static final Creator<TabInfo> CREATOR = new Creator<TabInfo>() {
            @Override
            public TabInfo createFromParcel(Parcel parcel) {
                return new TabInfo(parcel);
            }

            @Override
            public TabInfo[] newArray(int i) {
                return new TabInfo[i];
            }
        };
    }


    public static final class Builder {
        private ViewPager mMainViewPager;
        private TabLayout mTabLayout;
        private ArrayList<TabInfo> tabs = new ArrayList<>();
        private FragmentManager fm;
        private int defPosition;
        private int pageLimit;
        private boolean isPost;

        private Builder() {
        }

        public Builder mMainViewPager(ViewPager mMainViewPager) {
            this.mMainViewPager = mMainViewPager;
            return this;
        }

        public Builder mTabLayout(TabLayout mTabLayout) {
            this.mTabLayout = mTabLayout;
            return this;
        }

        public Builder addTab(TabInfo tab) {
            tabs.add(tab);
            return this;
        }

        public Builder addTab(String title, Class<? extends Fragment> fragmentClass, Bundle arguments) {
            tabs.add(new TabInfo(title,fragmentClass,arguments));
            return this;
        }

        public Builder fm(FragmentManager fm) {
            this.fm = fm;
            return this;
        }

        public Builder defPosition(int position) {
            this.defPosition = position;
            return this;
        }

        public Builder pageLimit(int pageLimit){
            this.pageLimit = pageLimit;
            return this;
        }

        public Builder isPost(boolean isPost){
            this.isPost = isPost;
            return this;
        }




        public TabFragmentHelper build() {
            return new TabFragmentHelper(this);
        }
    }
}
