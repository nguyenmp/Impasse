package ninja.mpnguyen.impasse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FolderListAdapter extends BaseAdapter {
    private final Folder[] folders;
    private final OnFolderClickListener callback;

    public FolderListAdapter(Folder[] folders, OnFolderClickListener callback) {
        this.folders = folders;
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return folders.length;
    }

    @Override
    public Folder getItem(int index) {
        return folders[index];
    }

    @Override
    public long getItemId(int index) {
        Folder folder = getItem(index);
        return folder.name.hashCode();
    }

    @Override
    public View getView(int index, View view, ViewGroup parent) {
        // Inflate new view if we can't recycle old view
        if (view == null) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.view_folder, parent, false);
        }

        // Stuff data into view
        Folder folder = folders[index];
        TextView nameView = (TextView) view.findViewById(R.id.key_folder_name);
        nameView.setText(folder.name);
        view.setOnClickListener(new InternalOnClickListener(callback, folder));

        return view;
    }

    private static class InternalOnClickListener implements View.OnClickListener {
        private final OnFolderClickListener callback;
        private final Folder folder;

        private InternalOnClickListener(OnFolderClickListener callback, Folder folder) {
            this.callback = callback;
            this.folder = folder;
        }

        @Override
        public void onClick(View view) {
            callback.onClick(view, folder);
        }
    }

    public interface OnFolderClickListener {
        void onClick(View view, Folder folder);
    }
}
