package com.binary.binatext;

import androidx.appcompat.app.AppCompatActivity;

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

    String[] items =  {"Text to Binary","Binary to Text"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    TextView input, input2, output;
    EditText inputEditText;
    Button convert, copy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);
        input = findViewById(R.id.input);
        input2 = findViewById(R.id.input2);
        inputEditText = findViewById(R.id.userInput);
        output = findViewById(R.id.output);
        convert = findViewById(R.id.convertInput);
        copy = findViewById(R.id.copyResult);

        PythonRun();

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.equals("Text to Binary")){
                    input.setText("Text");
                    input2.setText("Binary");
                    ConvertPython("Text");
                }if (item.equals("Binary to Text")){
                    input.setText("Binary");
                    input2.setText("Text");
                    ConvertPython("Binary");
                }else{
                    ConvertPython("None");
                }

            }
        });
    }


    private void PythonRun() {
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }

    private void ConvertPython(String value) {
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    try{
                        Python pyInstance = Python.getInstance();
                        PyObject pythonScript = pyInstance.getModule("script");
                        if (value.equals("Binary")){
                            PyObject pythonValue = pythonScript.callAttr("decipher",inputEditText.getText().toString());
                            output.setText(pythonValue.toString());
                        }else{
                            PyObject pythonValue = pythonScript.callAttr("cypher",inputEditText.getText().toString());
                            output.setText(pythonValue.toString());
                        }
                    }catch (Exception e){
                        output.setText(e.toString());
                    }
            }
        });
    }



}
/*
public class MainActivity extends AppCompatActivity {
    TextView outPut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outPut = findViewById(R.id.output);
        PythonRun();
    }

    private void PythonRun() {
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        PythonCode();
    }

    private void PythonCode() {
        try{
            //PyObject obj = pyObject.callAttr("main"); passing output
            //outPut.setText(obj.toString()); getting output
            Python pyInstance = Python.getInstance();
            PyObject pythonScript = pyInstance.getModule("script");
            //PyObject pythonValue = pythonScript.callAttr("main");
            //outPut.setText(pythonValue.toString());
         }catch (Exception e){
            //outPut.setText(e.toString());
        }
        }


}*/
