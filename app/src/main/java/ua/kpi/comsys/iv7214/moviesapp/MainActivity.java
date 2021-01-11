package ua.kpi.comsys.iv7214.moviesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Reader reader = new Reader();


    private static String JSON_URL = "https://api.jsonbin.io/b/5ff8937063e86571a2e384a7";

    private List<Movie> movieList = new ArrayList<>();
    static final String TITLE="TITLE";
    static final String TYPE="TYPE";
    static final String YEAR="YEAR";
    RecyclerView recyclerView;
    public Adaptery mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        GetData getData = new GetData();
        getData.execute();

        EditText editText = findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        Button create = (Button) findViewById(R.id.createButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createIntent = new Intent(MainActivity.this, Create.class);
                startActivityForResult(createIntent, 2);
            }
        });

        ToggleButton remove = (ToggleButton) findViewById(R.id.removeButton);
        remove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mAdapter.deleteMode=1;
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter.deleteMode=0;
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2) {
            Movie m = new Movie();
            m.setTitle(data.getStringExtra(TITLE));
            m.setType(data.getStringExtra(TYPE));
            m.setYear(data.getStringExtra(YEAR));
            m.setPoster("");
            m.setImdbID("");
            movieList.add(m);
            mAdapter.notifyDataSetChanged();
        }
    }



    public class GetData extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {

            String current = reader.fileToString(MainActivity.this, "MoviesList.txt");
            return current;

        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("Search");
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    Movie model = new Movie();
                    model.setImdbID("imdbID: "+jsonObject1.getString("imdbID"));
                    model.setTitle(jsonObject1.getString("Title"));
                    model.setYear("Year: "+jsonObject1.getString("Year"));
                    model.setPoster(jsonObject1.getString("Poster"));

                    //full info
                    String infoString = reader.fileToString(MainActivity.this, jsonObject1.getString("imdbID")+".txt");
                    if(infoString!="no file"){
                        JSONObject info = new JSONObject(infoString);
                        model.setRated("Rated: "+info.getString("Rated"));
                        model.setReleased("Released: "+info.getString("Released"));
                        model.setRuntime("Runtime: "+info.getString("Runtime"));
                        model.setGenre("Genre: "+info.getString("Genre"));
                        model.setDirector("Director: "+info.getString("Director"));
                        model.setWriter("Writer: "+info.getString("Writer"));
                        model.setActors("Actors: "+info.getString("Actors"));

                        model.setPlot("Plot: "+info.getString("Plot"));
                        model.setLanguage("Language: "+info.getString("Language"));
                        model.setCountry("Country: "+info.getString("Country"));
                        model.setAwards("Awards: "+info.getString("Awards"));
                        model.setImdbRating("imdbRating: "+info.getString("imdbRating"));
                        model.setImdbVotes("imdbVotes: "+info.getString("imdbVotes"));
                        model.setType("Type: "+info.getString("Type"));
                        model.setProduction("Production: "+info.getString("Production"));
                    }

                    movieList.add(model);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecyclerView(movieList);
        }
    }

    public void addMovie(String a, String b, String c){
        Movie m = new Movie();
        m.setTitle(a);
        m.setType(b);
        m.setYear(c);
        movieList.add(m);
        mAdapter.notifyDataSetChanged();
    }


    private void PutDataIntoRecyclerView(List<Movie> movieList) {
        mAdapter = new Adaptery(this, movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }




    public void popupMessage(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup, null);

        // create the popup window
        int width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

    private void filter(String text){
        ArrayList<Movie> filteredList = new ArrayList<>();

        for(Movie item: movieList) {
            if(item.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
        if(mAdapter.getItemCount()==0){
            popupMessage(recyclerView);
        }
    }

}
