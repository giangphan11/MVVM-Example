package phanbagiang.com.mvvm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import phanbagiang.com.mvvm.R;

public class AddNoteActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private EditText edtTitle, edtDes;
    private NumberPicker numberPicker;

    public static final String EXTRA_TITLE="phanbagiang.com.title";
    public static final String EXTRA_DES="phanbagiang.com.des";
    public static final String EXTRA_PRO="phanbagiang.com.pro";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        toolbar=findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add note");

        addControls();
        addEvents();
    }
    private void addEvents(){

    }
    private void addControls(){
        edtDes=findViewById(R.id.edtDes);
        edtTitle=findViewById(R.id.edtTitle);
        numberPicker=findViewById(R.id.number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnu_save_note:
                saveNote();
                return true;
        }
        return false;
    }
    private void saveNote(){
        String title= edtTitle.getText().toString();
        String des= edtDes.getText().toString();
        int pro=numberPicker.getValue();
        if(title.trim().isEmpty()||des.trim().isEmpty()){
            Toast.makeText(this, "please input all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent=new Intent(AddNoteActivity.this,MainActivity.class);
        intent.putExtra(EXTRA_TITLE,title);
        intent.putExtra(EXTRA_DES,des);
        intent.putExtra(EXTRA_PRO,pro);
        setResult(114,intent);
        finish();
    }
}