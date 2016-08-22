package gabrielcourtemanche.integration62;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class StatsFragment extends Fragment {
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stats_fragment, container, false);
        textView = (TextView) rootView.findViewById(R.id.textView);
        afficher();
        return rootView;
    }

    private void afficher() {
        IntegRestClient.get("stats", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject stats) {
                try {
                    textView.setText("Méchoui : " + stats.getString("mechoui") +
                            "\nMardi début : " + stats.getString("mardi_in") +
                            "\nMardi fin : " + stats.getString("mardi_out") +
                            "\nMercredi souper : " + stats.getString("mercredi_souper") +
                            "\nJeudi dîner : " + stats.getString("jeudi_diner"));
                } catch (JSONException e) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Erreur Serveur", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(4);
    }
}