package com.test.testreflection;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.digicap.android.reflection.Reflection;
import com.digicap.android.reflection.Reflection.SecurityLevel;
import com.digicap.android.reflection.ReflectionListener;



public class MainActivity extends Activity
{
    private static final String LOG_TAG = "ReflectionJar";

    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        ///////////////////////////////////////////////////////////////////////
        /// Reflection Library Sample Code
        
        
        // enum SecurityLevel {blank, red, orange, yellow, black, green};
        
        
        // Test Cp Package Name
        String test_pkg_name_1 = "com.digicap.android.test1";
        
        
        // 1. Get Reflection Instance
        Reflection mReflection = Reflection.getInstance(this.getApplication());
        
        
        // 2. Register Listener
        mReflection.registerDevice(new ReflectionListener() {

            @Override
            public void updateSecurityLevel(String pkg_name, SecurityLevel level)
            {
                Log.d(LOG_TAG, "RegisterDevice(). Package Name is " + pkg_name + ". Level is " + level); 
            }
        });

        // 3. Add PackageName
        mReflection.addAppPackage(test_pkg_name_1, new ReflectionListener() {

            @Override
            public void updateSecurityLevel(String pkg_name, SecurityLevel level)
            {
                Log.d(LOG_TAG, "addAppPackage(). Package is " + pkg_name + ". SecurityLevel is " + level);                 
            }
        });
        
        
        // 4. Get SecurityLevel of Companion App 
        SecurityLevel companionLevel = mReflection.getSecurityLevel();
        Log.d(LOG_TAG, "Companion SecurityLevel is " + companionLevel);
        
        
        // 5. Get SecurityLevel of CP App
        SecurityLevel cpLevel = mReflection.getSecurityLevel(test_pkg_name_1);
        Log.d(LOG_TAG, "CP SecurityLevel is " + cpLevel);
        
        ///////////////////////////////////////////////////////////////////////
        
        
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
