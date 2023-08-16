package com.example.my_code;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_code.R;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InternalStorage extends Activity implements View.OnClickListener {
    private String filename = "internalStorage.txt";
    private String filepath = "MyDirectory";
    File myInternalFile;

    EditText myInputText;
    TextView responseText;
    Button saveBtn, displayBtn;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.bai09_internal_storage);

        myInputText = (EditText) findViewById(R.id.myInputText); // Lấy view dữ liệu đầu vào
        responseText = (TextView) findViewById(R.id.responseText); // Lấy view dữ liệu đầu ra
        saveBtn = (Button) findViewById(R.id.btnSave); // Lấy nút lưu dữ liệu
        displayBtn = (Button) findViewById(R.id.btnDisplay); // Lấy nút hiển thị dữ liệu

        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext()); // Lấy context của ứng dụng

        // Tạo (Hoặc mở nếu đã tồn tại) đường dẫn của thư mục "MyDirectory", cài đặt chế độ MODE_PRIVATE cho thư mục này
        File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
//        File directory = contextWrapper.getDir(filepath, Context.MODE_APPEND);

        // Tạo (Hoặc là mở file nếu đã tồn tại) trong bộ nhớ có thư mục "MyDirectory"
        myInternalFile = new File(directory, filename);

        saveBtn.setOnClickListener(this);
        displayBtn.setOnClickListener(this);
    }

    // Triển khai phương thức onClick từ lớp View
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSave) { // Hành động của nút lưu
            try {
                // Mở file
                FileOutputStream fos = new FileOutputStream(myInternalFile); // Truyền vào tệp tin cần đọc

                // Ghi dữ liệu vào file
                fos.write(myInputText.getText().toString().getBytes());
                fos.close();
                myInputText.setText("");
                // Hiện thông báo Toast
                Toast.makeText(this, "Ghi dữ liệu vào file " + filename + " thành công!!!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (view.getId() == R.id.btnDisplay) { // Hành động của nút hiển thị
            try {
                // Đọc file
                FileInputStream fis = new FileInputStream(myInternalFile); // Truyền vào tệp tin cần đọc
                DataInputStream in = new DataInputStream(fis);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                // Đọc từng dòng
                String strLine;
                String myData = "";
                while ((strLine = br.readLine()) != null) {
                    myData = myData + "\r\n" + strLine;
                }
                in.close();

                // Hiển thị dữ liệu lên view
                responseText.setText(myData);

                // Hiện thông báo Toast
                Toast.makeText(this, "Đọc dữ liệu từ file " + filename + " thành công!!!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
