package souche.baptiste.hackathon_6;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //connect to wifi




        initiateSkypeUri(getApplicationContext(), "live:zertycraft")

    }

    //fonctions

    void switch_data(byte arduino_data)
    {
        int action = arduino_data%16;
        int contact = arduino_data%16;

/// Actions :
/// 1-	Appeler
///	2-	Ecrire
///	3-	Lire
/// 4-	Voir

        String[] contact_array; // 0 => Valeur nulle
        contact_array = new String[7]; // personne
        contact_array[1] = "0769728212"; // Baptiste
        contact_array[2] = "0637943947"; // Claire
        contact_array[3] = "0780037372"; // Gaëtan
        contact_array[4] = "0636938842"; // Johan
        contact_array[5] = "0637138771"; // Mathieu
        contact_array[6] = "0675826026"; // Morgane

        switch(action)
        {
            case 1: // Appeler
                phone_call(contact_array[contact]);
                break;
            case 2: // Envoyer
                phone_opensms(contact_array[contact]);
                break;
            case 3: // Lire
                phone_opensms(contact_array[contact]);
                break;
            case 4: // Voir
                //phone_skype(contact_);
                break;
            default:
                break;
        }
    }

    /* -------------------------------------------------------------------------------------------------------------------- */

    void phone_call(String phone_number) {
        Intent intent_call = new Intent(Intent.ACTION_CALL);
        intent_call.setData(Uri.parse("tel:" + phone_number));
        if (intent_call.resolveActivity(getPackageManager()) != null) {
            startActivity(intent_call);
        } else
            Toast.makeText(MainActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
    }

    /* -------------------------------------------------------------------------------------------------------------------- */

    void phone_call_perm() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CALL_PHONE},
                1);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == 1)
            Toast.makeText(MainActivity.this, "Permission denieIntentd to read your External storage", Toast.LENGTH_SHORT).show();
    }

    /* -------------------------------------------------------------------------------------------------------------------- */

    void phone_opensms(String phone_number) {
        Intent intent_mess = new Intent(Intent.ACTION_SENDTO);
        intent_mess.setData(Uri.parse("smsto:" + phone_number));
        //intent_mess.putExtra("sms_body", "le message");
        startActivity(intent_mess);
    }
    /* -------------------------------------------------------------------------------------------------------------------- */

    void phone_skype(String username) {
        Intent intent_mess = new Intent(Intent.ACTION_SENDTO);
        intent_mess.setData(Uri.parse("skype:" + username + "?call"));
        //intent_mess.putExtra("sms_body", "le message");
        startActivity(intent_mess);
    }


    public void initiateSkypeUri(Context myContext, String mySkypeUri) {

        // Create the Intent from our Skype URI.
        Uri skypeUri = Uri.parse("skype:" + mySkypeUri + "?call");
        Toast.makeText(MainActivity.this, skypeUri.toString(), Toast.LENGTH_SHORT).show();

        Intent myIntent = new Intent(Intent.ACTION_VIEW, skypeUri);

        // Restrict the Intent to being handled by the Skype for Android client only.
        myIntent.setComponent(new ComponentName("com.skype.raider", "com.skype.raider.Main"));
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Initiate the Intent. It should never fail because you've already established the
        // presence of its handler (although there is an extremely minute window where that
        // handler can go away).
        myContext.startActivity(myIntent);

        return;
    }
    /* -------------------------------------------------------------------------------------------------------------------- */

    void phone_Sendsms(String phone_number) {
        Intent intent_ssms = new Intent(Intent.ACTION_VIEW);
        intent_ssms.putExtra("sms_body", "cc");
        intent_ssms.setType("vnd.android-dir/mms-sms");
        startActivity(intent_ssms);
    }

}