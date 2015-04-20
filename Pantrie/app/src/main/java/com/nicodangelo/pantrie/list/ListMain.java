package com.nicodangelo.pantrie.list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.SystemClock;
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
import android.widget.TextView;
import android.widget.Toast;

import com.nicodangelo.pantrie.R;
import com.nicodangelo.pantrie.database.DBHandler;
import com.nicodangelo.pantrie.item.Item;
import com.nicodangelo.pantrie.item.ItemController;
import com.nicodangelo.pantrie.util.Settings;

import java.util.ArrayList;
import java.util.logging.Handler;

//whole or partial???

public class ListMain extends ActionBarActivity {
    ListView lv;
    AlertDialog ad;
    AlertDialog.Builder br;
    DBHandler db;
    SimpleCursorAdapter adapter;
    Cursor cursor;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_list_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("List");
        db = new DBHandler(this);
        db.open();
        lv = (ListView) findViewById(R.id.listView);
        updateListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int tempId = (int) id;
                AlertDialog.Builder ab = new AlertDialog.Builder(ListMain.this)
                        .setTitle("Edit Pantrie?")
                        .setNeutralButton("Edit Item", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                br = new AlertDialog.Builder(ListMain.this);

                                final TextView tempName = (TextView) findViewById(R.id.itemNameTextView);
                                final TextView tempAmn = (TextView) findViewById(R.id.itemAmountTextView);
                                final TextView tempLowAmn = (TextView) findViewById(R.id.itemLowAmountTextView);

                                br.setTitle(tempName.getText());

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
                                br.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        cursor = db.getRow("'" + tempName.getText().toString() + "'");
                                        int amn = Integer.parseInt(tempAmn.getText().toString());
                                        int low = Integer.parseInt(tempLowAmn.getText().toString());
                                        if (!TextUtils.isEmpty(one.getText().toString()))
                                            amn = Integer.parseInt(one.getText().toString());
                                        if (!TextUtils.isEmpty(two.getText().toString()))
                                            low = Integer.parseInt(two.getText().toString());
                                        db.updateRow(tempId, tempName.getText().toString(), amn, low);
                                        updateListView();
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
                        });
                ab.create();
                ab.show();
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ListMain.this)
                        .setTitle("Delete?")
                        .setMessage("Are you sure?\nThis can't be undone.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TextView temp = (TextView) findViewById(R.id.itemNameTextView);
                                db.deleteRow("'" + temp.getText().toString() + "'");
                                updateListView();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                ab.create();
                ab.show();
                return true;
            }
        });
    }

    public void changeSort(View view) {
        String no = "Fuck ya chicken strips";
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(ListMain.this, Settings.class);
            startActivity(i);
            return true;
        } else if (id == R.id.action_add_item) {
            br = new AlertDialog.Builder(ListMain.this);
            br.setTitle("Create New Item: All Fields Required")
                    .setCancelable(false);
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
            br.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Item i = new Item(name.getText().toString());
                    i.setAmount(Integer.parseInt(amount.getText().toString()));
                    i.setLow(Integer.parseInt(lowAmount.getText().toString()));
                    db.insertRow(i.getName(), i.getAmount(), i.getLow());
                    updateListView();
                    ad.dismiss();
                }
            });
            ad = br.create();
            ad = br.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateListView() {
        cursor = db.getAllRows();
        String[] fromFieldNames = new String[]{db.KEY_NAME, db.KEY_AMOUNT, db.KEY_LOWAMOUNT};
        int[] viewIds = new int[]{R.id.itemNameTextView, R.id.itemAmountTextView, R.id.itemLowAmountTextView};
        adapter = new SimpleCursorAdapter(getBaseContext(), R.layout.activity_list_adapter, cursor, fromFieldNames, viewIds, 0);
        lv.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
class ClickHandler implements AdapterView.OnItemClickListener {

    private boolean nonDoubleClick = true;
    private long firstClickTime = 0L;
    private final int DOUBLE_CLICK_TIMEOUT = 200;//ViewConfiguration.getDoubleTapTimeout();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        // @TODO check and catch the double click event
        synchronized(ClickHandler.this)
        {
            if(firstClickTime == 0)
            {
                firstClickTime = SystemClock.elapsedRealtime();
                nonDoubleClick = true;
            }
            else
            {
                long deltaTime = SystemClock.elapsedRealtime() - firstClickTime;
                firstClickTime = 0;
                if(deltaTime < DOUBLE_CLICK_TIMEOUT)
                {
                    nonDoubleClick = false;
                    this.onItemDoubleClick(parent, view, position, id);
                    return;
                }
            }

            view.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    if(nonDoubleClick)
                    {
                        // @TODO add your logic for single click event
                        //
                    }
                }
            }, DOUBLE_CLICK_TIMEOUT);
        }
    }

    public void onItemDoubleClick(AdapterView<?> parent, View view, int position, long id) {
        // @TODO override this method with your own logic
    }
}

