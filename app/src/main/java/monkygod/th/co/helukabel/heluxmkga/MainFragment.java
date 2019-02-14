package monkygod.th.co.helukabel.heluxmkga;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    } //Constructor


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Register Controller
        TextView textView = getView().findViewById(R.id.txtNewRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace Fragment

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentMainFragment, new RegisterFragment()).addToBackStack(null).commit();

            }
        });

        //  Login Controller
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);

                String user = userEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                MainAlert mainAlert = new MainAlert(getActivity());

                if (user.isEmpty() || password.isEmpty()) {
                    mainAlert.normalDialog("Have Space", "Please Fill Every Blank");

                } else {

                    Log.wtf("14Feb2019","loginwork");

                    try {
                        MainConstant mainConstant = new MainConstant();
                        GetUserWhereUserThread getUserWhereUserThread = new GetUserWhereUserThread(getActivity());
                        getUserWhereUserThread.execute(user, mainConstant.getUrlGetuser());
                        String json = getUserWhereUserThread.get();
                        Log.wtf("14Feb2019", "json =" + json);
                       //   Check Code

                        if (json.equals("null")) {
                            mainAlert.normalDialog("User False", "No " + user + "in my Database");
                        } else {

                            JSONArray jsonArray = new JSONArray(json);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            if (password.equals(jsonObject.getString("Password"))) {
                                Toast.makeText(getActivity(), "Welcome" + jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(),ServiceActivity.class));
                                getActivity().finish();
                            } else {
                                mainAlert.normalDialog("Password False", "Please Try Again");
                            }

                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }// If
            }
        });
    }   //Main Method

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }   //Create View

}   //Main Class
