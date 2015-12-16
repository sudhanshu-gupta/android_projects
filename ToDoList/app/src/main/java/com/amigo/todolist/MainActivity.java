package com.amigo.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView toDoTextView;
    ArrayList<String> toDoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toDoTextView = (TextView) findViewById(R.id.toDoTextView);
        if(savedInstanceState != null)
            toDoItems = savedInstanceState.getStringArrayList("ITEMS");
        else
            toDoItems = new ArrayList<>();
    }

    private void updateUI() {
        StringBuilder builder = new StringBuilder();
        for(String str:toDoItems)
            builder.append(str).append("\n");
        toDoTextView.setText(builder.toString());
    }

    public void addToDoList(View view) {
        Intent intent = new Intent(this, AddToDoActivity.class);
        startActivityForResult(intent, 102);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 102 && resultCode == RESULT_OK) {
            Log.i("INTENT DATA", data.getStringExtra("TO_DO_ITEM"));
            if(data != null) {
                String toDoItem = data.getStringExtra("TO_DO_ITEM");

                //add the item to list
                toDoItems.add(toDoItem);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("ITEMS", toDoItems);
    }
}
