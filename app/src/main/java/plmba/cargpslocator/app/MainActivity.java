package plmba.cargpslocator.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    static TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity aa = new MainActivity();

        //TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabspec = tabHost.newTabSpec("Main");
        tabspec.setContent(R.id.tabMain);
        tabspec.setIndicator("Main");
        tabHost.addTab(tabspec);

        tabspec = tabHost.newTabSpec("History");
        tabspec.setContent(R.id.tabHist);
        tabspec.setIndicator("History");
        tabHost.addTab(tabspec);
    }


    public void onBtnClick(View view) {
        String sms = "";
        String lat = "";
        String lng = "";
        String time = "";
        ArrayList<String> links = new ArrayList();
        TextView tv1 = (TextView) findViewById(R.id.textGPSLink);
        ((TextView) findViewById(R.id.textGPSLink)).setMovementMethod(LinkMovementMethod.getInstance());
        //((TextView) findViewById(R.id.textGPSLink)).setText(Html.fromHtml(getResources().getString(R.string.string_with_links)));
        CheckGPS cgps = new CheckGPS();
        Boolean createTab = Boolean.TRUE;


        tv1.setVisibility(View.VISIBLE);
        //FrameLayout content = (FrameLayout) findViewById(android.R.id.tabcontent);
        //TabHost tabHost = (TabHost) content.findViewById(android.R.id.tabhost);
        //getParent().findViewById(R.id.tabHost);


        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(uriSMSURI, null, null, null, null);
        while (cur.moveToNext()) {
            if (cur.getString(2).equals(getNumber())) {
                sms = cur.getString(12);
                lat = cgps.getLat(sms, "Lat");
                lng = cgps.getLng(sms, "Lng");
                if (cgps.isLatOK() && cgps.isLngOK()) {
                    links.add(cgps.getTime(sms) + " <a href=http://maps.google.com/maps?q=" + lat + "," + lng + ">GPS</a>" + "<br><br>");
                }
            }
        }


        tabHost.setCurrentTabByTag("History");
        tv1.setText("");
        if (links.isEmpty()) {
            tv1.setText("No GPS sms found!");
        } else {
            for (String i : links) {
                tv1.append(Html.fromHtml(i));
                //tv1.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }


    }

    public String getNumber() {

        EditText num = (EditText) findViewById(R.id.EditTextPN);
        return num.getText().toString().trim();
    }

    public void onBtnClickCall(View view) {
        String number = "tel:" + getNumber();
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
        startActivity(callIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
