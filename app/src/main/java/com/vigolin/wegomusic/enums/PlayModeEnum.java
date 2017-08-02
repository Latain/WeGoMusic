package com.vigolin.wegomusic.enums;

/**
 * Created by Administrator on 2017/7/28.
 */

public enum PlayModeEnum {
    LOOP(0),SHUFFLE(1),SINGLE(2);

    private int value;

    private PlayModeEnum(int value){
        this.value=value;
    }

    public static PlayModeEnum valueOf(int val){
        switch(val){
            case 1:
                return SHUFFLE;
            case 2:
                return SINGLE;
            case 0:
            default:
                return LOOP;
        }
    }

    public int getValue(){
        return value;
    }
}
