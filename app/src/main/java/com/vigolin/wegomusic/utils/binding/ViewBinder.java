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
        Field[] fields=obj.getClass().getDeclaredFields();
        if(fields!=null && fields.length>0){
            for(Field field:fields){
                try{
                    field.setAccessible(true);
                    if(field.get(obj)!=null){
                        continue;
                    }
                    Bind bind=field.getAnnotation(Bind.class);
                    if(bind!=null){
                        int resId=bind.value();
                        field.set(obj,view.findViewById(resId));
                    }
                }catch(IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
