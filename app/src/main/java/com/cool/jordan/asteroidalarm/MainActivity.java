package com.cool.jordan.asteroidalarm;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView myView = new MyView(this);
        setContentView(myView);
        Controller controller = new Controller();
        controller.start();
    }

    public class MyView extends View {
        Paint paint = null;
        public MyView(Context context) {
            super(context);
            paint = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int x = getWidth();
            int y = getHeight();
            int radius;
            radius = 75;
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLACK);
            canvas.drawPaint(paint);
            // Use Color.parseColor to define HTML colors
            paint.setColor(Color.parseColor("#FF0000"));
            canvas.drawCircle(x / 5, y / 5, radius, paint);
            radius = 25;
            canvas.drawCircle(x - (3 * radius ), y - (2 * radius), radius, paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            canvas.drawLine(x - (3 * radius ), y - (2 * radius), x / 5, y / 5, paint);
            //canvas.draw
        }
    }



}
