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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FindFragment extends Fragment {
    private EditText et_prenom;
    private EditText et_nom;
    private TextView tv_users;
    private CharSequence prenom;
    private CharSequence nom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.find_fragment, container, false);
        et_prenom = (EditText) rootView.findViewById(R.id.et_prenom);
        et_nom = (EditText) rootView.findViewById(R.id.et_nom);
        tv_users = (TextView) rootView.findViewById(R.id.tv_users);
        prenom = "";
        nom = "";

        et_prenom.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                prenom = s;
                find();
            }
        });
        et_nom.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                nom = s;
                find();
            }
        });

        return rootView;
    }

    private void find() {
        RequestParams params = new RequestParams();
        params.put("prenom", prenom);
        params.put("nom", nom);
        IntegRestClient.get("find", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray users) {
                String text = "";
                for (int i = 0; i < users.length(); i++) {
                    try {
                        JSONObject user = users.getJSONObject(i);
                        text += user.getString("id") + " " + user.getString("prenom") +
                                " " + user.getString("nom") + "\n";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                tv_users.setText(text);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(3);
    }
}