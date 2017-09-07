package com.vigolin.wegomusic.utils.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class PermissionReq {
    private static int sRequestCode=0;

    private Object mObject;
    private String[] permissions;
    private PermissionResult mResult;
    private static SparseArray<PermissionResult> sResultArray=new SparseArray<>();

    private PermissionReq(Object obj){
        this.mObject=obj;
    }

    public static PermissionReq with(Activity activity){
        return new PermissionReq(activity);
    }

    public static PermissionReq with(Fragment fragment){
        return new PermissionReq(fragment);
    }

    public PermissionReq setPermissions(String... permissions){
        this.permissions=permissions;
        return this;
    }

    public PermissionReq setPermissionResult(PermissionResult permissionResult){
        this.mResult=permissionResult;
        return this;
    }

    public void request(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            if(mResult!=null)
                mResult.onGranted();
            return;
        }

        Activity activity=getActivity(mObject);
        if(activity==null)
            throw new IllegalArgumentException(mObject.getClass().getSimpleName()+" is not supported.");

        List<String> deniedPermissions=getDeniedPermissions(activity,permissions);
        if(deniedPermissions.isEmpty()){
            if(mResult!=null)
                mResult.onGranted();
            return;
        }

        int requestCode=genRequestCode();
        String[] deniedPermissionArray=deniedPermissions.toArray(new String[deniedPermissions.size()]);
        requestPermissions(activity,deniedPermissionArray,requestCode);
        sResultArray.put(requestCode,mResult);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void requestPermissions(Object object,String[] permissionArray,int requestCode){
        if(object instanceof Activity)
            ((Activity)object).requestPermissions(permissionArray,requestCode);
        else if(object instanceof Fragment)
            ((Fragment)object).requestPermissions(permissionArray,requestCode);
    }

    private static List<String> getDeniedPermissions(Activity activity, String[] permissionArray){
        List<String> deniedPermissionList=new ArrayList<>();
        for(String permission:permissionArray){
            if(ContextCompat.checkSelfPermission(activity,permission)!= PackageManager.PERMISSION_GRANTED){
                deniedPermissionList.add(permission);
            }
        }
        return deniedPermissionList;
    }

    public static void onRequestPermissionResult(int requestCode,String[] permissions,int[] grantedCodes){
        PermissionResult pr=sResultArray.get(requestCode);
        if(pr==null)
            return;
        sResultArray.remove(requestCode);

        for(int grantedCode:grantedCodes){
            if(grantedCode!=PackageManager.PERMISSION_GRANTED){
                pr.onDenied();
                return;
            }
        }

        pr.onGranted();
    }

    private static Activity getActivity(Object object){
        Activity activity=null;
        if(object!=null){
            if(object instanceof Activity)
                activity=(Activity)object;
            else if(object instanceof Fragment)
                activity=((Fragment)object).getActivity();
        }

        return activity;
    }

    private int genRequestCode(){
        return ++sRequestCode;
    }
}
