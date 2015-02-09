package com.example.velocitysample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class VelocityProcessorStatusActivity extends Activity {
	private TextView textStatus,textStatuscode,textStatusMessage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_velocityprocessorstatus);
		textStatus=(TextView)findViewById(R.id.statusId);
		textStatuscode=(TextView)findViewById(R.id.statusCodeId);
		textStatusMessage=(TextView)findViewById(R.id.statusMessageId);
		String status=getIntent().getExtras().getString("status");
		textStatus.setText(status);
		textStatuscode.setText(getIntent().getExtras().getString("statusCode"));
		textStatusMessage.setText(getIntent().getExtras().getString("statusMessage"));
		
 }
}