package gabrielcourtemanche.integration61;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class JumelerFragment extends Fragment {
    private int posTypeUser;
    private CharSequence idUser;
    private Spinner typeUser;
    private EditText idUserText;
    private TextView infoUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.jumeler_fragment, container, false);

        typeUser  = (Spinner) rootView.findViewById(R.id.type_user);
        idUserText = (EditText) rootView.findViewById(R.id.id_user);
        infoUser = (TextView) rootView.findViewById(R.id.info_user);

        idUserText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence id, int start,
                                      int before, int count) {
                posTypeUser = typeUser.getSelectedItemPosition();
                idUser = id;

                final RequestParams params = new RequestParams();
                params.put("id", idUser);
                params.put("table", Users.tables[posTypeUser]);

                IntegRestClient.get("info", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject user) {
                        try {
                            infoUser.setText(Users.afficher(user, posTypeUser));
                        } catch (JSONException e) {
                            infoUser.setText("");
                        }
                    }
                });
            }
        });
        return rootView;
    }

    public void jumeler(String uid) {
        if (!infoUser.getText().equals("")) {
            RequestParams params = new RequestParams();
            params.put("uid", uid);
            params.put("id", idUser);
            params.put("table", Users.tables[posTypeUser]);

            IntegRestClient.post("jumeler", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
                    String response = "";
                    try {
                        response = data.get("response").toString();
                    } catch (JSONException e) {
                        response = "ERREUR";
                    }
                    if (response.equals("deja_jumeler")) {
                        try {
                            String prenom = data.getString("prenom");
                            String nom = data.getString("nom");
                            String message = "Le bracelet appartient à " + prenom + " " + nom;
                            Functions.alerteDialog(getActivity(), message);
                        } catch (JSONException e) {
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Erreur Serveur", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                    else {
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(0);
    }
}