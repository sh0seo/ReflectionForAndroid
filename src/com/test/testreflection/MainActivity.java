package com.test.testreflection;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.digicap.android.reflection.Reflection;
import com.digicap.android.reflection.Reflection.SecurityLevel;
import com.digicap.android.reflection.listener.CpKeyListener;
import com.digicap.android.reflection.ReflectionListener;



public class MainActivity extends Activity implements ReflectionListener {
    
    private static final String LOG_TAG = "ReflectionJar";

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        ///////////////////////////////////////////////////////////////////////
        /// Reflection Library Sample Code
        
        
        // enum SecurityLevel {blank, red, orange, yellow, black, green};
        
        
        // Test Cp Package Name
        String test_pkg_name_1 = "com.digicap.android.test30";
        
        
        // Read Init Certificate, Init Key
        byte[] mCertData = null;
        byte[] mKeyData = null;
        try {
            AssetManager assetMgr = getAssets();
            InputStream is = assetMgr.open("init.pem");
            mCertData = IOUtils.toByteArray(is);
            
            is = assetMgr.open("init.key");
            mKeyData = IOUtils.toByteArray(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        // 1. Get Reflection Instance
        Reflection mReflection = Reflection.getInstance(this.getApplication());
        
        
        // 2. Register Listener
        mReflection.registerDevice(this, mCertData, mKeyData);


        // 3. Add PackageName
        mReflection.addAppPackage(test_pkg_name_1, this);
        
        
        // 4. Get SecurityLevel of Companion App 
        SecurityLevel companionLevel = mReflection.getSecurityLevel();
        Log.d(LOG_TAG, "Companion SecurityLevel is " + companionLevel);
        
        
        // 5. Get SecurityLevel of CP App
        SecurityLevel cpLevel = mReflection.getSecurityLevel(test_pkg_name_1);
        Log.d(LOG_TAG, "CP SecurityLevel is " + cpLevel);
        
        
        // 6. Get CP Key API
        mReflection.getCPKey(new CpKeyListener() {
            
            @Override
            public void resultInfo(int resultCode, String descrition, byte[] cpKeyData) {
                if (cpKeyData != null) {
                    Log.d(LOG_TAG, "ResultCode is " + resultCode + ". Description is " + descrition + ". Cp Key is " + new String(cpKeyData));
                } else {
                    Log.d(LOG_TAG, "ResultCode is " + resultCode + ". Description is " + descrition + ". Cp Key is null");
                }
            }
        });
        ///////////////////////////////////////////////////////////////////////
    }


    @Override
    public void updateRootingStauts(SecurityLevel level) {
        Log.d(LOG_TAG, "updateRootingStauts(). Rooting is " + level);        
    }


    @Override
    public void updateSecurityLevel(String pkg_name, SecurityLevel level) {
        Log.d(LOG_TAG, "updateSecurityLevel(). Package Name is " + pkg_name + ". Level is " + level);
    }
}
