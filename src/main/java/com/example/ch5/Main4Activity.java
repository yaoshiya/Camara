package com.example.ch5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class Main4Activity extends Activity implements android.view.GestureDetector.OnGestureListener {
    private int[] images = {R.drawable.image1, R.drawable.image2,
            R.drawable.image3, R.drawable.image4, R.drawable.image5};

    private GestureDetector gestureDetector = null;
    private ViewFlipper viewFlipper = null;
    private static final int FLING_MIN_DISTANCE = 100;
    private static final int FLING_MIN_VELOCITY = 200;
    private Activity mActivity = null;
    private Animation rInAnim, rOutAnim, lInAnim, lOutAnim;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        mActivity = this;
        viewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipper01);
        gestureDetector = new GestureDetector(this, this);    // 声明检测手势事件
        //AnimationUtils类用于定义常用的处理动画功能，loadAnimation(Context context, int id)：从资源文件中加载一个Animation对象。
        rInAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_right_in);    // 向右滑动左侧进入的渐变效果（alpha  0.1 -> 1.0）
        rOutAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_right_out); // 向右滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）
        lInAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_left_in);        // 向左滑动左侧进入的渐变效果（alpha 0.1  -> 1.0）
        lOutAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_left_out);    // 向左滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）
        for (int i = 0; i < images.length; i++) {            // 添加图片源
            ImageView iv = new ImageView(this);
            iv.setImageResource(images[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(iv, i, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        }

        viewFlipper.setAutoStart(true);            // 设置自动播放功能（点击事件前，自动播放）
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewFlipper.stopFlipping();                // 点击事件后，停止自动播放
        viewFlipper.setAutoStart(false);
        //调用GestureDetector的onTouchEvent()方法，将捕捉到的MotionEvent交给GestureDetector来分析是否有合适的callback函数来处理用户的手势

        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        // 从左向右滑动（左进右出）,X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
        if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {

            viewFlipper.setInAnimation(rInAnim);
            viewFlipper.setOutAnimation(rOutAnim);
            viewFlipper.showPrevious();
            setTitle("相片编号：" + viewFlipper.getDisplayedChild());
            return true;
        } else if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {         // 从右向左滑动（右进左出）
            viewFlipper.setInAnimation(lInAnim);
            viewFlipper.setOutAnimation(lOutAnim);
            viewFlipper.showNext();
            setTitle("相片编号：" + viewFlipper.getDisplayedChild());
            return true;
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
}