package appdebugdemo.lyb.com.appdebugdemo.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import appdebugdemo.lyb.com.appdebugdemo.R;
import appdebugdemo.lyb.com.appdebugdemo.app.MyApplication;
import appdebugdemo.lyb.com.appdebugdemo.model.BeeQuery;


public class AppDebugDemoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_debug_demo);
        if(BeeQuery.environment() == BeeQuery.ENVIROMENT_DEVELOPMENT)
        {
            MyApplication.getInstance().showBug(this);
        }
    }
}
