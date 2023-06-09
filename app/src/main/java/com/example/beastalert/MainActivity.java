package com.example.beastalert;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.time.LocalDate;
import java.time.LocalTime;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    CheckBox checkBox;
    DisplayMetrics window = new DisplayMetrics();
    ArrayAdapter locs;
    LinearLayout threat_screen;
    String loc;
    ScrollView threatScreen;
    LinearLayout homePage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        checkBox = findViewById(R.id.cbx);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiterateData();
            }
        });
        LinearLayout plchldr = findViewById(R.id.plcdhr);
        plchldr.setVisibility(View.VISIBLE);


        homePage = findViewById(R.id.homePage);
        homePage.setVisibility(View.VISIBLE);
        LinearLayout aboutPage;
        aboutPage = findViewById(R.id.aboutPage);
        aboutPage.setVisibility(View.GONE);
        LinearLayout contactPage;
        contactPage = findViewById(R.id.contactPage);
        contactPage.setVisibility(View.GONE);


        Button hta = findViewById(R.id.hta);
        hta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.mainScreen).setBackgroundResource(R.drawable.background);
                homePage.setVisibility(View.GONE);
                aboutPage.setVisibility(View.VISIBLE);

            }
        });
        Button htc = findViewById(R.id.htc);
        htc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.mainScreen).setBackgroundResource(R.drawable.background);
                homePage.setVisibility(View.GONE);
                contactPage.setVisibility(View.VISIBLE);

            }
        });

        Button ath = findViewById(R.id.ath);
        ath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.mainScreen).setBackgroundResource(R.drawable.background);
                reiterateData();
                aboutPage.setVisibility(View.GONE);
                homePage.setVisibility(View.VISIBLE);

            }
        });

        Button atc = findViewById(R.id.atc);
        atc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.mainScreen).setBackgroundResource(R.drawable.background);
                aboutPage.setVisibility(View.GONE);
                contactPage.setVisibility(View.VISIBLE);

            }
        });

        Button cta = findViewById(R.id.cta);
        cta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.mainScreen).setBackgroundResource(R.drawable.background);
                contactPage.setVisibility(View.GONE);
                aboutPage.setVisibility(View.VISIBLE);

            }
        });
        Button cth = findViewById(R.id.cth);
        cth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.mainScreen).setBackgroundResource(R.drawable.background);
                contactPage.setVisibility(View.GONE);
                reiterateData();
                homePage.setVisibility(View.VISIBLE);

            }
        });

        findViewById(R.id.report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Coming Soon....", Toast.LENGTH_LONG).show();

            }
        });




        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        threatScreen = findViewById(R.id.AllThreats);
        threatScreen.setMinimumHeight((int)(height / 2.0f));


        threat_screen = new LinearLayout(this);
        threat_screen.setOrientation(LinearLayout.VERTICAL);
//        TextView warns = new TextView(this);
//        warns.setText("Hahahahaha");
//        warns.setTextColor(Color.parseColor("#FFFFFF"));
//        warns.setBackgroundColor(Color.parseColor("#000000"));
//        threat_screen.addView(warns);
        threatScreen.addView(threat_screen);

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
        LinearLayout.LayoutParams la = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,width/10) ;
        la.setMargins( 30 , 20 , 30 , 10 ) ;

        threatScreen.setMinimumHeight(height);
        threat_screen.removeAllViews();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Detected Dangerous Animal")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int total_docs_for_location = 0;
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
                                    layoutParams.width = 0;
                                    layoutParams.weight = 0.3f;
                                    layoutParams.height = 400;
                                    main.setBackgroundResource(R.drawable.rounf);

                                    ImageView img = new ImageView(MainActivity.this);
                                    if("lion".equals(document.getData().get("Animal"))) {
                                        img.setImageResource(R.drawable.lon);
                                    } else if ("tiger".equals(document.getData().get("Animal"))) {
                                        img.setImageResource(R.drawable.tgr);
                                    }
                                    img.setPadding(10,10,10,10);
                                    main.addView(img, layoutParams);

                                    LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams. WRAP_CONTENT ,  LinearLayout.LayoutParams. WRAP_CONTENT ) ;
                                    lp.setMargins( 30 , 20 , 30 , 10 ) ;
                                    lp.width = 0;
                                    lp.height = 400;
                                    lp.weight = 0.35f;


                                    TextView name = new TextView(MainActivity.this);
                                    name.setText("Name : " + document.getData().get("Animal").toString());




                                    TextView assurity = new TextView(MainActivity.this);
                                    if(!"User-Reported".equals(document.getData().get("Threat level"))) {
                                        assurity.setText("Assurity : " + document.getData().get("Assurity").toString());
                                    }else{
                                        assurity.setText("Assurity : " + "User Reported");
                                    }
                                    TextView Time = new TextView(MainActivity.this);
                                    Integer mins = (int)(Math.random() * 58);
                                    Time.setText("Time : " + document.getData().get("Hour").toString() + " : " + mins);

                                    TextView date = new TextView(MainActivity.this);
                                    date.setText("Date : " + document.getData().get("Date").toString());

                                    TextView space = new TextView(MainActivity.this);
                                    space.setText("   ");


                                    LinearLayout dataLy = new LinearLayout(MainActivity.this);
                                    dataLy.setOrientation(LinearLayout.VERTICAL);
                                    dataLy.addView(space);
                                    dataLy.addView(name);
                                    dataLy.addView(assurity);
                                    dataLy.addView(Time);
                                    dataLy.addView(date);

                                    main.addView(dataLy);


                                    if(checkBox.isChecked()) {
                                        threat_screen.addView(main, ll);


                                        if(LocalTime.now().getHour() - Integer.parseInt(document.getData().get("Hour").toString()) < 5 && LocalDate.now().toString().equals(document.getData().get("Date").toString())) {
                                            total_docs_for_location += 1;
                                        }

                                    } else if (LocalTime.now().getHour() - Integer.parseInt(document.getData().get("Hour").toString()) < 5 && LocalDate.now().toString().equals(document.getData().get("Date").toString())) {
                                        threat_screen.addView(main, ll);
                                        total_docs_for_location += 1;

                                    }


                                }else{
                                    //Toast.makeText(getApplicationContext(),Boolean.toString(checkBox.isChecked()),Toast.LENGTH_LONG).show();
                                }
                            }
                            if(total_docs_for_location < 1){
                                findViewById(R.id.mainScreen).setBackgroundResource(R.drawable.safebg);
                                TextView stat = findViewById(R.id.status);
                                stat.setText("You Are Safe");
                                TextView txtt = new TextView(MainActivity.this);
                                txtt.setText("If you Detect a threat nearby, please REPORT it.");
                                threat_screen.addView(txtt);
                            }else{
                                findViewById(R.id.mainScreen).setBackgroundResource(R.drawable.dangerbg);
                                TextView stat = findViewById(R.id.status);
                                stat.setText("Danger Nearby");
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        LinearLayout plchldr = findViewById(R.id.plcdhr);
        plchldr.setVisibility(View.GONE);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        loc = locs.getItem(position).toString();
        reiterateData();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}