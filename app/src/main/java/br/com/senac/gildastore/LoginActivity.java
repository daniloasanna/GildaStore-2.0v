package br.com.senac.gildastore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText cpfLogin,senhaLogin;
    ProgressBar loading;
    static String URL_LOGIN = "https://sitezasso.000webhostapp.com/gildastore/teste/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin =findViewById(R.id.buttonLogin);
        cpfLogin =findViewById(R.id.loginCpf);
        senhaLogin =findViewById(R.id.loginSenha);
        loading = findViewById(R.id.loading);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginc = cpfLogin.getText().toString().trim();
                String senhac = senhaLogin.getText().toString().trim();

                if(!loginc.isEmpty() || !senhac.isEmpty()){
                    Login();
                }else {
                    cpfLogin.setError("Por favor insira o CPF");
                    senhaLogin.setError("Por favor insira a senha");
                }
            }
        });
    }

    public void Login(){
        StringRequest request = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){

                        if(response.contains("1")){

                            Intent iniciar = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(iniciar);
                            Toast.makeText(getApplicationContext(),"Bem Vindo!",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Usuário ou Senha incorretos!",Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("cpfUsuario",cpfLogin.getText().toString());
                params.put("senhaUsuario",senhaLogin.getText().toString());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
