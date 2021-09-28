package com.binary.binatext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {

    String[] items = {"Text to Binary", "Binary to Text"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    TextView input, input2, output;
    EditText inputEditText;
    Button convert, copy, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FindViewByIdes();
        PythonRun();
        DropDownMenuOps();
        ConvertPython();
        ClearInput();
        CopyOutput();

    }

    private void FindViewByIdes() {

        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items);
        autoCompleteTxt.setAdapter(adapterItems);
        input = findViewById(R.id.input);
        input2 = findViewById(R.id.input2);
        inputEditText = findViewById(R.id.userInput);
        output = findViewById(R.id.output);
        convert = findViewById(R.id.convertInput);
        copy = findViewById(R.id.copyResult);
        clear = findViewById(R.id.inputClear);

    }

    private void PythonRun() {
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }

    private void DropDownMenuOps() {
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.equals("Text to Binary")) {
                    if (!input.getText().toString().equals("Text")) {
                        inputEditText.setText("");
                        output.setText("");
                    }
                    input.setText("Text");
                    input2.setText("Binary");
                } else if (item.equals("Binary to Text")) {
                    if (!input.getText().toString().equals("Binary")) {
                        inputEditText.setText("");
                        output.setText("");
                    }
                    input.setText("Binary");
                    input2.setText("Text");
                }

            }
        });
    }

    private void ConvertPython() {

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Input!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Python pyInstance = Python.getInstance();
                        PyObject pythonScript = pyInstance.getModule("script");
                        if (input.getText().toString().equals("Binary")) {
                            PyObject pythonValue = pythonScript.callAttr("decipher", inputEditText.getText().toString());
                            output.setText(pythonValue.toString());
                        } else if (input.getText().toString().equals("Text")) {
                            PyObject pythonValue = pythonScript.callAttr("cypher", inputEditText.getText().toString());
                            output.setText(pythonValue.toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "Select a Conversion!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        output.setText(e.toString());
                    }
                }

            }
        });

    }

    private void ClearInput() {

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Inputs to be Cleared!", Toast.LENGTH_SHORT).show();
                } else {
                    inputEditText.setText("");
                    output.setText("");
                }
            }
        });

    }

    private void CopyOutput() {

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!output.getText().toString().equals("")) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", output.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No Result Found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}