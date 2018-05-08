package helpers;

import android.view.View;
import android.widget.TextView;

import com.transportapp.lazar.transportapp.R;

public class ValuePairViewHelper {
    public static void setLabelValuePair(View parentView, int view_id, String label, String value){
        View v = parentView.findViewById(view_id);
        TextView label_tv = v.findViewById(R.id.label);
        TextView value_tv = v.findViewById(R.id.value);
        label_tv.setText(label);
        value_tv.setText(value);
    }
}
