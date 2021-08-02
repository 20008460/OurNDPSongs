package sg.edu.rp.c346.id20008460.ourndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnShow ;
    EditText etTitle, etSinger , etYear;
    ArrayList<Song> al;
    RatingBar rbStar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.buttonInsert);
        btnShow = findViewById(R.id.buttonShow);

        etTitle = findViewById(R.id.etTitle);
        etSinger = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);

        rbStar = findViewById(R.id.ratingBar2);
        al = new ArrayList<Song>();


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String singer = etSinger.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());

                float stars = rbStar.getRating();

                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertSong(title,singer,year, stars);
                Toast.makeText(MainActivity.this , "Item has been successfully inserted" , Toast.LENGTH_LONG).show();

                etSinger.setText(null);
                etTitle.setText(null);
                etYear.setText(null);
                rbStar.setRating(0);



            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent show = new Intent(MainActivity.this, ShowSongs.class);

                DBHelper dbh = new DBHelper(MainActivity.this);
                al.clear();
                al.addAll(dbh.getAllSong());

                show.putExtra("data", al);
                startActivity(show);
            }
        });


    }

    protected void onPause() {
        super.onPause();
        String title = etTitle.getText().toString();
        String singers = etSinger.getText().toString();
        String years = etYear.getText().toString();


        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefeditor = prefs.edit();

        prefeditor.putString("title" , title);
        prefeditor.putString("singers" , singers);
        prefeditor.putString("year" , years);

        prefeditor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String title = prefs.getString("title", "");
        String singers = prefs.getString("singers", "") ;
        String year = prefs.getString("year", "") ;
        etTitle.setText(title);
        etSinger.setText(singers);
        etYear.setText(year);


    }

}