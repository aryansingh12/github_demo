package com.example.aryansingh.github_demo;

import android.icu.lang.UScript;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    EditText searchEditText;
    ListView listView;
    ProgressBar progressBar;

    ArrayList<String> names = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchEditText);
        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);

        // the names list should only have names
        for(int i=0;i<users.size();i++){
            names.add(users.get(i).getLogin());
        }

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,names);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

    }

    public void search(View view){

        final String name = searchEditText.getText().toString();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/users/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        UserService service = retrofit.create(UserService.class);
        Call<ArrayList<User>> call = service.getUsers(name);

        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {

                names.clear();
                ArrayList<User> all_users = response.body();
                for(int i=0;i<all_users.size();i++){
                    if(all_users.get(i).getLogin().equals(name)){
                        users.add(all_users.get(i));
                    }
                }

                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // shows the details of the user when clicked
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
