package ninja.mpnguyen.impasse;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FolderListAdapter.OnFolderClickListener {
    private static final String content_json = "{\n" +
            "  \"foo\":\"bar\",\n" +
            "  \"baz\":\"buz\",\n" +
            "  \"biz\":{\n" +
            "    \"bob\":\"bill\",\n" +
            "    \"ben\":\"barn\"\n" +
            "  }\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        FragmentManager fm = getSupportFragmentManager();
        Folder folder = null;
        try {
            folder = MainActivity.fromJson(new JSONObject(content_json));
            fm.beginTransaction()
                    .replace(R.id.content_main, MainActivityFragment.newInstance(folder))
                    .commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject toJson(Folder root) throws JSONException {
        JSONObject jsonRoot = new JSONObject();
        for (Folder subfolder : root.folders) {
            jsonRoot.put(subfolder.name, toJson(subfolder));
        }
        for (Value value : root.values) {
            jsonRoot.put(value.name, value.data);
        }
        return jsonRoot;
    }

    public static Folder fromJson(JSONObject root) throws JSONException {
        return fromJson(root, null);
    }

    private static Folder fromJson(JSONObject root, String name) throws JSONException {
        List<Folder> folders = new ArrayList<>();
        List<Value> values = new ArrayList<>();

        Iterator<String> keys = root.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object object = root.get(key);
            if (object instanceof JSONObject) {
                folders.add(fromJson((JSONObject) object, key));
            } else if (object instanceof String) {
                values.add(new Value(key, (String) object));
            } else {
                throw new UnsupportedOperationException(object.getClass().toString() + " is not a valid type to deserialize");
            }
        }
        return new Folder(name, folders.toArray(new Folder[folders.size()]), values.toArray(new Value[values.size()]));
    }

    @Override
    public void onClick(View view, Folder folder) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .addToBackStack("Entering folder: " + folder.name)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.content_main, MainActivityFragment.newInstance(folder))
                .commit();
    }
}
