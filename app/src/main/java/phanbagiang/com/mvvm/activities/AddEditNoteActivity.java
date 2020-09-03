package phanbagiang.com.mvvm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.appbar.MaterialToolbar;

import phanbagiang.com.mvvm.R;

public class AddEditNoteActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private EditText edtTitle, edtDes;
    private NumberPicker numberPicker;
    private int currentBackgroundColor = 0xffffffff;
    private int currentTextColor = 0x00000000;
    private CardView view;
    Button button, btnChangeTextColor;
    TextView text_example;

    private static final String TAG = "AddEditNoteActivity";

    public static final String EXTRA_TITLE="phanbagiang.com.title";
    public static final String EXTRA_DES="phanbagiang.com.des";
    public static final String EXTRA_PRO="phanbagiang.com.pro";
    public static final String EXTRA_COLOR="phanbagiang.com.color";
    public static final String EXTRA_TEXT_COLOR="phanbagiang.com.color.text";
    public static final String EXTRA_ID="phanbagiang.com.id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        toolbar=findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        picolor();
        addControls();

    }
    private void picolor(){
        text_example=findViewById(R.id.text_example);
        view=findViewById(R.id.view_color);
        button=findViewById(R.id.btn_change_bg_color);
        btnChangeTextColor=findViewById(R.id.btn_change_text_color);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = AddEditNoteActivity.this;

                ColorPickerDialogBuilder
                        .with(context)
                        .setTitle(R.string.choose_color)
                        .initialColor(currentBackgroundColor)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorChangedListener(new OnColorChangedListener() {
                            @Override
                            public void onColorChanged(int selectedColor) {
                                // Handle on color change
                                Log.d("ColorPicker", "onColorChanged: 0x" + Integer.toHexString(selectedColor));
                                Log.d("ColorPicker", "onColorChanged1: " +selectedColor);
                            }
                        })
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                //toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                            }
                        })
                        .setPositiveButton(R.string.choose, new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                //changeBackgroundColor(selectedColor);
                                currentBackgroundColor=selectedColor;
                                view.setCardBackgroundColor(currentBackgroundColor);
//                                if (allColors != null) {
//                                    StringBuilder sb = null;
//
////                                    for (Integer color : allColors) {
////                                        if (color == null)
////                                            continue;
////                                        if (sb == null)
////                                            sb = new StringBuilder("Color List:");
////                                        sb.append("\r\n#" + Integer.toHexString(color).toUpperCase());
////                                    }
////
////                                    if (sb != null)
////                                        Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
//                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .showColorEdit(true)
                        .setColorEditTextColor(ContextCompat.getColor(AddEditNoteActivity.this, android.R.color.holo_blue_bright))
                        .build()
                        .show();
            }
        });
        btnChangeTextColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = AddEditNoteActivity.this;

                ColorPickerDialogBuilder
                        .with(context)
                        .setTitle(R.string.choose_color)
                        .initialColor(currentBackgroundColor)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorChangedListener(new OnColorChangedListener() {
                            @Override
                            public void onColorChanged(int selectedColor) {
                                // Handle on color change
                                Log.d("ColorPicker", "onColorChanged: 0x" + Integer.toHexString(selectedColor));
                            }
                        })
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                //toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                            }
                        })
                        .setPositiveButton(R.string.choose, new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                //changeBackgroundColor(selectedColor);
                                currentTextColor=selectedColor;
                                text_example.setTextColor(currentTextColor);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .showColorEdit(true)
                        .setColorEditTextColor(ContextCompat.getColor(AddEditNoteActivity.this, android.R.color.holo_blue_bright))
                        .build()
                        .show();
            }
        });
    }
    private void addControls(){

        edtDes=findViewById(R.id.edtDes);
        edtTitle=findViewById(R.id.edtTitle);
        numberPicker=findViewById(R.id.number_picker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(20);

        Intent intent=getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle(R.string.update_note);
            edtDes.setText(intent.getStringExtra(EXTRA_DES));
            edtTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRO,1));
            currentBackgroundColor=intent.getIntExtra(EXTRA_COLOR,0xffffffff);
            currentTextColor=intent.getIntExtra(EXTRA_TEXT_COLOR,0x00000000);
            view.setCardBackgroundColor(currentBackgroundColor);
            text_example.setTextColor(currentTextColor);


    }
        else{
            setTitle(R.string.add_note);
        }
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
            Toast.makeText(this, R.string.alert, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent=new Intent(AddEditNoteActivity.this,MainActivity.class);
        intent.putExtra(EXTRA_TITLE,title);
        intent.putExtra(EXTRA_DES,des);
        intent.putExtra(EXTRA_PRO,pro);
        intent.putExtra(EXTRA_COLOR,currentBackgroundColor);
        intent.putExtra(EXTRA_TEXT_COLOR,currentTextColor);
        int id= getIntent().getIntExtra(EXTRA_ID,-1);

        if(id!=-1){
            intent.putExtra(EXTRA_ID,id);
            Log.d(TAG, "allo: "+id);
        }
        setResult(RESULT_OK,intent);
        finish();
    }
}