package com.example.beastalert;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayAdapter locs;
    LinearLayout threat_screen;
    String loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        ScrollView threatScreen = findViewById(R.id.AllThreats);
        threatScreen.setMinimumHeight((int)(height / 2.0f));


        threat_screen = new LinearLayout(this);
        threat_screen.setOrientation(LinearLayout.VERTICAL);
//        TextView warns = new TextView(this);
//        warns.setText("Hahahahaha");
//        warns.setTextColor(Color.parseColor("#FFFFFF"));
//        warns.setBackgroundColor(Color.parseColor("#000000"));
//        threat_screen.addView(warns);
        threatScreen.addView(threat_screen);
//        threat_screen

        String[] all_locs = {"Gurgaon","sector-12", "sector-14"};
        Spinner loc_select = findViewById(R.id.Loc);
        locs = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,all_locs);
        loc_select.setAdapter(locs);
        loc_select.setOnItemSelectedListener(this);
        loc_select.setSelected(true);

    }

    public void reiterateData(){
        threat_screen.removeAllViews();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Detected Dangerous Animal")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(loc.equals(document.getData().get("Area").toString())) {
                                    TextView elt = new TextView(MainActivity.this);
                                    elt.setText(document.getData().get("Animal").toString());
                                    threat_screen.addView(elt);
                                }else{
                                    Toast.makeText(getApplicationContext(),document.getData().toString(),Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getApplicationContext(),locs.getItem(position).toString(),Toast.LENGTH_LONG).show();
        loc = locs.getItem(position).toString();
        reiterateData();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}