package com.android.velocity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.content.Context;
/**
* @author :ranjit kumar
* @created : Dec 19, 2014
* @description : to check Internet Connectivity
*/
public class VelocityCheckConnectivity {
	static ConnectivityManager connectivityManager;
    static NetworkInfo wifiInfo;
	static NetworkInfo mobileInfo;
	static NetworkInfo network;
	
	/**
     * 
         * @author : ranjit kumar
         * @created : Dec 19, 2014
         * @method name : checkConnection
         * @description : Check for <code>TYPE_WIFI</code> and <code>TYPE_MOBILE</code> connection using <code>isConnected()</code>
         * @param : con Application context
         * @return :Boolean
     */
 
    public static Boolean checkConnection(Context con){
         
        try{
            connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);   
            network = connectivityManager.getActiveNetworkInfo();
            
            if(wifiInfo.isConnected() || mobileInfo.isConnected() || network.isConnected())
            {
                return true;
            }
        }
        catch(Exception e){
            Log.d("GPSTester","CheckConnectivity Exception: " + e.getMessage());
        }
         
        return false;
    }   
}
