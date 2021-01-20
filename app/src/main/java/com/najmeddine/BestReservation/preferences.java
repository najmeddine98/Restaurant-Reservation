package com.najmeddine.BestReservation;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class preferences {
    private static final String DATA_LOGIN = "status_login",
        DATA_AS="as",
        DATA_NAME="full name",
        DATA_PHONE="phone number",
        DATA_EMAIL="email",
        DATA_PASS="pass",
        DATA_RESNB="0";


    private static SharedPreferences getSharedReferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setDataAs (Context context,String data){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_AS,data);
        editor.apply();
    }

    public static void setDataResnb (Context context,String data){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_RESNB,data);
        editor.apply();
    }


    public static void setDataPass (Context context,String data){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_PASS,data);
        editor.apply();
    }


    public static String getDataAs(Context context){
        return getSharedReferences(context).getString(DATA_AS,"");
    }

    public static String getDataResnb(Context context){
        return getSharedReferences(context).getString(DATA_RESNB,"");
    }


    public static void setDataName (Context context,String data){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_NAME,data);
        editor.apply();
    }

    public static String getDataName(Context context){
        return getSharedReferences(context).getString(DATA_NAME,"");
    }

    public static String getDataPass(Context context){
        return getSharedReferences(context).getString(DATA_PASS,"");
    }

    public static void setDataEmail (Context context,String data){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_EMAIL,data);
        editor.apply();
    }

    public static String getDataEmail(Context context){
        return getSharedReferences(context).getString(DATA_EMAIL,"");
    }

    public static void setDataPhone (Context context,String data){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_PHONE,data);
        editor.apply();
    }

    public static String getDataPhone(Context context){
        return getSharedReferences(context).getString(DATA_PHONE,"");
    }

    public  static void   setDataLogin (Context context,boolean status){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putBoolean(DATA_LOGIN,status);
        editor.apply();
    }

    public static boolean getDataLogin (Context context){
        return getSharedReferences(context).getBoolean(DATA_LOGIN,false);
    }

    public static void clearData(Context context){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.remove(DATA_AS);
        editor.remove(DATA_LOGIN);
        editor.remove(DATA_NAME);
        editor.remove(DATA_PASS);
        editor.remove(DATA_PHONE);
        editor.remove(DATA_EMAIL);
        editor.apply();
    }


}
