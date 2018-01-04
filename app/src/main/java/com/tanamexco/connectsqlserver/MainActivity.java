package com.tanamexco.connectsqlserver;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    EditText txtUserName, txtPassword, txtServer, txtDatabase;
    Button btnDangNhap, btnThoat;
    String usr, pass, ser, db;
    ConnectionSQLServer connectionSqlServer=new ConnectionSQLServer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

    }

    private void addEvents() {
    btnThoat.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr=txtUserName.getText().toString();
                pass=txtPassword.getText().toString();
                ser=txtServer.getText().toString();
                db=txtDatabase.getText().toString();
                DangNhap dangnhap=new DangNhap();
                dangnhap.execute("");
            }
        });
    }
    public class DangNhap extends AsyncTask<String,String,String>{
        String z = "";
        Boolean isDangNhap = false;
        String userid = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();

        @Override
        protected void onPreExecute() {
            //chua xu ly
        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(MainActivity.this,r,Toast.LENGTH_SHORT).show();
            Log.e("errr",r);
            /*if(isDangNhap) {
                Toast.makeText(MainActivity.this,"thanh cong",Toast.LENGTH_SHORT).show();

            }*/
        }

        @Override
        protected String doInBackground(String... params) {
            if(userid.trim().equals("")|| password.trim().equals(""))
                z = "Please enter User Id and Password";
            else
            {
                try
                {
                    Connection con =connectionSqlServer.CONN(usr,pass,ser,db);
                    if(con==null)
                    {z="Lỗi kết nối SQL Server";}
                    else
                    {
                        String query="Select * FROM tblDMNhanVien WHERE MaNV = '" +userid +"';";
                        Statement stmt=con.createStatement();
                        ResultSet rs=stmt.executeQuery(query);
                        if(rs.next())
                        {
                            z=rs.getString("TenNV");
                            isDangNhap=true;
                        }
                        else
                        {
                            z="Đăng nhập thất bại";
                            isDangNhap=false;
                        }
                    }
                }

                catch (Exception ex)
                {
                    z=ex.getMessage();
                    isDangNhap=false;
                }

            }
            return z;
        }
    }


    private void addControls() {
        txtUserName= (EditText) findViewById(R.id.txtUserName);
        txtPassword= (EditText) findViewById(R.id.txtPassword);
        txtServer= (EditText) findViewById(R.id.txtServer);
        txtDatabase= (EditText) findViewById(R.id.txtDatabase);
        btnDangNhap= (Button) findViewById(R.id.btnDangNhap);
        btnThoat= (Button) findViewById(R.id.btnThoat);

    }
}
