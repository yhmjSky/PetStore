package xmut.ygnn.petstore;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;

import java.util.List;

import xmut.ygnn.petstore.adapter.ArticleAdapter;
import xmut.ygnn.petstore.adapter.StoreAdapter;
import xmut.ygnn.petstore.util.overlay.WalkRouteOverlay;

import static xmut.ygnn.petstore.MainActivity.stores;

public class HomeFragment extends Fragment implements PoiSearch.OnPoiSearchListener,
        AMap.OnMarkerClickListener {

    private static final String TAG = "Fragment Map";
    View view;
    MapView mapView;
    AMap aMap;
    Context context;
    ImageView serchimageview;
    PoiResult poiResults;
    AMapLocationClient client;//??????????????????????????????
    AMapLocationClient mapLocationClient;
    AMapLocationClientOption mapLocationClientOption;

    StoreAdapter storeAdapter;
    ArticleAdapter articleAdapter;

    MyLocationStyle myLocationStyle;
    ProgressDialog progDialog = null;
    LatLonPoint TargetMarker;
    RouteSearch routeSearch;
    LatLonPoint latLonPoint;
    AMapLocationListener mLocationListener;

    String keyWord = "??????";
    String city = "";


    PoiSearch.Query query;
    PoiSearch poiSearch;


    WalkRouteResult mWalkRouteResult;


    TextView recommendTV,aroundTV,shopTV;
    TextView searchText;

    LinearLayout aroundLL;//??????
    LinearLayout shopLL, recommendLL;

    RecyclerView articleRecyclerView;
    RecyclerView storeRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_home,container,false);

        init();

        mapView.onCreate(savedInstanceState);
        permission();
        return view;

    }

    private void init(){


        recommendLL = view.findViewById(R.id.tuijian);
        shopLL = view.findViewById(R.id.shangcheng);
        aroundLL = view.findViewById(R.id.zhoubian);

        recommendTV = view.findViewById(R.id.recommend_tap);
        shopTV = view.findViewById(R.id.shop_tap);
        aroundTV = view.findViewById(R.id.around_tap);

        searchText = view.findViewById(R.id.searchText);
        storeRecyclerView = view.findViewById(R.id.storelist);
        articleRecyclerView = view.findViewById(R.id.articlelist);


        mapView = (MapView) view.findViewById(R.id.map);//??????????????????
        serchimageview=(ImageView) view.findViewById(R.id.search_img);

        aMap = mapView.getMap();//??????????????????????????????


        myLocationStyle = new MyLocationStyle();//??????????????????????????????
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????1???1???????????????????????????myLocationType????????????????????????????????????
        myLocationStyle.interval(20000); //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        myLocationStyle.myLocationType(4);
        aMap.setMyLocationStyle(myLocationStyle);//?????????????????????Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);?????????????????????????????????????????????????????????
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);// ?????????true?????????????????????????????????false??????????????????????????????????????????????????????false???
        aMap.showIndoorMap(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo((float) 15));

        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                TargetMarker = new LatLonPoint(marker.getPosition().latitude, marker.getPosition().longitude);
                return false;
            }
        });

        RouteSearch routeSearch = new RouteSearch(this.context);
        this.routeSearch = routeSearch;
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
            }

            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
            }

            public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
                dissmissProgressDialog();
                if (errorCode == 1000) {
                    CharSequence charSequence = "????????????";
                    if (result == null || result.getPaths() == null) {
                        Toast.makeText(context, charSequence, Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (result.getPaths().size() > 0) {

                        // ????????????????????????
                        aMap.clear();
                        myLocationStyle.interval(2000); //???????????????????????????

                        mWalkRouteResult = result;
                        WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(context,aMap, (WalkPath)mWalkRouteResult.getPaths().get(0),
                                mWalkRouteResult.getStartPos(), mWalkRouteResult.getTargetPos());
                        walkRouteOverlay.removeFromMap();
                        walkRouteOverlay.addToMap();
                        walkRouteOverlay.zoomToSpan();
                    } else if (result != null && result.getPaths() == null) {
                        Toast.makeText(context, charSequence, Toast.LENGTH_LONG).show();
                    }
                }
            }

            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
            }
        });


        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            public void onInfoWindowClick(Marker arg0) {
                if (TargetMarker == null || latLonPoint == null) {
                    Toast.makeText(context, "????????????????????????", Toast.LENGTH_SHORT).show();
                    return;
                }
                routeSearch.calculateWalkRouteAsyn(new RouteSearch.WalkRouteQuery(new RouteSearch.FromAndTo(latLonPoint, TargetMarker), 0));
                showProgressDialog();
            }
        });




        mLocationListener = new AMapLocationListener() {
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation == null) {
                    return;
                }

                if (aMapLocation.getErrorCode() == 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    int type = aMapLocation.getLocationType();
                    String address = aMapLocation.getAddress();
                    String stringBuilder2 = type + address;
                    stringBuilder.append(stringBuilder2);
                    city = aMapLocation.getCity();
                    if (latLonPoint == null) {
                        latLonPoint = new LatLonPoint(aMapLocation.getLatitude(),
                                aMapLocation.getLongitude());
                    } else {
                        latLonPoint.setLatitude(aMapLocation.getLatitude());
                        latLonPoint.setLongitude(aMapLocation.getLongitude());
                    }
                    return;
                }
                String stringBuilder3 = aMapLocation.getErrorCode() + "---" + aMapLocation.getErrorInfo();
                Log.i("erro info???", stringBuilder3);
            }
        };

        this.mapLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
        this.mapLocationClientOption = aMapLocationClientOption;
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        this.mapLocationClientOption.setNeedAddress(true);
        this.mapLocationClientOption.setOnceLocation(false);
        this.mapLocationClientOption.setWifiActiveScan(true);
        this.mapLocationClientOption.setMockEnable(false);
        this.mapLocationClientOption.setInterval(2000);
        this.mapLocationClient.setLocationOption(this.mapLocationClientOption);
        this.mapLocationClient.setLocationListener(this.mLocationListener);
        this.mapLocationClient.startLocation();


        serchimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord = searchText.getText().toString().trim();
                doSearchQuery(keyWord);
            }
        });



        articleAdapter = new ArticleAdapter(getContext());
        articleRecyclerView.setAdapter(articleAdapter);
        articleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));

        recommendTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                recommendTV.setTextColor(getResources().getColor(R.color.change_tap));
                shopTV.setTextColor(getResources().getColor(R.color.color_text));
                aroundTV.setTextColor(getResources().getColor(R.color.color_text));

                shopLL.setVisibility(View.GONE);
                aroundLL.setVisibility(View.GONE);
                recommendLL.setVisibility(View.VISIBLE);

                articleAdapter = new ArticleAdapter(getContext());
                articleRecyclerView.setAdapter(articleAdapter);
                articleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                        RecyclerView.VERTICAL, false));


            }
        });

        aroundTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recommendTV.setTextColor(getResources().getColor(R.color.color_text));
                shopTV.setTextColor(getResources().getColor(R.color.color_text));
                aroundTV.setTextColor(getResources().getColor(R.color.change_tap));


                recommendLL.setVisibility(View.GONE);
                shopLL.setVisibility(View.GONE);
                aroundLL.setVisibility(View.VISIBLE);
            }
        });


        shopTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                recommendTV.setTextColor(getResources().getColor(R.color.color_text));
                shopTV.setTextColor(getResources().getColor(R.color.change_tap));
                aroundTV.setTextColor(getResources().getColor(R.color.color_text));


                recommendLL.setVisibility(View.GONE);
                aroundLL.setVisibility(View.GONE);
                shopLL.setVisibility(View.VISIBLE);


                storeAdapter = new StoreAdapter(getContext());
                storeRecyclerView.setAdapter(storeAdapter);
                storeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                        RecyclerView.VERTICAL, false));


            }
        });





    }


    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        dissmissProgressDialog();
        String str = "????????????:";
        String str2 = "---";
        if (i == 1000) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(i);
            Log.i(str2, stringBuilder.toString());
            if (poiResult == null || poiResult.getQuery() == null) {
                Toast.makeText(this.context, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                return;
            } else if (poiResult.getQuery().equals(this.query)) {
                this.poiResults = poiResult;
                List<PoiItem> poiItems = poiResult.getPois();
                List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();
                if (poiItems != null && poiItems.size() > 0) {
                    this.aMap.clear();
                    for (int j = 0; j < poiItems.size(); j++) {
                        System.out.println(((PoiItem) poiItems.get(j)).getTitle());
                        this.aMap.addMarker(new MarkerOptions().position(convertToLatLng(((PoiItem) poiItems.get(j)).getLatLonPoint())).title(((PoiItem) poiItems.get(j)).getTitle()).snippet(((PoiItem) poiItems.get(j)).getSnippet()));
                    }
                } else if (suggestionCities == null || suggestionCities.size() <= 0) {
                    Toast.makeText(this.context, "???????????????", Toast.LENGTH_SHORT).show();
                } else {
//                    showSuggestCity(suggestionCities);
                }
                return;
            } else {
                return;
            }
        }
        String stringBuilder = str + i;
        Log.i(str2, stringBuilder);
        Context context = this.context;
        String stringBuilder2 = "????????????---" + i;
        Toast.makeText(context, stringBuilder2, Toast.LENGTH_SHORT).show();
    }

    private void dissmissProgressDialog() {
        ProgressDialog progressDialog = this.progDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    private void doSearchQuery(String keyword) {
        showProgressDialog();
//        this.currentPage = 0;
        PoiSearch.Query query = new PoiSearch.Query(keyword, "", this.city);
        this.query = query;
        query.setPageSize(30);
        this.query.setPageNum(0);
        PoiSearch poiSearch = new PoiSearch(this.context, this.query);
        this.poiSearch = poiSearch;
        poiSearch.setOnPoiSearchListener(this);
        LatLonPoint latLonPoint = this.latLonPoint;
        if (latLonPoint != null) {
            this.poiSearch.setBound(new PoiSearch.SearchBound(latLonPoint, 20000));
        }
        this.poiSearch.searchPOIAsyn();
    }


    private void showProgressDialog() {
        if (this.progDialog == null) {
            this.progDialog = new ProgressDialog(this.context);
        }
        this.progDialog.setProgressStyle(0);
        this.progDialog.setIndeterminate(false);
        this.progDialog.setCancelable(false);
        ProgressDialog progressDialog = this.progDialog;
        String stringBuilder = "????????????:\n" + this.keyWord;
        progressDialog.setMessage(stringBuilder);
        this.progDialog.show();
    }


    private void permission() {
        String str = "android.permission.ACCESS_COARSE_LOCATION";
        String str2 = "android.permission.ACCESS_FINE_LOCATION";
        if (Build.VERSION.SDK_INT <= 28) {
            Log.i(TAG, "android sdk <= 28 Q");
            if (ActivityCompat.checkSelfPermission(this.context, str2) != 0 && ActivityCompat.checkSelfPermission(this.context, str) != 0) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{str2, str}, 1);
            }
        } else if (ActivityCompat.checkSelfPermission(this.context, str2) != 0 && ActivityCompat.checkSelfPermission(this.context, str) != 0) {
            String str3 = "android.permission.ACCESS_BACKGROUND_LOCATION";
            if (ActivityCompat.checkSelfPermission(this.context, str3) != 0) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{str2, str, str3}, 2);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String str = TAG;
        if (requestCode == 1) {
            if (grantResults.length > 1 && grantResults[0] == 0 && grantResults[1] == 0) {
                Log.i(str, "onRequestPermissionsResult: apply LOCATION PERMISSION successful");
            } else {
                Log.i(str, "onRequestPermissionsResult: apply LOCATION PERMISSSION  failed");
            }
        }
        if (requestCode != 2) {
            return;
        }
        if (grantResults.length > 2 && grantResults[2] == 0 && grantResults[0] == 0 && grantResults[1] == 0) {
            Log.i(str, "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION successful");
        } else {
            Log.i(str, "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION  failed");
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    public LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
        mapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
