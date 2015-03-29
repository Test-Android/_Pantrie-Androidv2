package com.nicodangelo.pantrie.list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jetts.pantrie.R;
import com.nicodangelo.pantrie.item.ItemController;
import com.nicodangelo.pantrie.util.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ListMain extends ActionBarActivity
{
    ArrayList<String> list = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ItemController itemList = new ItemController();
    int curSize = 0;
    ListView lv;
    Boolean paused = false;
    AlertDialog ad;
    AlertDialog.Builder br;

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_list_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("List");

        Button btn = (Button) findViewById(R.id.btnAdd);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText edit = (EditText) findViewById(R.id.txtItem);
                itemList.addItem(edit.getText().toString());
                list.add(itemList.getName(curSize));
                edit.setText("");
                curSize++;
                adapter.notifyDataSetChanged();
            }
        };

        btn.setOnClickListener(listener);
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                for(int k = 0; k < curSize; k++)
                {
                    if(k == position)
                    {
                        final int a = k;
                        AlertDialog.Builder ab = new AlertDialog.Builder(ListMain.this)
                                .setTitle("Edit Pantrie?")
                                .setPositiveButton("Edit Item", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        br = new AlertDialog.Builder(ListMain.this);
                                        br.setTitle(itemList.getName(position));

                                        final EditText one = new EditText(ListMain.this);
                                        one.setHint("Set Amount");
                                        final EditText two = new EditText(ListMain.this);
                                        two.setHint("Set Low Amount");

                                        one.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        two.setInputType(InputType.TYPE_CLASS_NUMBER);

                                        LinearLayout lay = new LinearLayout(ListMain.this);
                                        lay.setOrientation(LinearLayout.VERTICAL);
                                        lay.addView(one);
                                        lay.addView(two);
                                        br.setView(lay);
                                        br.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                                        {
                                            public void onClick(DialogInterface dialog, int whichButton)
                                            {
                                                if(!TextUtils.isEmpty(one.getText().toString()))
                                                {
                                                    itemList.setAmount(a, Integer.parseInt(one.getText().toString()));
                                                    list.set(a,itemList.getInfo(a));
                                                }
                                                if(!TextUtils.isEmpty(two.getText().toString()))
                                                    itemList.setAmount(a,Integer.parseInt(two.getText().toString()));
                                                adapter.notifyDataSetChanged();

                                            }
                                        });
                                        br.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                dialog.cancel();
                                            }
                                        });
                                        br.create();
                                        br.show();
                                    }
                                })
                                .setNeutralButton("New Item", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        br = new AlertDialog.Builder(ListMain.this);
                                        br.setTitle("Create New Item: All Fields Required");
                                        final EditText name = new EditText(ListMain.this);
                                        name.setHint("Name of item");
                                        final EditText amount = new EditText(ListMain.this);
                                        amount.setHint("Amount of items");

                                        name.setInputType(InputType.TYPE_CLASS_TEXT);
                                        amount.setInputType(InputType.TYPE_CLASS_NUMBER);

                                        LinearLayout lay = new LinearLayout(ListMain.this);
                                        lay.setOrientation(LinearLayout.VERTICAL);
                                        lay.addView(name);
                                        lay.addView(amount);
                                        br.setView(lay);
                                        br.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton)
                                            {
                                                itemList.addItem(name.getText().toString());
                                                curSize++;
                                                if (!TextUtils.isEmpty(amount.getText().toString()))
                                                    itemList.setAmount(a +1, Integer.parseInt(amount.getText().toString()));
                                                list.add(itemList.getName(a + 1));
                                                adapter.notifyDataSetChanged();

                                                ad.dismiss();

                                                br = new AlertDialog.Builder(ListMain.this);
                                                br.setTitle("Extra Info: Optional");
                                                final EditText setLow = new EditText(ListMain.this);
                                                setLow.setHint("Set Low Amount Warning");
                                                final EditText type = new EditText(ListMain.this);
                                                type.setHint("Solid, or Liquid");
                                                final EditText measurement = new EditText(ListMain.this);
                                                measurement.setHint("Set Measurement Type");

                                                setLow.setInputType(InputType.TYPE_CLASS_NUMBER);
                                                type.setInputType(InputType.TYPE_CLASS_TEXT);
                                                measurement.setInputType(InputType.TYPE_CLASS_TEXT);

                                                LinearLayout lay = new LinearLayout(ListMain.this);
                                                lay.setOrientation(LinearLayout.VERTICAL);
                                                lay.addView(setLow);
                                                lay.addView(type);
                                                br.setView(lay)
                                                        .setPositiveButton("Okay", new DialogInterface.OnClickListener()
                                                        {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which)
                                                            {
                                                                if (!TextUtils.isEmpty(setLow.getText().toString()))
                                                                    itemList.setLowAmount(a + 1, Integer.parseInt(setLow.getText()
                                                                            .toString()));
                                                                if (!TextUtils.isEmpty(type.getText().toString()))
                                                                    itemList.setType(a + 1, type.getText().toString());
                                                                if (!TextUtils.isEmpty(measurement.getText().toString()))
                                                                    itemList.setMes(a + 1, measurement.getText().toString());

                                                            }
                                                        });


                                            }
                                        });
                                        ad = br.create();
                                        ad = br.show();
                                    }
                                })
                                .setNegativeButton("Get Info", new DialogInterface.OnClickListener()
                                {
                                    @Override public void onClick(DialogInterface dialog, int which)
                                    {
                                        br = new AlertDialog.Builder(ListMain.this)
                                                .setTitle("Info");
                                        final TextView name = new TextView(ListMain.this);
                                        final TextView amount = new TextView(ListMain.this);
                                        final TextView lowAmount = new TextView(ListMain.this);
                                        final TextView type = new TextView(ListMain.this);
                                        final TextView mes = new TextView(ListMain.this);

                                        name.setText(itemList.getName(a + 1));
                                        amount.setText("Amount: " + itemList.getAmount(a + 1));
                                        lowAmount.setText("Low Amount: " + itemList.getLowAmount(a + 1));
                                        type.setText("Item Type: " + itemList.getType(a + 1));
                                        mes.setText("Measurement Type: " + itemList.getMes(a + 1));

                                        LinearLayout lay = new LinearLayout(ListMain.this);
                                        lay.setOrientation(LinearLayout.VERTICAL);
                                        lay.addView(name);
                                        lay.addView(amount);
                                        lay.addView(lowAmount);
                                        lay.addView(type);
                                        lay.addView(mes);
                                        br.setView(lay)
                                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        ad.dismiss();
                                                    }
                                                });
                                        ad = br.create();
                                        ad = br.show();

                                        adapter.notifyDataSetChanged();
                                    }
                                });
                        ab.create();
                        ab.show();
                    }
                }
            }
        });
        if(getInfo())
        {
            for(int k = 0; k < itemList.getSpot(); k++)
            {
                list.add(itemList.getInfo(k));
                adapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    protected void onPause()
    {
        itemList.printAll();
        paused = true;
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_list_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.action_settings)
        {
            Intent i = new Intent(ListMain.this, Settings.class);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_add_item)
        {
            br = new AlertDialog.Builder(ListMain.this);
            br.setTitle("Create New Item: All Fields Required");
            final EditText name = new EditText(ListMain.this);
            name.setHint("Name of item");
            final EditText amount = new EditText(ListMain.this);
            amount.setHint("Amount of items");

            name.setInputType(InputType.TYPE_CLASS_TEXT);
            amount.setInputType(InputType.TYPE_CLASS_NUMBER);

            LinearLayout lay = new LinearLayout(ListMain.this);
            lay.setOrientation(LinearLayout.VERTICAL);
            lay.addView(name);
            lay.addView(amount);
            br.setView(lay);
            br.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    itemList.addItem(name.getText().toString());
                    if (!TextUtils.isEmpty(amount.getText().toString()))
                        itemList.setAmount(curSize, Integer.parseInt(amount.getText().toString()));
                    list.add(itemList.getInfo(curSize));
                    adapter.notifyDataSetChanged();

                    ad.dismiss();

                    br = new AlertDialog.Builder(ListMain.this);
                    br.setTitle("Extra Info: Optional");
                    final EditText setLow = new EditText(ListMain.this);
                    setLow.setHint("Set Low Amount Warning");
                    final EditText type = new EditText(ListMain.this);
                    type.setHint("Solid, or Liquid");
                    final EditText measurement = new EditText(ListMain.this);
                    measurement.setHint("Set Measurement Type");

                    setLow.setInputType(InputType.TYPE_CLASS_NUMBER);
                    type.setInputType(InputType.TYPE_CLASS_TEXT);
                    measurement.setInputType(InputType.TYPE_CLASS_TEXT);

                    LinearLayout lay = new LinearLayout(ListMain.this);
                    lay.setOrientation(LinearLayout.VERTICAL);
                    lay.addView(setLow);
                    lay.addView(type);
                    lay.addView(measurement);
                    br.setView(lay)
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    if (!TextUtils.isEmpty(setLow.getText().toString()))
                                        itemList.setLowAmount(curSize, Integer.parseInt(setLow.getText()
                                                .toString()));
                                    if (!TextUtils.isEmpty(type.getText().toString()))
                                        itemList.setType(curSize, type.getText().toString());
                                    if (!TextUtils.isEmpty(measurement.getText().toString()))
                                        itemList.setMes(curSize, measurement.getText().toString());
                                    curSize++;

                                }
                            });
                    ad = br.create();
                    ad = br.show();

                }
            });
            ad = br.create();
            ad = br.show();
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean getInfo()
    {
        String state = "";
        File s = getCacheDir();
        try
        {
            FileInputStream a = new FileInputStream(s);
            int g = 0;
            while((g = a.read()) != -1)
                state = state + ((char)g);
            a.close();
            if(state.equals("true"))
            {
                FileOutputStream fi = new FileOutputStream(s);
                fi.write("false".getBytes());
                fi.close();
                return true;
            }
            else
                return false;

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;

    }
}

