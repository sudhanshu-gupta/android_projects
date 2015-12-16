package com.amigo.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddToDoActivity extends AppCompatActivity {

    EditText toDoItemEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        toDoItemEditText = (EditText) findViewById(R.id.addToDoItemEditText);
    }

    public void add(View view) {
        String toDoItem = toDoItemEditText.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("TO_DO_ITEM", toDoItem);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}
