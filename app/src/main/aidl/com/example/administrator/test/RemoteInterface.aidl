// RemoteInterface.aidl
package com.example.administrator.test;

// Declare any non-default types here with import statements
import com.example.administrator.test.bean.aidl.ExData;
import com.example.administrator.test.RemoteCallBack;

interface RemoteInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int add(in int a,in int b,inout ExData c,in RemoteCallBack callback);


    void exit();
}
