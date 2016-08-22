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
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class LireFragment extends Fragment {
    private TextView infoUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lire_fragment, container, false);
        infoUser = (TextView) rootView.findViewById(R.id.tv_infoUser);
        return rootView;
    }

    public void lire(String uid) {
        RequestParams p = new RequestParams("uid", uid);
        IntegRestClient.get("uid", p, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject user) {
                try {
                    infoUser.setText(Users.afficher(user, user.getInt("table")));
                } catch (JSONException e) {
                    infoUser.setText("");
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Bracelet non jumelé", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(1);
    }
}