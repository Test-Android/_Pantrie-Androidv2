package com.nicodangelo.pantrie.list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.nicodangelo.pantrie.R;
import com.nicodangelo.pantrie.database.DBHandler;
import com.nicodangelo.pantrie.item.Item;
import com.nicodangelo.pantrie.item.ItemController;
import com.nicodangelo.pantrie.util.Settings;

import java.util.ArrayList;
import java.util.logging.Handler;

//whole or partial???

public class ListMain extends ActionBarActivity
{
    ArrayList<String> list = new ArrayList<String>();
    int curSize = 0;
    ListView lv;
    AlertDialog ad;
    AlertDialog.Builder br;
    DBHandler db;
    SimpleCursorAdapter adapter;
    Cursor cursor;

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_list_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("List");
        db = new DBHandler(this);
        db.open();
        lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            System.out.print("asu;dfhasldufhasdlufhalsifhuda");
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                for(int k = 0; k < curSize; k++)
                {
                    if(k == position)
                    {
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
//                                                    db.setItemAmount(list.get(position), Integer.parseInt(one.getText().toString()));
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
                        ab.create();
                        ab.show();
                    }
                }
            }
        });
        updateListView();
    }

    public void changeSort(View view)
    {
        String no = "Fuck ya chicken strips";
    }

    @Override
    protected void onPause()
    {
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
            final EditText lowAmount = new EditText(ListMain.this);
            lowAmount.setHint("Low Amount Warning");

            name.setInputType(InputType.TYPE_CLASS_TEXT);
            amount.setInputType(InputType.TYPE_CLASS_NUMBER);
            lowAmount.setInputType(InputType.TYPE_CLASS_NUMBER);

            LinearLayout lay = new LinearLayout(ListMain.this);
            lay.setOrientation(LinearLayout.VERTICAL);
            lay.addView(name);
            lay.addView(amount);
            lay.addView(lowAmount);
            br.setView(lay);
            br.setPositiveButton("Ok", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int whichButton)
                {

                    if(!(TextUtils.isEmpty(name.getText().toString()) && TextUtils.isEmpty(amount.getText().toString()) && TextUtils.isEmpty(lowAmount.getText().toString())))
                    {
                        Item i = new Item(name.getText().toString());
                        i.setAmount(Integer.parseInt(amount.getText().toString()));
                        i.setLow(Integer.parseInt(lowAmount.getText().toString()));
                        db.insertRow(i.getName(),i.getAmount(),i.getLow());
                        updateListView();
                        ad.dismiss();
                    }
                    else
                    {
                        Toast toast = new Toast(ListMain.this);
                        toast.setText("Please Fill All Spaces");
                        toast.show();

                    }

            }
            });
            ad = br.create();
            ad = br.show();
        }
        return super.onOptionsItemSelected(item);
    }
    private void updateListView()
    {
        cursor = db.getAllRows();
        String[] fromFieldNames = new String[]{db.KEY_NAME,db.KEY_AMOUNT,db.KEY_LOWAMOUNT};
        int[] viewIds = new int[]{R.id.itemNameTextView, R.id.itemAmountTextView,R.id.itemLowAmountTextView};
        adapter = new SimpleCursorAdapter(getBaseContext(), R.layout.activity_list_adapter, cursor, fromFieldNames, viewIds, 0);
        lv.setAdapter(adapter);

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        db.close();
    }
}

