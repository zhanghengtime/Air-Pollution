package com.example.tianqi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tianqi.utils.DBAdapter;
import com.example.tianqi.utils.User;

public class AdminActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    private DBAdapter dbAdepter ;
    private EditText usernameText;
    private EditText userpwdText;
    private EditText idEntry;
    private TextView labelView;
    private TextView displayView;
    private Button exitbtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        exitbtn = (Button) findViewById(R.id.btn_exit2);
        usernameText = (EditText)findViewById(R.id.username);
        userpwdText = (EditText)findViewById(R.id.userpwd);
        idEntry = (EditText)findViewById(R.id.id_entry);
        labelView = (TextView)findViewById(R.id.label);
        displayView = (TextView)findViewById(R.id.display);
        Button addButton = (Button)findViewById(R.id.add);
        Button queryAllButton = (Button)findViewById(R.id.query_all);
        Button clearButton = (Button)findViewById(R.id.clear);
        Button deleteAllButton = (Button)findViewById(R.id.delete_all);
        Button queryButton = (Button)findViewById(R.id.query);
        Button deleteButton = (Button)findViewById(R.id.delete);
        Button updateButton = (Button)findViewById(R.id.update);
        addButton.setOnClickListener(addButtonListener);
        queryAllButton.setOnClickListener(queryAllButtonListener);
        clearButton.setOnClickListener(clearButtonListener);
        deleteAllButton.setOnClickListener(deleteAllButtonListener);
        queryButton.setOnClickListener(queryButtonListener);
        deleteButton.setOnClickListener(deleteButtonListener);
        updateButton.setOnClickListener(updateButtonListener);
        dbAdepter = new DBAdapter(this);
        dbAdepter.open();
        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    View.OnClickListener addButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            User user = new User();
            user.username = usernameText.getText().toString();
            user.userpwd = userpwdText.getText().toString();
            long colunm = dbAdepter.insert(user);
            if (colunm == -1 ){
                labelView.setText("添加过程错误！");
            } else {
                labelView.setText("成功添加数据，ID："+String.valueOf(colunm));
            }
        }
    };
    View.OnClickListener queryAllButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            User[] users = dbAdepter.queryAllData();
            if (users == null){
                labelView.setText("数据库中没有数据");
                return;
            }
            labelView.setText("数据库：");
            String msg = "";
            for (int i = 0 ; i<users.length; i++){
                msg += users[i].toString()+"\n";
            }
            displayView.setText(msg);
        }
    };
    View.OnClickListener clearButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            displayView.setText("");
        }
    };
    View.OnClickListener deleteAllButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dbAdepter.deleteAllData();
            String msg = "数据全部删除";
            labelView.setText(msg);
        }
    };
    View.OnClickListener queryButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = Integer.parseInt(idEntry.getText().toString());
            User[] users = dbAdepter.queryOneData(id);
            if (users == null){
                labelView.setText("数据库中没有ID为"+String.valueOf(id)+"的数 据");
                return;
            }
            labelView.setText("数据库：");
            displayView.setText(users[0].toString());
        }
    };
    View.OnClickListener deleteButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            long id = Integer.parseInt(idEntry.getText().toString());
            long result = dbAdepter.deleteOneData(id);
            String msg = "删除ID为"+idEntry.getText().toString()+"的数据" +
                    (result>0?"成功":"失败");
            labelView.setText(msg);
        }
    };
    View.OnClickListener updateButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            User user = new User();
            user.username = usernameText.getText().toString();
            user.userpwd = userpwdText.getText().toString();
            long id = Integer.parseInt(idEntry.getText().toString());
            long count = dbAdepter.updateOneData(id, user);
            if (count == -1 ){
                labelView.setText("更新错误！");
            } else {
                labelView.setText("更新成功，更新数据"+String.valueOf(count)+" 条");
            }
        }
    };
}