package sg.edu.rp.c346.id20004713.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    EditText etInput;
    Button btnAdd;
    Button btnClear;
    ListView lvOutput;
    Spinner spnrMode;
    Button btnDelete;

    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = findViewById(R.id.etInput);
        btnAdd = findViewById(R.id.btnAdd);
        btnClear = findViewById(R.id.btnClear);
        lvOutput = findViewById(R.id.lvOutput);
        btnDelete = findViewById(R.id.btnDel);
        spnrMode = findViewById(R.id.spnrMode);

        list = new ArrayList<String>();

        btnDelete.setEnabled(false);

        ArrayAdapter show  = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvOutput.setAdapter(show);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check(etInput)){
                    String input = etInput.getText().toString();
                    list.add(input);
                    show.notifyDataSetChanged();
                    etInput.setText("");
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.Wraning_enter_event), Toast.LENGTH_SHORT).show(); // To show a warning message
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                show.notifyDataSetChanged();
                etInput.setText("");
                Toast.makeText(MainActivity.this, getString(R.string.Wraning_Cleared), Toast.LENGTH_SHORT).show(); // To show a warning message
            }
        });

        spnrMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        btnAdd.setEnabled(true);
                        btnDelete.setEnabled(false);
                        etInput.setHint("Enter a Event to Add");
                        break;
                    case 1:
                        btnAdd.setEnabled(false);
                        btnDelete.setEnabled(true);
                        etInput.setHint("Enter a position to Delete");
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIndex(etInput) && list.size() > 0){
                    int input = Integer.parseInt(etInput.getText().toString());
                    list.remove(input - 1);
                    show.notifyDataSetChanged();
                    etInput.setText("");
                } else if (! (list.size() > 0)){
                    Toast.makeText(MainActivity.this, getString(R.string.Warning_nothing_to_remove), Toast.LENGTH_SHORT).show(); // To show a warning message
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.Warning_number), Toast.LENGTH_SHORT).show(); // To show a warning message
                }
            }
        });

        // _____________________________________________ OWN Enhancement __________________________________________________________
        lvOutput.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //when in deleting mode. user can click on a event and it add the position to Edit text view
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (btnDelete.isClickable()){
                    etInput.setText("" + (position + 1));
                }
            }
        });
    }

    private boolean check (EditText input){
        if(input.getText().toString().trim().length() > 0){
            return true;
        } else {
            return false;
        }
    }

    private boolean checkIndex (EditText input){
        if(input.getText().toString().trim().length() > 0){  //ensure user enter number within range
            int intInput = Integer.parseInt(input.getText().toString());

            if (intInput > 0 && intInput <= list.size()){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}