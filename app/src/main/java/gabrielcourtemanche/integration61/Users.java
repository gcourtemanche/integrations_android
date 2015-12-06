package gabrielcourtemanche.integration61;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gabriel on 2015-08-17.
 */
public class Users {
    public static String[] tables = {"inscription", "membre_equipe", "benevole", "tsj"};
    public static String afficher(JSONObject user, int type) throws JSONException {
        String text = "";
        String prenom = user.get("prenom").toString();
        String nom = user.get("nom").toString();

        switch (type) {
            case 0:
                String allergie = user.get("allergie").toString();
                String adresse = user.get("adresse").toString();

                if (!allergie.equals("")) {
                    text += "ALLERGIE : " + allergie + "\n\n";
                }
                text += "NOUVEAU\n";
                text += prenom + " " + nom + "\n";
                text += adresse;
                break;
            case 1:
                text += "CHEF D'ÉQUIPE\n";
                text += prenom + " " + nom + "\n";
                break;
            case 2:
                text += "BÉNÉVOLE\n";
                text += prenom + " " + nom + "\n";
                break;
            case 3:
                text += "TSJ\n";
                text += prenom + " " + nom + "\n";
                break;
        }
        return text;
    }
}
