package ninja.mpnguyen.impasse;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KeyList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}

/**
 * Dictionaries:
 * many to 1
 * key_id -> name
 * LHS should be primary unique/distinct
 *
 * Hierarchy:
 * 1 to many
 * key_id <-> key_id
 * RHS should be primary unique/distinct
 *
 * Data:
 * many to 1
 * value_id -> String
 * LHS should be primary unique/distinct
 *
 * File format should have built in versioning semantic versioning.
 * That version will tell us if we need to update the client (major version incompatibility).
 * That version will tell us if we need to migrate (minor version incompatibility)
 * If we introduce sharding, index and size should be part of
 * metadata so we know how to compose the data bits.
 *
 * Datastore should have versioning/journaling.
 * This affords for merging of collisions.
 * Use journaling to prevent changes from introducing cascading diffs in the file.
 *
 * We can't trust free cloud storage.  If one is bought out by another, we can't trust both.
 * We shouldn't trust even two to not collaborate.
 * We should trust that three would be too difficult to unify.
 * Thus, you will need 3 pieces from M total shards.
 * Dropbox, Box, OneDrive, Google Drive.
 * All of these support etag and revisions so we should leverage that for failures.
 *
 * Can we avoid trusting other devices?  What if someone
 *
 * What should we encrypt?
 * Only the database should be encrypted.  The file headings and
 * metadata stuff should not have any meaningful information.
 * Encrypt both names and entities (but not IDs).  Names may contain meaningful information,
 * such as the types of security questions one answered, and the type of information that the
 * datastore contains.
 *
 * What should we encourage?
 * We should encourage people to enable full disk encryption (they will lose all their data).
 * We should encourage people to set some security.
 * Discourage root.  It ruins security and we try to avoid allowing root apps
 * from exploiting us but it's technically impossible.
 */

/**
 * 1) Deduplicate all pantry items
 * 2) Judge how much cabinet space we really need
 * 3) Combine microwave, stove, and oven
 * 4) Create pantry and deep drawers for pots
 * 5) Magnet knives
 * 6) Hang pots
 * 7) Actually use dish washer
 */