package com.example.tianqi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianqi.utils.MyDBOpenHelper;
import com.example.tianqi.utils.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminActivity extends AppCompatActivity {
    /** Called when the activity is first created. */
    private EditText usernameText;
    private EditText userpwdText;
    private EditText idEntry;
    private TextView labelView;
    private TextView displayView;
    private Button exitbtn;
    private static Handler handler=new Handler();
    public String msg="";
    public String msgs="";
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
        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    /**
     * 添加数据
     */
    View.OnClickListener addButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new  Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Connection conn = MyDBOpenHelper.getConn();
                        if(conn!=null){
                            Log.d("调试","连接成功");
                            Statement stmt = conn.createStatement();
                            String sql = "select * from user " + "where Username =\"" + usernameText.getText().toString() + "\"";
                            ResultSet rs = stmt.executeQuery(sql);
                            if(rs.next()) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "用户名已经存在！", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else
                            {
                                String sqls = "insert into user (Username, Password) values (\"" + usernameText.getText().toString() + "\" , \"" + userpwdText.getText().toString() +"\")";
                                stmt.executeUpdate(sqls);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        displayView.setText("成功添加数据!");
                                    }
                                });
                            }
                        }else{
                            Log.d("调试","连接失败");
                        }
                    }catch (SQLException e){
                        Log.i("debug",Log.getStackTraceString(e));
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayView.setText("添加过程错误！");
                            }
                        });
                    }
                }
            }).start();
        }
    };
    /**
     * 全部显示
     */
    View.OnClickListener queryAllButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new  Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        msg="";
                        Connection conn = MyDBOpenHelper.getConn();
                        if(conn!=null){
                            Log.d("调试","连接成功");
                            Statement stmt = conn.createStatement();
                            String sql = "select * from user where flag=0";
                            ResultSet rs = stmt.executeQuery(sql);
                            msg += "ID       用户名        密码 \n";
                            while(rs.next()) {
                               msg += rs.getString(4)+ "  ,  " + rs.getString(1)+"  ,  "+rs.getString(2)+"\n";
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(msg.equals("ID       用户名        密码 \n"))
                                    {
                                        displayView.setText("数据库中没有数据");
                                    }else {
                                        displayView.setText(msg);
                                    }
                                }
                            });
                        }else{
                            Log.d("调试","连接失败");
                        }
                    }catch (SQLException e){
                        Log.i("debug",Log.getStackTraceString(e));
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayView.setText("系统错误！");
                            }
                        });
                    }
                }
            }).start();
        }
    };
    /**
     * 清除显示
     */
    View.OnClickListener clearButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            msg="";
            msgs="";
            displayView.setText("");
        }
    };
    /**
     * 删除所有普通用户
     */
    View.OnClickListener deleteAllButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new  Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Connection conn = MyDBOpenHelper.getConn();
                        if(conn!=null){
                            Log.d("调试","连接成功");
                            Statement stmt = conn.createStatement();
                            String sql = "delete from user where flag=0";
                            stmt.executeUpdate(sql);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    displayView.setText("成功删除全部数据!");
                                }
                            });
                        }else{
                            Log.d("调试","连接失败");
                        }
                    }catch (SQLException e){
                        Log.i("debug",Log.getStackTraceString(e));
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayView.setText("系统错误！");
                            }
                        });
                    }
                }
            }).start();
        }
    };
    /**
     * ID查询
     */
    View.OnClickListener queryButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection conn = MyDBOpenHelper.getConn();
                        if (conn != null) {
                            msgs = "";
                            Log.d("调试", "连接成功");
                            Statement stmt = conn.createStatement();
                            String sql = "select * from user where Id=\"" + idEntry.getText().toString() + "\"";
                            ResultSet rs = stmt.executeQuery(sql);
                            if (rs.next()) {
                                msgs += rs.getString(1) + " , " + rs.getString(2) + "\n";
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        displayView.setText(msgs);
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        displayView.setText("数据库中没有ID为" + idEntry.getText().toString() + "的数据");
                                    }
                                });
                            }
                        } else {
                            Log.d("调试", "连接失败");
                        }
                    } catch (SQLException e) {
                        Log.i("debug", Log.getStackTraceString(e));
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayView.setText("系统错误！");
                            }
                        });
                    }
                }
            }).start();
        }
    };
    /**
     * ID删除
     */
    View.OnClickListener deleteButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection conn = MyDBOpenHelper.getConn();
                        if (conn != null) {
                            msgs = "";
                            Log.d("调试", "连接成功");
                            Statement stmt = conn.createStatement();
                            String sql = "delete from user where Id=\"" + idEntry.getText().toString() + "\"";
                            stmt.executeUpdate(sql);
                            handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        displayView.setText("数据库中ID为" + idEntry.getText().toString() + "的数据已删除!");
                                    }
                                });
                        } else {
                            Log.d("调试", "连接失败");
                        }
                    } catch (SQLException e) {
                        Log.i("debug", Log.getStackTraceString(e));
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayView.setText("系统错误！");
                            }
                        });
                    }
                }
            }).start();
        }
    };
    View.OnClickListener updateButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection conn = MyDBOpenHelper.getConn();
                        if (conn != null) {
                            msgs = "";
                            Log.d("调试", "连接成功");
                            Statement stmt = conn.createStatement();
                            String sql = "update user set Username=\"" + usernameText.getText().toString() + "\" , Password= \"" + userpwdText.getText().toString() + "\" where Id=\"" + idEntry.getText().toString() + "\"";
                            stmt.executeUpdate(sql);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    displayView.setText("数据库中ID为" + idEntry.getText().toString() + "的数据已更新!");
                                }
                            });
                        } else {
                            Log.d("调试", "连接失败");
                        }
                    } catch (SQLException e) {
                        Log.i("debug", Log.getStackTraceString(e));
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayView.setText("系统错误!");
                            }
                        });
                    }
                }
            }).start();
        }
    };
}