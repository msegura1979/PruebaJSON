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

    ProgressBar progressBar;
    ImageView imageView;
    TextView textViewNombre,txtLugar,txtEvento;
    String []objetos= new String[4];
    String id="bf2ce448982dd479166da51d5fa49668";
    String urlCiudad = "http://www.nvivo.es/api/request.php?api_key="+id+"&method=city.getEvents&city=Madrid&format=json";
    JSONObject jsonCiudad, JsonNombre,JsonImagen,JsonLugar;

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

    public class Sincronizador extends AsyncTask<String, String, String[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String[] doInBackground(String... params) {
            final String OWM_LIST = "response";
            try {
                jsonCiudad = JsonParser.readJsonFromUrl(params[0]);

                //objetos[0]= jsonCiudad.getJSONObject("venue").getString("name");
                //objetos[0] = String.valueOf(jsonCiudad.getJSONObject("response"));
                JSONObject prueba = jsonCiudad.getJSONObject(OWM_LIST);
                JSONArray listaArray = prueba.getJSONArray("gigs");

                Log.e("Objeto array", String.valueOf(listaArray.length()));
                JSONObject objeto;
                for (int i = 0; i < listaArray.length(); i++) {
                    objeto = (JSONObject) listaArray.get(i);

                    Log.e("nombre ",""+objeto.getString("name"));

                   // objetos[0]= objetoArray.getString("name");
                }



//                JsonNombre = JsonParser.readJsonFromUrl(params[1]);
               // objetos[1] = JsonNombre.getString("status");
             //   JsonLugar = JsonParser.readJsonFromUrl(params[2]);
               // objetos[2] = JsonLugar.getString("response");
               // JsonImagen = JsonParser.readJsonFromUrl(params[3]);
                //objetos[3] = JsonImagen.getJSONObject("artists").getString("art_logo");
            }catch (IOException ex){
                ex.printStackTrace();
            }catch (JSONException ex){
                ex.printStackTrace();
            }
            return objetos;
        }
        @Override
        protected void onPostExecute(String[] stringDesedeDoInBackground) {
            super.onPostExecute(stringDesedeDoInBackground);
            textViewNombre = (TextView) findViewById(R.id.txtName);
            textViewNombre.setText(stringDesedeDoInBackground[0]);
            txtEvento = (TextView)findViewById(R.id.txtCity);
            txtEvento.setText(stringDesedeDoInBackground[1]);
            txtLugar = (TextView)findViewById(R.id.txtLugar);
            txtLugar.setText(stringDesedeDoInBackground[2]);
            imageView = (ImageView)findViewById(R.id.imgView);
            Picasso.with(MainActivity.this).load(stringDesedeDoInBackground[3]).into(imageView);
            progressBar = (ProgressBar)findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
        }


    }
}
