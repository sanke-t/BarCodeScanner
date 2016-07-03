package com.example.sanket.test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Bill extends ActionBarActivity {

    Helper h=new Helper(this,null,null,1);
    Calendar c= Calendar.getInstance();
    SimpleDateFormat dateformat = new SimpleDateFormat("hhmmss");
    String time=dateformat.format(c.getTime());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        /*Map<String, Object> params = new HashMap<String, Object>();
        params.put("time",time);
        params.put("number of items",h.quantity());
        params.put("total cost", h.sum());
        RequestQueue queue=Volley.newRequestQueue(this);
        String url = "http://192.168.173.1:5984/test/transaction/"+time;
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String> ();
                params.put("'{\"name\" :","\"Alif\"}'");
                return params;
            }

        };

        queue.add(putRequest);*/

        ListView lvCustomList = (ListView) findViewById(R.id.lv_custom_list);
        ListAdapter listAdapter=new ListAdapter(Bill.this,h.getTable());
        lvCustomList.setAdapter(listAdapter);
        TextView t=(TextView)findViewById(R.id.grand);
        t.setText("TOTAL              " + h.sum());
        TextView tr=(TextView)findViewById(R.id.transaction);
        tr.setText("YOUR TRANSACTION NUMBER IS USER_1_"+time);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
