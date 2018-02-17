package ph.codeaxis.android.handycat.MainApp.Profile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

import ph.codeaxis.android.handycat.Entity.User;
import ph.codeaxis.android.handycat.MainApp.Drawer.DrawerProfile;
import ph.codeaxis.android.handycat.MainApp.MainFragmentHolder;
import ph.codeaxis.android.handycat.R;

/**
 * Created by Trisha on 27/09/2017.
 */

public class StatsFragment extends Fragment{

    private BarDataSet set1;

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_drawer_profile_stats, container,false);
        final HorizontalBarChart chart = (HorizontalBarChart) view.findViewById(R.id.chart);
        chart.setNoDataTextColor(Color.parseColor("#ffffff"));

        set1 = new BarDataSet(getDataSet(), "");
        set1.setColors(Color.parseColor("#90CAF9"), Color.parseColor("#CE93D8"), Color.parseColor("#EF9A9A"), Color.parseColor("#4CB5AB"), Color.parseColor("#FBC02D"));

        final ArrayList dataSets = new ArrayList();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        // hide Y-axis
        YAxis left = chart.getAxisLeft();
        left.setDrawLabels(false);

        YAxis right = chart.getAxisRight();
        right.setDrawLabels(false);

        // custom X-axis labels
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawLabels(false);
        xAxis.disableGridDashedLine();

        chart.setData(data);

        Description description = new Description();
        description.setText("");
        chart.setDescription(description);

        chart.animateY(1000);
        chart.invalidate();

        return view;
    }

    /*public class MyXAxisValueFormatter implements IAxisValueFormatter{
        private String[] mValues;

        public MyXAxisValueFormatter(String[] mValues){
            this.mValues = mValues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis){
            return mValues[(int)value];
        }
    }*/

    private ArrayList getDataSet() {

        ArrayList valueSet1 = new ArrayList();
        User user = MainFragmentHolder.user;

        //donate
        BarEntry v2 = new BarEntry(1, user.getDonate());
        valueSet1.add(v2);

        //trade
        BarEntry v3 = new BarEntry(2, user.getTrade());
        valueSet1.add(v3);

        //rent
        BarEntry v4 = new BarEntry(3, user.getRent());
        valueSet1.add(v4);

        //sell
        BarEntry v5 = new BarEntry(4, user.getSell());
        valueSet1.add(v5);

        //buy
        BarEntry v6 = new BarEntry(5, user.getBuy());
        valueSet1.add(v6);

        return valueSet1;
    }
}
