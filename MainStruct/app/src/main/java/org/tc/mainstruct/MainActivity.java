package org.tc.mainstruct;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private FirstFragment mFirstFragment; // 1st Fragment
    private SecondFragment mSecondFragment; // 2nd Fragment
    private ThirdFragment mThirdFragment;// 4th fragment
    private FourthFragment mFourthFragment; // 3rd Fragment
    private List<Fragment> mFragments;
    private ImageView[] imagebuttons;
    private TextView[] textviews;
    private PagerAdapter myPageAdapter;
    private NoScrollViewPager mViewPager;
    public FragmentManager mFragmentManager;
    private int currentTabIndex = 0, index = 0;
    private View currentButton;
    public static final String TAG = "FragmentManageActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (null != savedInstanceState) {
            currentTabIndex = savedInstanceState.getInt("neo");
        }
        findView();
        initData();
        initTabView();
    }

    private void findView() {
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewPager);
    }

    private void initData() {
        mFragmentManager = getSupportFragmentManager();
        mFragments = new ArrayList<Fragment>();
        mFirstFragment = new FirstFragment();
        mSecondFragment = new SecondFragment();
        mThirdFragment = new ThirdFragment();
        mFourthFragment = new FourthFragment();
        mFragments.add(mFirstFragment);
        mFragments.add(mSecondFragment);
        mFragments.add(mThirdFragment);
        mFragments.add(mFourthFragment);

        textviews = new TextView[mFragments.size()];
        textviews[0] = (TextView) findViewById(R.id.tv_weixin);
        textviews[1] = (TextView) findViewById(R.id.tv_contact_list);
        textviews[2] = (TextView) findViewById(R.id.tv_find);
        textviews[3] = (TextView) findViewById(R.id.tv_profile);

        imagebuttons = new ImageView[mFragments.size()];
    }

    private void initTabView() {
        imagebuttons[0] = (ImageView) findViewById(R.id.ib_weixin);
        imagebuttons[1] = (ImageView) findViewById(R.id.ib_contact_list);
        imagebuttons[2] = (ImageView) findViewById(R.id.ib_find);
        imagebuttons[3] = (ImageView) findViewById(R.id.ib_profile);

        imagebuttons[0].setSelected(true);
        textviews[0].setTextColor(getResources().getColor(R.color.blue));

        myPageAdapter = new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mFragments.get(position).getView());
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Fragment fragment = mFragments.get(position);
                if (!fragment.isAdded()) {
                    FragmentTransaction ft = mFragmentManager.beginTransaction();
                    ft.add(fragment, fragment.getClass().getSimpleName());
                    ft.show(fragment);
                    ft.commit();
                    mFragmentManager.executePendingTransactions();
                }

                if (fragment.getView().getParent() == null) {
                    container.addView(fragment.getView());
                }
                return fragment.getView();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mViewPager.setAdapter(myPageAdapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                int currentItem = mViewPager.getCurrentItem();
                setBottomBtn(currentItem);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    private void setBottomBtn(int position) {
        // set current tab to selected
        imagebuttons[currentTabIndex].setSelected(false);
        imagebuttons[position].setSelected(true);
        textviews[currentTabIndex].setTextColor(getResources().getColor(R.color.grey));
        textviews[position].setTextColor(getResources().getColor(R.color.blue));
        currentTabIndex = position;
    }

    /*
       callback function whne bottom button is clicked
     */
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.re_weixin:
                index = 0;
                break;

            case R.id.re_contact_list:
                index = 1;
                break;

            case R.id.re_find:
                index = 2;
                break;

            case R.id.re_profile:
                index = 3;
                break;

            default:
                break;
        }
        setSelect(index);
    }

    private void setSelect(int i) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragments(transaction);
        // smoothScroll True to smoothly scroll to the new item, false to transition immediately
        mViewPager.setCurrentItem(i, false);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mFirstFragment != null) {
            transaction.hide(mFirstFragment);
        }
        if (mSecondFragment != null) {
            transaction.hide(mSecondFragment);
        }

        if (mThirdFragment != null) {
            transaction.hide(mThirdFragment);
        }

        if (mFourthFragment != null) {
            transaction.hide(mFourthFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
