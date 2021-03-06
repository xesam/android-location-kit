package dev.xesam.applocationall;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.xeam.android.lib.location.CLocationClient;
import dev.xeam.android.lib.location.CLocationOption;
import dev.xeam.android.lib.location.baidu.BaiduLocationClient;


public class BaiduFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int mType;
    private String mTitle;

    @BindView(R.id.console)
    public TextSwitcher vConsole;

    public BaiduFragment() {
        // Required empty public constructor
    }

    public static BaiduFragment newInstance(int param1, String param2) {
        BaiduFragment fragment = new BaiduFragment();
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

    CLocationClient mCLocationClient;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCLocationClient = new BaiduLocationClient(getContext());
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
        option.setLocationInterval(5_000);
        mCLocationClient.requestLocationUpdates(option, new SimpleLocationListener(vConsole));
    }

    @OnClick(R.id.stop_locate)
    public void removeUpdates() {
        mCLocationClient.removeUpdates();
    }
}
