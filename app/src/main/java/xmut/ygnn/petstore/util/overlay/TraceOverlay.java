package xmut.ygnn.petstore.util.overlay;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import java.util.ArrayList;
import java.util.List;

public class TraceOverlay {
    public static final int TRACE_STATUS_FAILURE = 3;
    public static final int TRACE_STATUS_FINISH = 2;
    public static final int TRACE_STATUS_PREPARE = 4;
    public static final int TRACE_STATUS_PROCESSING = 1;
    private AMap mAMap;
    private int mDistance;
    private PolylineOptions mOption;
    private Polyline mPolyline;
    private int mTraceStatus = 4;
    private List<LatLng> mTracedList = new ArrayList();
    private int mWaitTime;

    public TraceOverlay(AMap amap, List<LatLng> lines) {
        this.mAMap = amap;
        options();
        this.mOption.addAll(lines);
        this.mPolyline = amap.addPolyline(this.mOption);
    }

    public TraceOverlay(AMap amap) {
        this.mAMap = amap;
        options();
    }

    public void add(List<LatLng> segments) {
        if (segments != null) {
            if (segments.size() != 0) {
                this.mTracedList.addAll(segments);
                options();
                if (this.mPolyline == null) {
                    this.mPolyline = this.mAMap.addPolyline(this.mOption);
                }
                this.mPolyline.setPoints(this.mTracedList);
            }
        }
    }

    public void remove() {
        Polyline polyline = this.mPolyline;
        if (polyline != null) {
            polyline.remove();
        }
    }

    public void setProperCamera(List<LatLng> lists) {
        Builder builder = LatLngBounds.builder();
        if (lists != null) {
            if (lists.size() != 0) {
                for (LatLng latlng : lists) {
                    builder.include(latlng);
                }
                try {
                    this.mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 20));
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void zoopToSpan() {
        setProperCamera(this.mOption.getPoints());
    }

    private PolylineOptions options() {
        if (this.mOption == null) {
            PolylineOptions polylineOptions = new PolylineOptions();
            this.mOption = polylineOptions;
            polylineOptions.setCustomTexture(BitmapDescriptorFactory.fromAsset("tracelinetexture.png"));
            this.mOption.width(40.0f);
        }
        return this.mOption;
    }

    public int getTraceStatus() {
        return this.mTraceStatus;
    }

    public void setTraceStatus(int mTraceStatus) {
        this.mTraceStatus = mTraceStatus;
    }

    public int getDistance() {
        return this.mDistance;
    }

    public void setDistance(int mDistance) {
        this.mDistance = mDistance;
    }

    public int getWaitTime() {
        return this.mWaitTime;
    }

    public void setWaitTime(int mWaitTime) {
        this.mWaitTime = mWaitTime;
    }
}
