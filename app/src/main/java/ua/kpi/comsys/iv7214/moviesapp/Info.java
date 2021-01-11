package ua.kpi.comsys.iv7214.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import static ua.kpi.comsys.iv7214.moviesapp.Adaptery.getResId;


public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        getIncomingIntent();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("image") && getIntent().hasExtra("name") &&
            getIntent().hasExtra("year") && getIntent().hasExtra("imdbID")){
            String title = getIntent().getStringExtra("name");
            String poster = getIntent().getStringExtra("image");
            String imdbID = getIntent().getStringExtra("imdbID");
            String year = getIntent().getStringExtra("year");
            //new
            String rated = getIntent().getStringExtra("rated");
            String released = getIntent().getStringExtra("released");
            String runtime = getIntent().getStringExtra("runtime");
            String genre = getIntent().getStringExtra("genre");
            String director = getIntent().getStringExtra("director");
            String writer = getIntent().getStringExtra("writer");
            String actors = getIntent().getStringExtra("actors");

            String plot = getIntent().getStringExtra("plot");
            String language = getIntent().getStringExtra("language");
            String country = getIntent().getStringExtra("country");
            String awards = getIntent().getStringExtra("awards");
            String imdbRating = getIntent().getStringExtra("imdbRating");
            String imdbVotes = getIntent().getStringExtra("imdbVotes");
            String type = getIntent().getStringExtra("type");
            String production = getIntent().getStringExtra("production");

            setData(title, year, imdbID, poster, rated, released, runtime, genre, director, writer,
                    actors, plot, language, country, awards, imdbRating, imdbVotes, type, production);
        }
    }

    private void setData(String title, String year, String imdbID, String poster, String rated,
                         String released, String runtime, String genre, String director,
                         String writer, String actors, String plot, String language, String country,
                         String awards, String imdbRating, String imdbVotes, String type, String production){
        TextView name = findViewById(R.id.nameText2);
        TextView yr = findViewById(R.id.yearText2);
        TextView id = findViewById(R.id.idText);
        name.setText(title);
        yr.setText(year);
        id.setText(imdbID);
        //new
        TextView rate = findViewById(R.id.ratedText);
        TextView releas = findViewById(R.id.releasedText);
        TextView runt = findViewById(R.id.runtimeText);
        TextView genr = findViewById(R.id.genreText);
        TextView direc = findViewById(R.id.directorText);
        TextView writ = findViewById(R.id.writerText);
        TextView act = findViewById(R.id.actorsText);

        TextView plt = findViewById(R.id.plotText);
        TextView lang = findViewById(R.id.languageText);
        TextView count = findViewById(R.id.countryText);
        TextView awrd = findViewById(R.id.awardsText);
        TextView rat = findViewById(R.id.ratingText);
        TextView vot = findViewById(R.id.votesText);
        TextView typ = findViewById(R.id.typeText);
        TextView prod = findViewById(R.id.productionText);
        rate.setText(rated);
        releas.setText(released);
        runt.setText(runtime);
        genr.setText(genre);
        direc.setText(director);
        writ.setText(writer);
        act.setText(actors);

        plt.setText(plot);
        lang.setText(language);
        count.setText(country);
        awrd.setText(awards);
        rat.setText(imdbRating);
        vot.setText(imdbVotes);
        typ.setText(type);
        prod.setText(production);

        ImageView image = findViewById(R.id.imageView);
        int resID = getResId(poster, R.drawable.class);
        Glide.with(this).load(resID).into(image);

    }


}
