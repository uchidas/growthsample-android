package me.uchidas.growthsample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.growthbeat.Growthbeat;
import com.growthbeat.analytics.GrowthAnalytics;
import com.growthpush.GrowthPush;
import com.growthpush.handler.BaseReceiveHandler;
import com.growthpush.handler.DefaultReceiveHandler;
import com.growthpush.model.Environment;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Growthbeat.getInstance().initialize(this.getApplicationContext(), GrowthbeatConstants.APPLICATION_ID, GrowthbeatConstants.CREDENTIAL_ID);
		GrowthAnalytics.getInstance().setBasicTags();
		GrowthPush.getInstance().requestRegistrationId(GrowthbeatConstants.ANDROID_SENDER_ID, BuildConfig.DEBUG ? Environment.development : Environment
				.production);
		((DefaultReceiveHandler) GrowthPush.getInstance().getReceiveHandler()).setCallback(new BaseReceiveHandler.Callback() {
			@Override
			public void onOpen(Context context, Intent intent) {
				super.onOpen(context, intent);
				GrowthPush.getInstance().trackEvent("Launch via push notification");
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onStart() {
		super.onStart();
		Growthbeat.getInstance().start();
	}

	@Override
	public void onStop() {
		super.onStop();
		Growthbeat.getInstance().stop();
	}

}
