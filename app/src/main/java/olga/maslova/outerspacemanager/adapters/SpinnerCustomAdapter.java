package olga.maslova.outerspacemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import olga.maslova.outerspacemanager.R;

public class SpinnerCustomAdapter extends BaseAdapter {

        Context context;
        List<String> userNames;
        LayoutInflater inflater;

        public SpinnerCustomAdapter(Context applicationContext, List<String> userNames) {
            this.context = applicationContext;
            this.userNames = userNames;
            inflater = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return userNames.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.custom_spinner_items, null);
            TextView names = (TextView) view.findViewById(R.id.textView);
            names.setText(userNames.get(i));
            return view;
        }

}
