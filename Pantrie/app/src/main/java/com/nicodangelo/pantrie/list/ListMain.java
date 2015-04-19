package com.nicodangelo.pantrie.list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.nicodangelo.pantrie.R;
import com.nicodangelo.pantrie.database.DBHandler;
import com.nicodangelo.pantrie.database.PantrieDBHandler;
import com.nicodangelo.pantrie.item.Item;
import com.nicodangelo.pantrie.item.ItemController;
import com.nicodangelo.pantrie.item.ListAdapter;
import com.nicodangelo.pantrie.util.Settings;

import java.util.ArrayList;

//whole or partial???

public class ListMain extends ActionBarActivity
{
    ArrayList<String> list = new ArrayList<String>();
    ListAdapter adapter;
    ItemController itemList = new ItemController();
    int curSize = 0;
    ListView lv;
    Boolean paused = false;
    AlertDialog ad;
    AlertDialog.Builder br;
    DBHandler db;

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_list_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("List");
        adapter = new ListAdapter(this, list);
        db = new PantrieDBHandler(this,"itemsDB", new SQLiteDatabase.CursorFactory()
        {
        db = new DBHandler(this,"itemsDB", new SQLiteDatabase.CursorFactory() {
            @Override
            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query)
            {
                return null;
            }
        }, 1);
        db.databaseToString(list);
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
                                        br.setTitle(list.get(position));

                                        final EditText one = new EditText(ListMain.this);
                                        one.setHint("Set Amount");
                                        final EditText two = new EditText(ListMain.this);
                                        two.setHint("Set Low Amount");

                                        one.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        two.setInputType(InputType.TYPE_CLASS_NUMBER);

                                        LinearLayout lay = new LinearLayout(ListMain.this);
                                        lay.setOrientation(LinearLayout.VERTICAL);
                                        lay.addView(one);
//                                        lay.addView(two);
                                        br.setView(lay);
                                        br.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                                        {
                                            public void onClick(DialogInterface dialog, int whichButton)
                                            {
                                                if (!TextUtils.isEmpty(one.getText().toString()))
                                                {
                                                    db.setItemAmount(list.get(position), Integer.parseInt(one.getText().toString()));
                                                }
//                                                if (!TextUtils.isEmpty(two.getText().toString()))
//                                                    items.get(a).setLow(Integer.parseInt(two.getText().toString()));
                                                adapter.notifyDataSetChanged();

                                            }
                                        });
                                        br.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                                        {
                                            public void onClick(DialogInterface dialog, int whichButton)
                                            {
                                                dialog.cancel();
                                            }
                                        });
                                        br.create();
                                        br.show();
                                    }
                                });
/*                                .setNegativeButton("Get Info", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ad.dismiss();
                                        br = new AlertDialog.Builder(ListMain.this)
                                                .setTitle("Info");
                                        final TextView name = new TextView(ListMain.this);
                                        final TextView amount = new TextView(ListMain.this);
                                        final TextView lowAmount = new TextView(ListMain.this);
                                        final TextView type = new TextView(ListMain.this);
                                        final TextView mes = new TextView(ListMain.this);

                                        name.setText(items.get(a).getName());
                                       amount.setText("Amount: " + items.get(a).getAmount());
                                        lowAmount.setText("Low Amount: " + items.get(a).getLow());
                                        type.setText("Item Type: " + items.get(a).getType());
                                        mes.setText("Measurement Type: " + items.get(a).getMeasurment());

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
                                }); */
                        ab.create();
                        ab.show();
                    }
                }
            }
        });
    }

    public void changeSort(View view)
    {
        String no = "Fuck ya chicken strips";
/*        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("Thread Started");
              ArrayList<Item> sort = new ArrayList<Item>();
                for(int k = 0; k < items.size(); k++)
                {
                    sort.add(items.get(k));
                    System.out.println(sort.get(k).getName());
                }

                long start = System.nanoTime();
                int max;
                System.out.println("start");
                for (int k = 0; k < curSize - 1; k++)
                {
                    System.out.println("sort size now : " + items.size());
                    max = k;
                    System.out.println("Max = " + max);
                    for (int j = k + 1; j < curSize; j++)
                    {
                        System.out.println("sort size now : " + items.size());
/*
*  So the compareTo method in java compares the entireity of one string to the other
*  so while it works for smaller strings it won't for what we're trying to do
* /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
* The compare method that i created comapares the chars of the strings rather than the entire string added together and
* subtracted from the other.

                        if(items.get(j).getName().trim().compareToIgnoreCase(items.get(max).getName().trim()) < 0)
                            max = j;
                        System.out.println("sorting..." + max + " - " + items.get(j).getName() + " with " + items.get(max).getName());
                    }
                    if(max != k)
                    {
                        Item temp = items.get(k);
                        Item temp2 = items.get(max);
                        items.add(k, temp2);
                        items.add(max, temp);
                    }
                }
//                items.clear();
                System.out.println("finished sorting...");
/*              for(int x = 0; x < sort.size(); x++)
                {
                    Item item = new Item(sort.get(x).getName(),sort.get(x).getAmount());
                    System.out.println(item.getName());
                    items.add(item);
                }

                long stop = System.nanoTime();
                System.out.println((stop - start));

        
//        items = Sorty.sortString(items, curSize);

        adapter.notifyDataSetChanged();
        for(int k = 0; k < curSize; k++)
        {
            list.add(k, items.get(k).getName());
            adapter.notifyDataSetChanged();
        }
                adapter.notifyDataSetChanged();
            }
        }).start(); */
/*        if(curSize != 0 && curSize != 1)
        {
            br = new AlertDialog.Builder(this)
                    .setTitle("Sort the list?")
                    .setCancelable(false);
            final Button az = new Button(ListMain.this);
            az.setText("Sort a-z");
            az.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ad.dismiss();
                    list = Sorty.sortAZ(list);
                    adapter.notifyDataSetChanged();
                }
            });
            final Button za = new Button(ListMain.this);
            za.setText("Sort z-a");
            za.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ad.dismiss();
                    items = Sorty.sortZA(items);
                    for(int k = 0; k < items.size(); k++)
                    {
                        list.set(k, items.get(k).getName());
                    }
                    adapter.notifyDataSetChanged();
                }
            }); */
/*            final Button numUp = new Button(ListMain.this);
            numUp.setText("Sort 1 2 3");
            numUp.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ad.dismiss();
                    items = Sorty.sortLH(items);
                    for(int k = 0; k < items.size(); k++)
                    {
                        list.set(k, items.get(k).getName());
                    }
                    adapter.notifyDataSetChanged();
                }
            });
            final Button numDown = new Button(ListMain.this);
            numDown.setText("Sort 3 2 1");
            numDown.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ad.dismiss();
                    items = Sorty.sortHL(items);
                    for(int k = 0; k < items.size(); k++)
                    {
                        list.set(k, items.get(k).getName());
                    }
                    adapter.notifyDataSetChanged();
                }
            });
            LinearLayout lay = new LinearLayout(ListMain.this);
            lay.setOrientation(LinearLayout.VERTICAL);
            lay.addView(az);
            lay.addView(za);
            lay.addView(numUp);
            lay.addView(numDown);
            br.setView(lay);

            ad = br.create();
            ad = br.show(); */
//        }
    }
//WOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
// Hell fucking yes it worked.
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

                    Item i = new Item(name.getText().toString());
                    if (!TextUtils.isEmpty(amount.getText().toString()))
                        i.setAmount(Integer.parseInt(amount.getText().toString()));
                    db.addItem(i);
                    db.getItemAmount(i.getName());
                    db.databaseToString(list);
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

/*                    LinearLayout lay = new LinearLayout(ListMain.this);
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
                                        items.get(curSize).setLow(Integer.parseInt(setLow.getText()
                                                .toString()));
                                    if (!TextUtils.isEmpty(type.getText().toString()))
                                        items.get(curSize).setType(type.getText().toString());
                                    if (!TextUtils.isEmpty(measurement.getText().toString()))
                                        items.get(curSize).setType(measurement.getText().toString());
                                    curSize++;

                                }
                            });
                    ad = br.create();
                    ad = br.show(); */

                }
            });
            ad = br.create();
            ad = br.show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed()
    {
        Toast toast = Toast.makeText(getApplicationContext(), "There is no going back now:)", Toast.LENGTH_SHORT);
        toast.show();
    }
}

