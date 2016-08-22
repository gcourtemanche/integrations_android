package gabrielcourtemanche.integration62;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gabriel on 2015-08-17.
 */
public class Users {
    public static String[] tables = {"nouveaux", "chefs", "benevoles", "tsj"};
    public static String afficher(JSONObject user, int type) throws JSONException {
        String text = "";
        String prenom, nom, allergie, adresse, vegetarien;
        prenom = user.get("Prenom").toString();
        nom = user.get("Nom").toString();
        switch (type) {
            case 0:
                allergie = user.get("Allergies").toString();
                adresse = user.get("Adresse").toString();
                vegetarien = user.get("Vegetarien").toString();

                if (!allergie.equals("")) {
                    text += "ALLERGIE : " + allergie + "\n\n";
                }
                text += "NOUVEAU\n";
                text += prenom + " " + nom + "\n";
                if (vegetarien.equals("X")) {
                    text += "VÉGÉTARIEN" + "\n";
                }
                text += adresse;
                break;
            case 1:
                text += "CHEF D'ÉQUIPE\n";
                text += prenom + " " + nom + "\n";
                break;
            case 2:
                vegetarien = user.get("Vegetarien").toString();
                text += "BÉNÉVOLE\n";
                text += prenom + " " + nom + "\n";
                if (vegetarien.equals("X")) {
                    text += "VÉGÉTARIEN" + "\n";
                }
                break;
            case 3:
                text += "TSJ\n";
                text += prenom + " " + nom + "\n";
                break;
        }
        return text;
    }
}
