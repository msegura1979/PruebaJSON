package com.example.manuelseguranavarro.pruebajson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final String LIST_RESPONSE = "response";
    final String LIST_GIGS = "gigs";
    final String LISTS_ARTISTS = "artists";
    ProgressBar progressBar;
    ImageView imageView;
    TextView textViewNombre,txtLugar,txtEvento;
    String id="bf2ce448982dd479166da51d5fa49668";
    String urlCiudad = "http://www.nvivo.es/api/request.php?api_key="+id+"&method=city.getEvents&city=Madrid&format=json";
    JSONObject objLista = new JSONObject();



    List<String> allNames = new ArrayList<String>();
    JSONArray cast = new JSONArray(allNames);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Sincronizador().execute(urlCiudad);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public class Sincronizador extends AsyncTask<String, JSONObject, JSONObject>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected JSONObject doInBackground(String... params) {

            try {

                objLista = JsonParser.readJsonFromUrl(params[0]);


            }catch (IOException ex){
                ex.printStackTrace();
            }catch (JSONException ex){
                ex.printStackTrace();
            }
            return objLista;
        }
        @Override
        protected void onPostExecute(JSONObject objetoDesedeDoInBackground) {
            super.onPostExecute(objetoDesedeDoInBackground);

            textViewNombre = (TextView) findViewById(R.id.txtName);

            txtEvento = (TextView)findViewById(R.id.txtCity);

            txtLugar = (TextView)findViewById(R.id.txtLugar);

            imageView = (ImageView)findViewById(R.id.imgView);
            //Picasso.with(MainActivity.this).load(objetoDesedeDoInBackground[1]).into(imageView);
            JSONObject objResponse = null;
            try {
                objResponse = objetoDesedeDoInBackground.getJSONObject(LIST_RESPONSE);
                JSONArray arrGigs = objResponse.getJSONArray(LIST_GIGS);

                for (int i = 0; i < arrGigs.length() ; i++) {
                    JSONObject objGigs = (JSONObject) arrGigs.get(i);
                    String name = objGigs.getString("name");
                    textViewNombre.setText(name);
                    JSONObject objetoVenue = objGigs.getJSONObject("venue");
                   // JSONObject objetodDescripcion = objGigs.getJSONObject("description");

                    //txtLugar.setText(objetoVenue.getJSONObject("venue").getString("name"));
                    txtLugar.setText(objGigs.getString("startDate"));

                    Picasso.with(MainActivity.this).load("http://d36jiqg3u1m7g0.cloudfront.net/salas/143x91/v2_143x91_thumb_91_sala_heineken.jpg").into(imageView);
                    Picasso.with(MainActivity.this).setIndicatorsEnabled(true);


                    Log.e("Gigs",name);
                    JSONArray arrArtist = objGigs.getJSONArray(LISTS_ARTISTS);
                    for (int j = 0; j < arrArtist.length(); j++) {
                        JSONObject objArtista = arrArtist.getJSONObject(j);
                        txtEvento.setText(objArtista.getString("name"));
                        Picasso.with(MainActivity.this).load(String.valueOf(objetoDesedeDoInBackground.getJSONObject("art_logo"))).into(imageView);

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            progressBar = (ProgressBar)findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
        }


    }
}
