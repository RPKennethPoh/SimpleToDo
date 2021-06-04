package sg.edu.rp.c346.id20040654.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // declare variables
    Spinner spinChoice;
    EditText etTask, etIndex;
    Button btnAdd, btnDelete, btnClear;
    ListView lvTask;
    ArrayList<String> taskList, choiceList;
    ArrayAdapter<String> aaTask, aaChoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign variables
        spinChoice = findViewById(R.id.spinnerChoice);
        etTask = findViewById(R.id.editTextTask);
        etIndex = findViewById(R.id.editTextIndex);
        btnAdd = findViewById(R.id.buttonAdd);
        btnDelete = findViewById(R.id.buttonDelete);
        btnClear = findViewById(R.id.buttonClear);
        lvTask = findViewById(R.id.listViewTask);
        taskList = new ArrayList<String>();
        choiceList = new ArrayList<String>();

        choiceList.add("Add a task");
        choiceList.add("Remove a task");

        // creating array adapter
        aaTask = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, taskList);
        aaChoice = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, choiceList);

        // link arraylist with listview and spinner using adapters
        lvTask.setAdapter(aaTask);
        spinChoice.setAdapter(aaChoice);

        // enable context menu for the given view
        registerForContextMenu(lvTask);

        // add new task
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get user input
                String task = etTask.getText().toString().trim();
                // check if user's input is not empty
                if(!task.isEmpty()) {
                    int index = taskList.size();
                    taskList.add(task);
                    aaTask.notifyDataSetChanged();
                    etTask.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Enter a task.", Toast.LENGTH_SHORT).show();
                    etTask.setText("");
                }
            }
        });

        // delete existing task
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if arraylist is empty
                if(taskList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "There are no tasks to delete.", Toast.LENGTH_SHORT).show();
                    etIndex.setText("");
                } else {
                    int index;
                    // check if user's input is valid index
                    if(Integer.parseInt(etIndex.getText().toString()) >= taskList.size()) {
                        Toast.makeText(getApplicationContext(), "Invalid index.", Toast.LENGTH_SHORT).show();
                        etIndex.setText("");
                    } else {
                        index = Integer.parseInt(etIndex.getText().toString());
                        taskList.remove(index);
                        aaTask.notifyDataSetChanged();
                        etIndex.setText("");
                        Toast.makeText(getApplicationContext(), "Task deleted.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // clear all tasks
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if arraylist is empty
                if(taskList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "List is already empty.", Toast.LENGTH_SHORT).show();
                } else {
                    taskList.clear();
                    aaTask.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "List cleared.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // choosing add or delete task
        spinChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // if add task
                if(position == 0) {
                    etTask.setVisibility(View.VISIBLE);
                    etIndex.setVisibility(View.GONE);
                    btnAdd.setEnabled(true);
                    btnDelete.setEnabled(false);
                    btnAdd.setAlpha(1f);
                    btnDelete.setAlpha(.3f);
                // if delete task
                } else if (position == 1) {
                    etIndex.setVisibility(View.VISIBLE);
                    etTask.setVisibility(View.GONE);
                    btnDelete.setEnabled(true);
                    btnAdd.setEnabled(false);
                    btnDelete.setAlpha(1);
                    btnAdd.setAlpha(.3f);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        Log.v("context", "Context Menu has been created");

        // inflate menu / add items into menu
        getMenuInflater().inflate(R.menu.menu_context, menu);

    }

    public boolean onContextItemSelected(MenuItem item) {

        // idk, got code from google
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        // removes item used to open contextmenu from the arraylist
        taskList.remove(info.position);
        aaTask.notifyDataSetChanged();

        Toast.makeText(MainActivity.this, "Task completed!", Toast.LENGTH_SHORT).show();

        return super.onContextItemSelected(item); //pass menu item to the superclass implementation
    }
}