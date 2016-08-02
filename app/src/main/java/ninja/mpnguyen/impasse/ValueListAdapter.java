package ninja.mpnguyen.impasse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ValueListAdapter extends BaseAdapter {
    private final Value[] values;
    private final OnValueClickListener callback;

    public ValueListAdapter(Value[] values, OnValueClickListener callback) {
        this.values = values;
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Value getItem(int index) {
        return values[index];
    }

    @Override
    public long getItemId(int index) {
        Value value = getItem(index);
        return value.name.hashCode();
    }

    @Override
    public View getView(int index, View view, ViewGroup parent) {
        // Inflate new view if we can't recycle old view
        if (view == null) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.view_key_value, parent, false);
        }

        // Stuff data into view
        Value value = values[index];
        TextView nameView = (TextView) view.findViewById(R.id.key_value_name);
        nameView.setText(value.name);
        view.setOnClickListener(new InternalOnClickListener(callback, value));

        return view;
    }

    private static class InternalOnClickListener implements View.OnClickListener {
        private final OnValueClickListener callback;
        private final Value value;

        private InternalOnClickListener(OnValueClickListener callback, Value value) {
            this.callback = callback;
            this.value = value;
        }

        @Override
        public void onClick(View view) {
            callback.onClick(view, value);
        }
    }

    public interface OnValueClickListener {
        void onClick(View view, Value value);
    }
}
