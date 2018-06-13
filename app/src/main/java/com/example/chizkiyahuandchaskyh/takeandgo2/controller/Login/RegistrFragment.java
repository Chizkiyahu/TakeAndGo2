package com.example.chizkiyahuandchaskyh.takeandgo2.controller.Login;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Customer;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.utils.Constants;


public class RegistrFragment extends Fragment {
    EditText  idView, firstNameView, lastNameView, phoneView, emailView, passView;
    Button registerButton;
    DataSource dataSource = BackendFactory.getDataSource();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_registr, container, false);
        registerButton = view.findViewById(R.id.register_button);
        idView =  view.findViewById(R.id.customer_add_id);
        firstNameView = view.findViewById(R.id.customer_add_first_name);
        lastNameView = view.findViewById(R.id.customer_add_last_name);
        phoneView = view.findViewById(R.id.customer_add_phone);
        emailView = view.findViewById(R.id.customer_add_email);
        passView = view.findViewById(R.id.customer_add_pass);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                final String user = emailView.getText().toString();
                final String pass =  passView.getText().toString();
                try {
                    if (!isEmailValid(user)){
                        throw new Exception("Please enter a valid email address");
                    }
                    if(!isPassStrong(pass)){
                        throw new Exception("Password is too short Please enter at least 8 characters");
                    }
                    final Customer  customer = new Customer(lastNameView.getText().toString(),
                                firstNameView.getText().toString(),
                                Integer.parseInt( idView.getText().toString()),
                                phoneView.getText().toString(),
                                emailView.getText().toString(),
                                passView.getText().toString());

                    new AsyncTask<Customer,String,Void>(){
                        @Override
                        protected void onPostExecute(Void aVoid) {

                        }

                        @Override
                        protected void onProgressUpdate(String... values) {
                            Toast.makeText(getContext(), values[0], Toast.LENGTH_LONG).show();
                        }


                        @Override
                        protected Void doInBackground(Customer... customers) {

                            try {
                                if(dataSource.checkUserIsFree(user)){
                                    dataSource.addCustomer(customer);
                                    new TryUserPass(getView()).execute(user, pass);
                                }else {
                                    publishProgress(getContext().getString(R.string.error_the_user_already_exsit));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(Constants.Log.TAG,e.getMessage());
                            }
                            return null;
                        }
                    }.execute(customer);
                }catch (Exception e){
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    Log.e(Constants.Log.TAG,e.getMessage());
                }
            }
        });
        return view;
    }




    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isPassStrong(String pass) {
        return pass.length() >= 8;
    }

}
