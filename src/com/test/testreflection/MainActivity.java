package com.test.testreflection;

import com.digicap.android.reflection.Reflection;
import com.digicap.android.reflection.ReflectionListner;
import com.digicap.android.reflection.Reflection.SecurityLevel;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity
{
    private static final String LOG_TAG = "SecurityLevel";
    private SecurityLevel mLevel;
    private Reflection mReflection;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        // 1. Get Reflection Instance
        mReflection = Reflection.getInstance();
        
        // 2. Register Listener
        mReflection.RegisterDevice(new ReflectionListner() {
            
            @Override
            public void UpdateSecurityLevel(SecurityLevel level)
            {
                Log.d(LOG_TAG, "UpdateSecurityLevel() is " + mLevel + ".");
            }
        });
        
        // 3. Get SecurityLevel
        mLevel = mReflection.GetSecurityLevel();
        Log.d(LOG_TAG, "SecurityLevel is " + mLevel);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) { return true; }
        return super.onOptionsItemSelected(item);
    }
}
