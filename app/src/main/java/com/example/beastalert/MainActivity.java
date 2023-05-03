package com.example.beastalert;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DisplayMetrics window = new DisplayMetrics();
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
        getWindowManager().getDefaultDisplay().getMetrics(window);
    }
    int width = window.widthPixels;
    int height = window.heightPixels;
    public void reiterateData(){
        width = window.widthPixels;
        height = window.heightPixels;
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

                                    LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams. MATCH_PARENT ,
                                            LinearLayout.LayoutParams. WRAP_CONTENT ) ;
                                    ll.setMargins( 30 , 20 , 30 , 10 ) ;




                                    LinearLayout main = new LinearLayout(MainActivity.this);
                                    main.setOrientation(main.HORIZONTAL);
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams. WRAP_CONTENT ,  LinearLayout.LayoutParams. WRAP_CONTENT ) ;
                                    layoutParams.setMargins( 30 , 20 , 30 , 10 ) ;
                                    main.setBackgroundResource(R.drawable.rounf);

                                    ImageView img = new ImageView(MainActivity.this);
                                    img.setImageResource(R.drawable.lion);
                                    img.setPadding(10,10,10,10);
                                    main.addView(img, layoutParams);

                                    TextView name = new TextView(MainActivity.this);
                                    name.setText("Name : " + document.getData().get("Animal").toString());
                                    main.addView(name,layoutParams);



                                    TextView assurity = new TextView(MainActivity.this);
                                    assurity.setText("Assurity : " + document.getData().get("Assurity").toString());
                                    main.addView(assurity,layoutParams);

                                    threat_screen.addView(main, ll);
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