###  仿微信主界面  

###  特点  
*  总共四个页面
*  滑动切换页面  
*  点击底部按钮切换界面  
*  所有切换都平滑，页面跳转不会显示中间的页面   
*  横竖屏切换不会重叠显示    



###  ViewPager介绍   
ViewPager是Android开发者比较常用的一个控件了，由于它允许数据页从左到右或者从右到左翻页，  
在APP中的很多场景都用得到，比如第一次安装APP时的用户引导页、图片浏览时左右翻页、广告Banner  
页、聊天界面表情翻页等等都会用到ViewPager。  

 
###  使用    
####  一、自定义ViewPager  
```java  
package org.tc.mainstruct;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean isScroll = true;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {

        if (!isScroll) {
            return false;
        }
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (!isScroll) {
            return false;
        }
        return super.onTouchEvent(arg0);
    }

    public void setScroll(boolean isScroll) {
        this.isScroll = isScroll;
    }

}
```  

####  二、在布局文件中声明控件  
```java   
<org.tc.mainstruct.NoScrollViewPager
            android:id="@+id/viewPager"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:layout_above="@+id/layout_bm"
            android:flipInterval="30"
            android:persistentDrawingCache="animation"
    />  
```  
	

####  三、在代码中设置适配器      
自定义Adapter
```java  
PagerAdapter myPageAdapter = new PagerAdapter() {
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
```    
适配器说明：  

*  int getCount()：返回显示页面总数

*  boolean isViewFromObject(View view, Object object)：判断初始化返回的Object是不是一个View对象

*  Object instantiateItem(ViewGroup container, int position)：初始化显示的条目对象

*  void destroyItem(ViewGroup container, int position, Object object)：销毁条目对象  


 
		
```java  
ViewPager mViewPager = (NoScrollViewPager) findViewById(R.id.viewPager);  
```  

  
 

```java    
 // 设置预加载页面个数
 mViewPager.setOffscreenPageLimit(mFragments.size() - 1);   
 
 // 设置适配器：
mViewPager.setAdapter(myPageAdapter);    

 // 设置页面切换监听
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
  
// 手动切换页面  
private void setSelect(int i) {
	FragmentTransaction transaction = mFragmentManager.beginTransaction();
	hideFragments(transaction);
	// smoothScroll True to smoothly scroll to the new item, false to transition immediately
	mViewPager.setCurrentItem(i, false);
}
```  
		


 