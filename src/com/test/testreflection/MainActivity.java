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
import com.digicap.android.reflection.listener.CpKeyForHeadUnitListener;
import com.digicap.android.reflection.listener.CpKeyListener;
import com.digicap.android.reflection.listener.ReflectionListener;



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
        mReflection.registerDevice(mCertData, mKeyData, this);


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
        
        
        
        
        // /////////////////////////////////////////////////////////////////////        
        // Get CPKey for HeadUnit
        byte[] signData = "6eQvCi1aQoXtTqPoZsaW18TzUCT2JAD0HYBPTNm9gmwP6Ky6ZmKbbr0oodM7W09ezzMq5jmN2Z6Q98sKHepmDVNhZm31JXUlLebb8m7ZiU5o+lb1b75kft/jY+Zo5mTU9aglBUmBsoIhKD35Ny3dqs5OcizrL0AeCswkKY6by2VyfnzH4dJS/H0769B4prMadmgwvFcUUlciD8Po0aFKtMzhNw/L09xlUlMgVMePPnmfBs0DzWcrdrB96YVWUhlnpbmpcGzpcuE3YZsNKXAkapzhwLZCGNhA4KHT6Tw2MkDI6tBIDyTesJKHMsSPD5Ozc0af0b71QMrhMkHSZLpe5g==".getBytes();
        String macAddress = "0019B8015F7D";
        Reflection.getCpKeyforHeadUnit(getApplication(), macAddress, signData, new CpKeyForHeadUnitListener() {
            
            @Override
            public void resultInfo(String resultCode, String descrition, byte[] cpKeyData, byte[] responseData) {
                // Respnose Body
                // String body = new String(responseData);
            }
        });
        // /////////////////////////////////////////////////////////////////////
    }


    @Override
    public void updateRootingStauts(SecurityLevel level) {
        Log.d(LOG_TAG, "updateRootingStauts(). Rooting is " + level);        
    }


    @Override
    public void updateSecurityLevel(String pkg_name, SecurityLevel level) {
        Log.d(LOG_TAG, "updateSecurityLevel(). Package Name is " + pkg_name + ". Level is " + level);
    }


    @Override
    public void resultAuthentication(String resultCode, String description) {
        
        int code = Integer.valueOf(resultCode);
        switch (code) {
            case 400:   // Success
                Log.d(LOG_TAG, "ResultCode is " + resultCode + ". Description is " + description);
                break;
            
            default:    // Fail
                Log.d(LOG_TAG, "ResultCode is " + resultCode + ". Description is " + description);
                break;
        }
    }
}
