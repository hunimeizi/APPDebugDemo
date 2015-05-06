package appdebugdemo.lyb.com.appdebugdemo.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import appdebugdemo.lyb.com.appdebugdemo.R;
import appdebugdemo.lyb.com.appdebugdemo.activity.DebugCancelDialogActivity;
import appdebugdemo.lyb.com.appdebugdemo.activity.DebugTabActivity;

/**
 * Created by MyPC on 2015/5/6.
 */
public class MyApplication extends Application implements View.OnClickListener{
    private static MyApplication instance;
    private WindowManager wManager;
    public Context currContext;
    private ImageView bugImage;
    public Handler messageHandler;
    private boolean flag = true;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance()
    {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }
    public void showBug(final Context context)
    {
        MyApplication.getInstance().currContext = context;
        wManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        wmParams.x = 0;
        wmParams.y = 0;

        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        if(bugImage != null) { //判断bugImage是否存在，如果存在则移除，必须加在 new ImageView(context) 前面
            wManager.removeView(bugImage);
        }

        bugImage = new ImageView(context);
        bugImage.setImageResource(R.drawable.bug);
        wManager.addView(bugImage, wmParams);
        bugImage.setOnClickListener(this);

        bugImage.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                DebugCancelDialogActivity.parentHandler = messageHandler;
                Intent intent = new Intent(MyApplication.getInstance().currContext,DebugCancelDialogActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                flag = false;
                return false;
            }
        });

        messageHandler = new Handler() {

            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    wManager.removeView(bugImage);
                    bugImage = null; // 必须要把bugImage清空，否则再次进入debug模式会与102行冲突
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        if(flag != false) {
            Intent intent = new Intent(MyApplication.getInstance().currContext,DebugTabActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        flag = true;
    }
}
