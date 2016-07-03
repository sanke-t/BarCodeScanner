package com.example.sanket.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class scanner extends ActionBarActivity {
    TextView metaTextView, metaTextVieww, metaTextVie;
    Button scanButton,view;
    public String codeContent, codeFormat, img, dd;
    HashMap<String,int[]>map=new HashMap<>();
    private Helper h;
    private RecyclerView r;
    private CustomAdapter ad;
    IntentResult scanningResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        metaTextView = (TextView) findViewById(R.id.metaTextView);
        scanButton = (Button) findViewById(R.id.scan_Button);
        view=(Button)findViewById(R.id.view_Button);
        h=new Helper(getApplicationContext(),null,null,1);
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setResultDisplayDuration(0);
        integrator.setPrompt(String.valueOf("Scan a Bar Code"));
        integrator.setScanningRectangle(600, 400);
        integrator.setCameraId(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scanner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void scanNow(View view) {
        if(metaTextView.getText().length()<1)
        {
            h.firstRun();
        }
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setResultDisplayDuration(0);
        integrator.setPrompt(String.valueOf("Scan a Bar Code"));
        integrator.setScanningRectangle(600, 400);
        integrator.setCameraId(0);
        integrator.initiateScan();
    }

    public void show(View view) {
        Intent intent = new Intent(scanner.this, Bill.class);
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            codeFormat = scanningResult.getFormatName();
            codeContent = scanningResult.getContents();
            if (codeContent!=null) {
                StringBuilder url1 = new StringBuilder();
                url1.append("http://192.168.137.1:5984/productlist11/" + codeContent);
                RequestQueue queue = Volley.newRequestQueue(this);
                JsonObjectRequest fetch = new JsonObjectRequest(Request.Method.GET, url1.toString(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String site = response.getString("Name");
                            String a = response.getString("Price");
                            metaTextView.setText(site + " worth Rs. " + a + " added");
                            metaTextView.setVisibility(View.VISIBLE);
                            if(h.CheckIsDataAlreadyInDBorNot(site))
                            {
                                h.price(site);
                                h.qty(site);
                            }
                            else
                            {
                                h.addBook(site,a,1);
                            }
                            /*final LinearLayoutManager lay = new LinearLayoutManager(getApplicationContext());
                            lay.setOrientation(LinearLayoutManager.VERTICAL);
                            r=(RecyclerView) findViewById(R.id.rv);
                            r.setLayoutManager(lay);
                            ad = new CustomAdapter(getApplicationContext(), h.getTable());
                            r.setAdapter(ad);
                            ad.notifyDataSetChanged();
                            JSONObject w =v.getJSONObject("imageLinks");
                               img=w.getString("thumbnail"); */


                    /*JSONObject items = response.getJSONObject("items");
                    String site = items.getString("title");
                    TextView metaTextView = (TextView) findViewById(R.id.metaTextView);
                    metaTextView.setText(site);
                    JSONArray dd = items.getJSONArray("authors");
                    TextView metaTextVieww = (TextView) findViewById(R.id.metaTextVieww);
                    metaTextVieww.setText(dd.toString());*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                queue.add(fetch);


            }
            /* ImageRequest imgRequest = new ImageRequest(img,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            Log.d("SUCCESS_RESPONSE", "Image fetched!");
                            ImageView mImageView = (ImageView) findViewById(R.id.barcode_image_view);
                            mImageView.setImageBitmap(response);

                        }
                    }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error again", "Url2 failed also !");
                    error.printStackTrace();
                }
            });
            queue.add(imgRequest); */
        }


    }

}


