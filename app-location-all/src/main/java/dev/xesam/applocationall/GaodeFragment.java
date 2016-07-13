package dev.xesam.applocationall;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.xeam.android.lib.location.CLocation;
import dev.xeam.android.lib.location.CLocationClient;
import dev.xeam.android.lib.location.CLocationException;
import dev.xeam.android.lib.location.CLocationListener;
import dev.xeam.android.lib.location.CLocationOption;
import dev.xeam.android.lib.location.gaode.GaodeLocationClient;


public class GaodeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int mType;
    private String mTitle;

    @Bind(R.id.console)
    public TextSwitcher vConsole;

    public GaodeFragment() {
        // Required empty public constructor
    }

    public static GaodeFragment newInstance(int param1, String param2) {
        GaodeFragment fragment = new GaodeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getInt(ARG_PARAM1);
            mTitle = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    GaodeLocationClient mCLocationClient;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCLocationClient = new GaodeLocationClient(getContext());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.request_single_reuse_false)
    public void requestSingle2() {
        CLocationOption option = new CLocationOption();
        option.setReuse(false);
        mCLocationClient.requestSingleUpdate(option, new SimpleLocationListener(vConsole));
    }

    @OnClick(R.id.request_single_reuse_true)
    public void requestSingle1() {
        CLocationOption option = new CLocationOption();
        option.setReuse(true);
        mCLocationClient.requestSingleUpdate(option, new SimpleLocationListener(vConsole));
    }

    @OnClick(R.id.start_locate)
    public void startUpdates() {
        CLocationOption option = new CLocationOption();
        option.setLocationInterval(10_000);
        mCLocationClient.requestLocationUpdates(option, new SimpleLocationListener(vConsole));
    }

    @OnClick(R.id.stop_locate)
    public void removeUpdates() {
        mCLocationClient.removeUpdates();
    }
}
