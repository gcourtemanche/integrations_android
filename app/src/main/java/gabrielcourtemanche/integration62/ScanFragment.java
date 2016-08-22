package gabrielcourtemanche.integration62;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class ScanFragment extends Fragment {
    private TextView infoUser;
    private Spinner list_activites;
    private static String [] db_list_activites = {"mechoui", "mardi_in", "mardi_out", "mercredi_souper", "jeudi_diner"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.scan_fragment, container, false);
        infoUser = (TextView) rootView.findViewById(R.id.tv_infoUser);
        list_activites = (Spinner) rootView.findViewById(R.id.list_activites);
        return rootView;
    }

    public void scan(String uid) {
        infoUser.setText("");
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("activite", db_list_activites[list_activites.getSelectedItemPosition()]);
        IntegRestClient.post("scan", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
                String response = "";
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Erreur Serveur", Toast.LENGTH_SHORT);
                try {
                    response = data.getString("response");
                } catch (JSONException e) {
                    toast.show();
                }
                if (response.equals("ERREUR")) {
                    toast.show();
                }
                else if (response.equals("ok")) {
                    try {
                        JSONObject user = data.getJSONObject("user");
                        infoUser.setText(Users.afficher(user, user.getInt("table")));
                    } catch (JSONException e) {
                        toast.show();
                    }
                }
                else {
                    String message = "";
                    if (response.equals("deja_participe")) {
                        try {
                            String prenom = data.getString("prenom");
                            String nom = data.getString("nom");
                            message = prenom + " " + nom + " a déjà participé à l'activité!";
                        } catch (JSONException e) {
                            toast.show();
                        }
                    }
                    else if (response.equals("bracelet_vide")) {
                        message = "Le bracelet n'est pas jumelé";
                    }
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setMessage(message);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(2);
    }
}