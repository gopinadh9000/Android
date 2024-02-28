package com.example.todolist.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.adapters.TodoAdapter;
import com.example.todolist.database.AppSharedPreferences;
import com.example.todolist.model.TodoItem;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TodoItem> items;
    private TodoAdapter itemsAdapter;
    private RecyclerView recyclerView;
    private Button button;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;


        recyclerView = findViewById(R.id.rv_todo_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

        items = AppSharedPreferences.getInstance(context).getTodoListItems();

        itemsAdapter = new TodoAdapter(this, items);
        recyclerView.setAdapter(itemsAdapter);

    }

    private void addItem(View view) {
        EditText input = findViewById(R.id.editText2);
        String itemText = input.getText().toString();
        
        if (!(itemText.equals(""))) {
            long id = generateIdFromTimestamp();
            TodoItem item = new TodoItem(id, itemText, false);
            itemsAdapter.addItem(item);
            AppSharedPreferences.getInstance(context).saveHashMap(id,item);
            input.setText("");

        } else {
            Toast.makeText(getApplicationContext(), "Please enter text...", Toast.LENGTH_LONG).show();
        }
    }

    public static long generateIdFromTimestamp() {
        // Get current timestamp in milliseconds
        // Use timestamp as the ID
        return new Date().getTime();
    }
}
