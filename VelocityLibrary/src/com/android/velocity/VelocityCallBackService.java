package com.android.velocity;

import com.velocity.verify.response.VelocityResponse;

/**
 * 
 * @author ranjitk
 *
 */
public interface VelocityCallBackService {
	public void  onSuccess(VelocityResponse velocityResponse);
    public void onFaliure(VelocityResponse velocityResponse);
/*	public void  onSuccess(String sessionToken);
    public void onFaliure(int statusCode);
	*/
}
