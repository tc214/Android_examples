###  ��΢��������  

###  �ص�  
*  �ܹ��ĸ�ҳ��
*  �����л�ҳ��  
*  ����ײ���ť�л�����  
*  �����л���ƽ����ҳ����ת������ʾ�м��ҳ��   
*  �������л������ص���ʾ    



###  ViewPager����   
ViewPager��Android�����߱Ƚϳ��õ�һ���ؼ��ˣ���������������ҳ�����һ��ߴ��ҵ���ҳ��  
��APP�еĺܶೡ�����õõ��������һ�ΰ�װAPPʱ���û�����ҳ��ͼƬ���ʱ���ҷ�ҳ�����Banner  
ҳ�����������鷭ҳ�ȵȶ����õ�ViewPager��  

 
###  ʹ��    
####  һ���Զ���ViewPager  
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

####  �����ڲ����ļ��������ؼ�  
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
	

####  �����ڴ���������������      
�Զ���Adapter
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
������˵����  

*  int getCount()��������ʾҳ������

*  boolean isViewFromObject(View view, Object object)���жϳ�ʼ�����ص�Object�ǲ���һ��View����

*  Object instantiateItem(ViewGroup container, int position)����ʼ����ʾ����Ŀ����

*  void destroyItem(ViewGroup container, int position, Object object)��������Ŀ����  


 
		
```java  
ViewPager mViewPager = (NoScrollViewPager) findViewById(R.id.viewPager);  
```  

  
 

```java    
 // ����Ԥ����ҳ�����
 mViewPager.setOffscreenPageLimit(mFragments.size() - 1);   
 
 // ������������
mViewPager.setAdapter(myPageAdapter);    

 // ����ҳ���л�����
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
  
// �ֶ��л�ҳ��  
private void setSelect(int i) {
	FragmentTransaction transaction = mFragmentManager.beginTransaction();
	hideFragments(transaction);
	// smoothScroll True to smoothly scroll to the new item, false to transition immediately
	mViewPager.setCurrentItem(i, false);
}
```  
		


 