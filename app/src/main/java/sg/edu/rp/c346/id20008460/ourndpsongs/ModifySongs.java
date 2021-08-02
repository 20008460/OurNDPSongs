package sg.edu.rp.c346.id20008460.ourndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import java.util.ArrayList;

public class ModifySongs extends AppCompatActivity {

    Button btnUpdate , btnDelete, btnCancel;
    EditText etTitle, etSinger , etYear;

    RatingBar rbStar;
    Song data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_songs);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.buttonCancel);
        rbStar = findViewById(R.id.ratingBar3);

        etTitle = findViewById(R.id.upTitle);
        etSinger = findViewById(R.id.upSingers);
        etYear = findViewById(R.id.upYear);


        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");


        rbStar.setRating(data.getStars());
        etTitle.setText(data.getTitle());
        etSinger.setText(data.getSingers());
        etYear.setText(Integer.toString(data.getYear()));


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifySongs.this);
                data.setTitle(etTitle.getText().toString());
                data.setSingers(etSinger.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));

                float starRating = rbStar.getRating();

                data.setStars(starRating);

                dbh.updateSong(data);
                dbh.close();

                finish();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifySongs.this);
                dbh.deleteSong(data.get_id());

                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModifySongs.this,
                        ShowSongs.class);
                startActivity(i);
            }
        });

    }



}