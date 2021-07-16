package xmut.ygnn.petstore.util.overlay;

import android.content.Context;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkStep;
import com.autonavi.base.ae.gmap.glanimation.AdglAnimationContantValues;
import java.util.List;

public class WalkRouteOverlay extends RouteOverlay {
    private PolylineOptions mPolylineOptions;
    private WalkPath walkPath;
    private BitmapDescriptor walkStationDescriptor = null;

    public WalkRouteOverlay(Context context, AMap amap, WalkPath path, LatLonPoint start, LatLonPoint end) {
        super(context);
        this.mAMap = amap;
        this.walkPath = path;
        this.startPoint = AMapServicesUtil.convertToLatLng(start);
        this.endPoint = AMapServicesUtil.convertToLatLng(end);
    }

    public void addToMap() {
        initPolylineOptions();
        try {
            List<WalkStep> walkPaths = this.walkPath.getSteps();
            for (int i = 0; i < walkPaths.size(); i++) {
                WalkStep walkStep = (WalkStep) walkPaths.get(i);
                addWalkStationMarkers(walkStep, AMapServicesUtil.convertToLatLng((LatLonPoint) walkStep.getPolyline().get(0)));
                addWalkPolyLines(walkStep);
            }
            addStartAndEndMarker();
            showPolyline();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void checkDistanceToNextStep(WalkStep walkStep, WalkStep walkStep1) {
        LatLonPoint lastPoint = getLastWalkPoint(walkStep);
        LatLonPoint nextFirstPoint = getFirstWalkPoint(walkStep1);
        if (!lastPoint.equals(nextFirstPoint)) {
            addWalkPolyLine(lastPoint, nextFirstPoint);
        }
    }

    private LatLonPoint getLastWalkPoint(WalkStep walkStep) {
        return (LatLonPoint) walkStep.getPolyline().get(walkStep.getPolyline().size() - 1);
    }

    private LatLonPoint getFirstWalkPoint(WalkStep walkStep) {
        return (LatLonPoint) walkStep.getPolyline().get(0);
    }

    private void addWalkPolyLine(LatLonPoint pointFrom, LatLonPoint pointTo) {
        addWalkPolyLine(AMapServicesUtil.convertToLatLng(pointFrom), AMapServicesUtil.convertToLatLng(pointTo));
    }

    private void addWalkPolyLine(LatLng latLngFrom, LatLng latLngTo) {
        this.mPolylineOptions.add(new LatLng[]{latLngFrom, latLngTo});
    }

    private void addWalkPolyLines(WalkStep walkStep) {
        this.mPolylineOptions.addAll(AMapServicesUtil.convertArrList(walkStep.getPolyline()));
    }

    private void addWalkStationMarkers(WalkStep walkStep, LatLng position) {
        MarkerOptions position2 = new MarkerOptions().position(position);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("方向:");
        stringBuilder.append(walkStep.getAction());
        stringBuilder.append("\n道路:");
        stringBuilder.append(walkStep.getRoad());
        addStationMarker(position2.title(stringBuilder.toString()).snippet(walkStep.getInstruction()).visible(this.nodeIconVisible).anchor(AdglAnimationContantValues.ADGLANIMATOIN_ONE_HALF, AdglAnimationContantValues.ADGLANIMATOIN_ONE_HALF).icon(this.walkStationDescriptor));
    }

    private void initPolylineOptions() {
        if (this.walkStationDescriptor == null) {
            this.walkStationDescriptor = getWalkBitmapDescriptor();
        }
        this.mPolylineOptions = null;
        PolylineOptions polylineOptions = new PolylineOptions();
        this.mPolylineOptions = polylineOptions;
        polylineOptions.color(getWalkColor()).width(getRouteWidth());
    }

    private void showPolyline() {
        addPolyLine(this.mPolylineOptions);
    }
}
