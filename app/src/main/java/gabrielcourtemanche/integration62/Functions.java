package gabrielcourtemanche.integration62;

import android.app.Activity;
import android.app.AlertDialog;

import java.math.BigInteger;

/**
 * Created by Gabriel on 21/08/2015.
 */
public class Functions {
    // Create an alert dialog with the message in parameter
    public static void alerteDialog(Activity activity, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(message);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // To display the UID from the binary
    public static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + "X", new BigInteger(1,data));
    }
}
