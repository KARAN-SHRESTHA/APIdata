package com.example.apidata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import model.Employee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class search extends AppCompatActivity {

    private final static String BASE_URL = "http://dummy.restapiexample.com/api/v1/";
    private EditText etSearch;
    private TextView tvList;
    private Button btnSearch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etSearch = findViewById(R.id.etSearch);
        tvList = findViewById(R.id.tvList);
        btnSearch1 = findViewById(R.id.btnSearch1);

        btnSearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadData();

            }
        });

    }

    private void loadData()
    {

        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

        API api = retrofit.create(API.class);

        Call<Employee> listcall = api.getEmployeeByID(Integer.parseInt(etSearch.getText().toString()));

        listcall.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {

                Toast.makeText(search.this,response.body().toString(), Toast.LENGTH_SHORT).show();
                String content ="";
                content += "ID: " + response.body().getId() + "\n";
                content += "Name: " + response.body().getEmployee_name() + "\n";
                content += "Age: " + response.body().getEmployee_age() + "\n";
                content += "Salary: " + response.body().getEmployee_salary() + "\n";

                tvList.setText(content);
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(search.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
