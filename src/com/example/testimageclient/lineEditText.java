package com.example.testimageclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

public class lineEditText extends EditText {  
    
  private Paint paint;  
    
  public lineEditText(Context context, AttributeSet attrs) {  
      super(context, attrs);  
      //…Ë÷√ª≠± µƒ Ù–‘  
      paint = new Paint();  
      paint.setStyle(Paint.Style.STROKE);  
      paint.setColor(Color.WHITE);
      }
  @Override  
  protected void onDraw(Canvas canvas) {  
      super.onDraw(canvas);  
      canvas.drawLine(0, this.getHeight()-2, this.getWidth()-2, this.getHeight()-2, paint);  
  }  

}  
