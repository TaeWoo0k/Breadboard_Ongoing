package com.ice.project_0513_1;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

class DBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "mycontacts.db";
    private static final int DATABASE_VERSION = 2;

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE contacts(_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,tel TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
}

public class MainActivity extends AppCompatActivity {

    DBHelper helper;
    SQLiteDatabase db;
    EditText edit_name, edit_tel;
    TextView tv1;
    Button btn,btn123;
    int a,b;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DBHelper(this);
        try {
            db = helper.getWritableDatabase();
        }catch (SQLException ex){
            db=helper.getReadableDatabase();
        }
        edit_name = (EditText) findViewById(R.id.edtname);
        edit_tel = (EditText)findViewById(R.id.edtnum);
        btn = (Button)findViewById(R.id.btn);
        btn123 = (Button)findViewById(R.id.btn123);
        tv1 = (TextView)findViewById(R.id.tv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert(v);
            }
        });
        tv1.setText("123");
        btn123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int q = Integer.parseInt(tv1.getText().toString());
               q+=123;
               tv1.setText(Integer.toString(q));
            }
        });
    }

    public void insert(View target){
        String name = edit_name.getText().toString();
        String tel = edit_tel.getText().toString();
        db.execSQL("INSERT INTO contacts VALUES (null,'"+name+"','"+tel+"');");
        Toast.makeText(getApplicationContext(),"성공적으로 추가되었음",Toast.LENGTH_SHORT).show();
        edit_tel.setText("");
        edit_name.setText("");
    }
    public void insert1(View target){
        int name=0,tel=0;
        db.execSQL("INSERT INTO contacts VALUES (null,'"+name+"','"+tel+"');");
        Toast.makeText(getApplicationContext(),"성공적으로 추가되었음",Toast.LENGTH_SHORT).show();
        edit_tel.setText("");
        edit_name.setText("");
    }
    public void search(View target){        //이름 입력 번호 출력
        String name = edit_name.getText().toString();
        Cursor cursor;
        cursor = db.rawQuery("SELECT name, tel FROM contacts WHERE name ='"+name+"';",null);

        while(cursor.moveToNext()){
            String tel = cursor.getString(1);
            edit_tel.setText(tel);
        }
    }
    public void search1(View target){   //번호 입력 이름 출력
        String tel = edit_tel.getText().toString();
        Cursor cursor;
        cursor = db.rawQuery("SELECT tel, name FROM contacts WHERE tel ='"+ tel +"';",null);

        while(cursor.moveToNext()){
            String name = cursor.getString(1);
            edit_name.setText(name);` `
        }
    }

}

