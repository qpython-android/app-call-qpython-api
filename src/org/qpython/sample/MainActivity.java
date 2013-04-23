package org.qpython.sample;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	/*
	 * 
	 * 
	 */
	private final int SCRIPT_EXEC_PY = 40001;
	private final String extPlgPlusName = "com.hipipal.qpy";
	public static boolean checkAppInstalledByName(Context context, String packageName) {
	    if (packageName == null || "".equals(packageName))  
	        return false;  
	    try {  
	        ApplicationInfo info = context.getPackageManager().getApplicationInfo(  
	                packageName, PackageManager.GET_UNINSTALLED_PACKAGES);  
	        
	        Log.d("QPYMAIN",  "checkAppInstalledByName:"+packageName+" found");
	        return true;  
	    } catch (NameNotFoundException e) {  
	        Log.d("QPYMAIN",  "checkAppInstalledByName:"+packageName+" not found");

	        return false;  
	    }  
	}
	
	public void onQPyExec(View v) {
		
		if (checkAppInstalledByName(getApplicationContext(), extPlgPlusName)) {
			Toast.makeText(this, "Sample of calling QPython API", Toast.LENGTH_SHORT).show();

	        Intent intent = new Intent();
	        intent.setClassName(extPlgPlusName, extPlgPlusName+".MPyApi");
	        intent.setAction(extPlgPlusName+".action.MPyApi");

	        Bundle mBundle = new Bundle(); 
	        mBundle.putString("app", "myappid");
	        mBundle.putString("act", "onPyApi");
	        mBundle.putString("flag", "onQPyExec");            // any String flag you may use in your context
	        mBundle.putString("param", "");          // param String param you may use in your context
	        mBundle.putString("pycode", "print 'Hello world'");        // The String Python code

	        intent.putExtras(mBundle);

	        startActivityForResult(intent, SCRIPT_EXEC_PY);
	    } else {
	        Toast.makeText(getApplicationContext(), "Please install QPython Player first", Toast.LENGTH_LONG).show();

	    	try {
		        Uri uLink = Uri.parse("market://details?id=com.hipipal.qpy");
		        Intent intent = new Intent( Intent.ACTION_VIEW, uLink );
		        startActivity(intent);
	    	} catch (Exception e) {
		        Uri uLink = Uri.parse("http://qpython.com");
		        Intent intent = new Intent( Intent.ACTION_VIEW, uLink );
		        startActivity(intent);
	    	}
	    	
	    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	    if (requestCode == SCRIPT_EXEC_PY) {
	        Bundle bundle = data.getExtras();
	        String flag = bundle.getString("flag"); // flag you set
	        String param = bundle.getString("param"); // param you set 
	        String result = bundle.getString("result"); // Result your Pycode generate
	        Toast.makeText(this, "onQPyExec: return ("+result+")", Toast.LENGTH_SHORT).show();
	    }
	}

}
