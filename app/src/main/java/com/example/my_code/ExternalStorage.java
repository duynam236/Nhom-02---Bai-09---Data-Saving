package com.example.my_code;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExternalStorage extends Activity implements View.OnClickListener {
    private String filename = "externalStorage.txt";
    private String filepath = "MyDirectory";
    File myExternalFile;

    EditText myInputText;
    TextView responseText;
    Button saveBtn, displayBtn;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.bai09_external_storage);

        myInputText = (EditText) findViewById(R.id.myInputText2); // Lấy view dữ liệu đầu vào
        responseText = (TextView) findViewById(R.id.responseText2); // Lấy view dữ liệu đầu ra
        saveBtn = (Button) findViewById(R.id.btnSave2); // Lấy nút lưu dữ liệu
        displayBtn = (Button) findViewById(R.id.btnDisplay2); // Lấy nút hiển thị dữ liệu
        saveBtn.setOnClickListener(this);
        displayBtn.setOnClickListener(this);

        // Kiểm tra xem thiết bị có bộ nhớ ngoài và sẵn sàng để đọc và ghi
        if (isExternalStorageReadable() || !isExternalStorageWritable()) // Không thể thao tác
            saveBtn.setEnabled(false); // Vô hiệu hoá nút lưu dữ liệu
        else
            // Tạo (Hoặc mở nếu đã tồn tại) file trong thư mục "MyDirectory"
            myExternalFile = new File(getExternalFilesDir(filepath), filename);
    }

    // Triển khai phương thức onClick từ lớp View
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSave2) { // Hành động của nút lưu
            try {
                // Mở file
                FileOutputStream fos = new FileOutputStream(myExternalFile); // Truyền vào tệp tin cần đọc

                // Ghi dữ liệu vào file
                fos.write(myInputText.getText().toString().getBytes());
                fos.close();

                // Xoá dữ liệu đầu vào khi ghi dữ liệu vào file thành công
                myInputText.setText("");

                // Hiện thông báo Toast
                Toast.makeText(this, "Ghi dữ liệu vào file " + filename + " thành công!!!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (view.getId() == R.id.btnDisplay2) { // Hành động của nút hiển thị
            try {
                // Đọc file
                FileInputStream fis = new FileInputStream(myExternalFile); // Truyền vào tệp tin cần đọc
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

    // Kiểm tra xem thiết bị có bộ nhớ ngoài chỉ để đọc hay không
    private static boolean isExternalStorageReadable() {
        String extStorageState = Environment.getExternalStorageState(); // Truy cập tài nguyên hệ thống và lấy ra tên trạng thái bộ nhớ ngoài
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState); // So sánh trạng thái
    }

    // Kiểm tra xem thiết bị có bộ nhớ ngoài đang sẵn sàng để đọc và ghi không
    private static boolean isExternalStorageWritable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }
}
