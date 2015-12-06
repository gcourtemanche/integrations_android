package gabrielcourtemanche.integration61;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import mobisocial.nfc.Nfc;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Nfc mNfc;
    private int currentFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNfc = new Nfc(this);
        mNfc.onCreate(this);
        //NdefMessage message = NdefFactory.fromText("allo Gab");
        //mNfc.enableTagWriteMode(message);

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    public void onResume() {
        super.onResume();
        mNfc.onResume(this);
        // your activity's onResume code
    }

    public void onPause() {
        super.onPause();
        mNfc.onPause(this);
        // your activity's onPause code
    }

    public void onNewIntent(Intent intent) {
        if (mNfc.onNewIntent(this, intent)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String uid = Functions.bin2hex(tag.getId());
            switch (currentFragment) {
                case 0:
                    JumelerFragment frag0 = (JumelerFragment) getFragmentManager().findFragmentById(R.id.container);
                    frag0.jumeler(uid);
                    break;
                case 1:
                    LireFragment frag1 = (LireFragment) getFragmentManager().findFragmentById(R.id.container);
                    frag1.lire(uid);
                    break;
                case 2:
                    ScanFragment frag2 = (ScanFragment) getFragmentManager().findFragmentById(R.id.container);
                    frag2.scan(uid);
                    break;
            }
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // Create new fragment and transaction
        Fragment objFragment = null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        switch (position) {
            case 0:
                objFragment = new JumelerFragment();
                break;
            case 1:
                objFragment = new LireFragment();
                break;
            case 2:
                objFragment = new ScanFragment();
                break;
            case 3:
                objFragment = new FindFragment();
                break;
            case 4:
                objFragment = new StatsFragment();
                break;
        }

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.container, objFragment);
        transaction.addToBackStack(objFragment.getClass().getName());

        // Commit the transaction
        transaction.commit();
    }

    public void onSectionAttached(int position) {
        currentFragment = position;
        switch (position) {
            case 0:
                mTitle = getString(R.string.title_section1);
                break;
            case 1:
                mTitle = getString(R.string.title_section2);
                break;
            case 2:
                mTitle = getString(R.string.title_section3);
                break;
            case 3:
                mTitle = getString(R.string.title_section4);
                break;
            case 4:
                mTitle = getString(R.string.title_section5);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}