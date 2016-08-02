package ninja.mpnguyen.impasse;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by mark on 7/30/16.
 */
class CompositeListAdapter extends BaseAdapter {
    private final BaseAdapter[] adapters;

    public CompositeListAdapter(BaseAdapter... adapters) {
        this.adapters = adapters;
    }

    @Override
    public int getCount() {
        int sum = 0;
        for (BaseAdapter adapter : adapters) {
            sum += adapter.getCount();
        }
        return sum;
    }

    @Override
    public Object getItem(int i) {
        for (BaseAdapter adapter : adapters) {
            int count = adapter.getCount();
            if (i >= count) {
                i -= count;
            } else {
                return adapter.getItem(i);
            }
        }
        throw new RuntimeException("Bad state.  Not enough content.");
    }

    @Override
    public long getItemId(int i) {
        for (BaseAdapter adapter : adapters) {
            int count = adapter.getCount();
            if (i >= count) {
                i -= count;
            } else {
                return adapter.getItemId(i);
            }
        }
        throw new RuntimeException("Bad state.  Not enough content.");
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        for (BaseAdapter adapter : adapters) {
            int count = adapter.getCount();
            if (i >= count) {
                i -= count;
            } else {
                return adapter.getView(i, view, viewGroup);
            }
        }
        throw new RuntimeException("Bad state.  Not enough content.");
    }
}
