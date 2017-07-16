package com.vigolin.wegomusic.utils.binding;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/7/16.
 */

public class ViewBinder {
    public static void bind(Activity activity){
        bind(activity,activity.getWindow().getDecorView());
    }

    public static void bind(Object obj,View view){
        Class<?> classObj=obj.getClass();
        Field[] fields=classObj.getDeclaredFields();
        if(fields!=null && fields.length>0){
            try{
                for(Field field:fields){
                    if(field.get(obj)!=null)
                        continue;

                    field.setAccessible(true);
                    Bind bind=field.getAnnotation(Bind.class);
                    if(bind!=null){
                        int resId=bind.value();
                        field.set(obj,view.findViewById(resId));
                    }
                }
            }catch(IllegalAccessException e){
                e.printStackTrace();
            }
        }
    }
}
