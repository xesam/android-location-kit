package dev.xesam.applocationall;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextSwitcher;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.xesam.libbaidulocatioin.BaiduLocationClient;
import dev.xesam.libgaodelocation.GaodeLocationClient;
import dev.xesam.liblocation.CLocation;
import dev.xesam.liblocation.CLocationClient;
import dev.xesam.liblocation.CLocationException;
import dev.xesam.liblocation.CLocationListener;


public class LocationFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int mType;
    private String mTitle;

    @Bind(R.id.request_single)
    public Button vSingle;

    @Bind(R.id.start_locate)
    public Button vStart;

    @Bind(R.id.stop_locate)
    public Button vStop;

    @Bind(R.id.console)
    public TextSwitcher vConsole;

    public LocationFragment() {
        // Required empty public constructor
    }

    public static LocationFragment newInstance(int param1, String param2) {
        LocationFragment fragment = new LocationFragment();
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
        if (mType == Type.GAODE) {
            mCLocationClient = new GaodeLocationClient(getContext());
        } else if (mType == Type.BAIDU) {
            mCLocationClient = new BaiduLocationClient(getContext());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.request_single)
    public void requestSingle() {
        if (mType == Type.GAODE) {
            CLocationClient locationClient = new GaodeLocationClient(getContext());
            locationClient.requestSingleUpdate(new CLocationListener() {
                @Override
                public void onLocateSuccess(CLocationClient locationClient, CLocation location) {
                    vConsole.setText(location.toString());
                }

                @Override
                public void onLocateFail(CLocationClient locationClient, CLocationException e) {
                    vConsole.setText(e.toString());
                }
            });
        } else if (mType == Type.BAIDU) {
            CLocationClient locationClient = new BaiduLocationClient(getContext());
            locationClient.requestSingleUpdate(new CLocationListener() {
                @Override
                public void onLocateSuccess(CLocationClient locationClient, CLocation location) {
                    vConsole.setText(location.toString());
                }

                @Override
                public void onLocateFail(CLocationClient locationClient, CLocationException e) {
                    vConsole.setText(e.toString());
                }
            });
        }
    }

    @OnClick(R.id.start_locate)
    public void startLocate() {
        mCLocationClient.startLocation();
    }

    @OnClick(R.id.stop_locate)
    public void stopLocate() {
        mCLocationClient.stopLocation();
    }
}
