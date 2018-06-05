###  自定义View  

###  继承View，重写onDraw    
```java  
CircleView.java:  

package custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {
    private int mColor = Color.RED;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(mColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2;
        canvas.drawCircle(width/2, height/2, radius, mPaint);
    }
}

activity_main.xml:  

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
          />

    <custom.CircleView
        android:id="@+id/circleView"
        android:layout_width="wrap_content"
        android:layout_height="100dp"
        android:background="#000000"
        android:layout_margin="20dp"
        android:padding="20dp"
        />
</LinearLayout>  
```  
我们在布局中"Hello World"的旁边画了一个圆，它的直径为宽和高中的最小值作为直径，红色、实心。  
我们发现，如果去掉上述activity_main.xml中的 android:padding="20dp"或将android:layout_width="wrap_content"  
改为android:layout_width="match_parent",结果画出来的圆没有变化，这说明：  
直接继承自View和ViewGroup的控件，padding默认无法生效，wrap_content相当于match_parent。  

###   
