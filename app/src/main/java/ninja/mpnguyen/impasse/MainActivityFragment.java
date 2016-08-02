package ninja.mpnguyen.impasse;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.lang.ref.WeakReference;

import ninja.mpnguyen.impasse.FolderListAdapter.OnFolderClickListener;
import ninja.mpnguyen.impasse.ValueListAdapter.OnValueClickListener;

public class MainActivityFragment extends Fragment {

    public static final String ARGUMENT_FOLDER = "mpnguyen.impasse.MainActivityFragment.EXTRA_FOLDER";

    public static MainActivityFragment newInstance(Folder root) {
        MainActivityFragment f = new MainActivityFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_FOLDER, root);
        f.setArguments(arguments);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle inState) {
        super.onViewCreated(view, inState);
        ListView folder_list_view = (ListView) view.findViewById(R.id.folder_list_view);
        Folder root = getArguments().getParcelable(ARGUMENT_FOLDER);
        assert root != null;
        OnFolderClickListener folderListener = (OnFolderClickListener) getActivity();
        DeferredFolderListener deferredFolderListener = new DeferredFolderListener(folderListener);
        folder_list_view.setAdapter(new RootFolderListAdapter(root, new ValueClickHandler(), deferredFolderListener));
    }


    private static class DeferredFolderListener implements OnFolderClickListener {
        private final WeakReference<OnFolderClickListener> callbackRef;

        private DeferredFolderListener(OnFolderClickListener callback) {
            this.callbackRef = new WeakReference<OnFolderClickListener>(callback);
        }

        @Override
        public void onClick(View view, Folder folder) {
            OnFolderClickListener callback = callbackRef.get();
            if (callback != null) {
                callback.onClick(view, folder);
            } else {
                Log.w(getClass().getName(), "onClick triggered but callback was already collected");
            }
        }
    }

    private static class ValueClickHandler implements OnValueClickListener {
        @Override
        public void onClick(View view, Value value) {
            ClipData.newPlainText(value.name, value.data);
            Snackbar.make(view, "Copied " + value.name, Snackbar.LENGTH_LONG).show();
        }
    }

    private static class RootFolderListAdapter extends CompositeListAdapter {
        public RootFolderListAdapter(
                Folder root,
                OnValueClickListener valueCallback,
                OnFolderClickListener folderCallback
        ) {
            super(
                    new FolderListAdapter(root.folders, folderCallback),
                    new ValueListAdapter(root.values, valueCallback)
            );
        }
    }

}
