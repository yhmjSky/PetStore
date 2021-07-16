package xmut.ygnn.petstore.util.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import xmut.ygnn.petstore.R;
import java.util.ArrayList;
import java.util.List;

public class RouteOverlay {
    protected List<Polyline> allPolyLines = new ArrayList();
    private Bitmap busBit;
    private Bitmap driveBit;
    private Bitmap endBit;
    protected Marker endMarker;
    protected LatLng endPoint;
    protected AMap mAMap;
    private Context mContext;
    protected boolean nodeIconVisible = true;
    private Bitmap startBit;
    protected Marker startMarker;
    protected LatLng startPoint;
    protected List<Marker> stationMarkers = new ArrayList();
    private Bitmap walkBit;

    public RouteOverlay(Context context) {
        this.mContext = context;
    }

    public void removeFromMap() {
        Marker marker = this.startMarker;
        if (marker != null) {
            marker.remove();
        }
        marker = this.endMarker;
        if (marker != null) {
            marker.remove();
        }
        for (Marker marker2 : this.stationMarkers) {
            marker2.remove();
        }
        for (Polyline line : this.allPolyLines) {
            line.remove();
        }
        destroyBit();
    }

    private void destroyBit() {
        Bitmap bitmap = this.startBit;
        if (bitmap != null) {
            bitmap.recycle();
            this.startBit = null;
        }
        bitmap = this.endBit;
        if (bitmap != null) {
            bitmap.recycle();
            this.endBit = null;
        }
        bitmap = this.busBit;
        if (bitmap != null) {
            bitmap.recycle();
            this.busBit = null;
        }
        bitmap = this.walkBit;
        if (bitmap != null) {
            bitmap.recycle();
            this.walkBit = null;
        }
        bitmap = this.driveBit;
        if (bitmap != null) {
            bitmap.recycle();
            this.driveBit = null;
        }
    }

    protected BitmapDescriptor getStartBitmapDescriptor() {
        return BitmapDescriptorFactory.fromResource(R.drawable.amap_start);
    }

    protected BitmapDescriptor getEndBitmapDescriptor() {
        return BitmapDescriptorFactory.fromResource(R.drawable.amap_end);
    }

    protected BitmapDescriptor getBusBitmapDescriptor() {
        return BitmapDescriptorFactory.fromResource(R.drawable.amap_bus);
    }

    protected BitmapDescriptor getWalkBitmapDescriptor() {
        return BitmapDescriptorFactory.fromResource(R.drawable.amap_man);
    }

    protected BitmapDescriptor getDriveBitmapDescriptor() {
        return BitmapDescriptorFactory.fromResource(R.drawable.amap_car);
    }

    protected void addStartAndEndMarker() {
        this.startMarker = this.mAMap.addMarker(new MarkerOptions().position(this.startPoint).icon(getStartBitmapDescriptor()).title("起点"));
        this.endMarker = this.mAMap.addMarker(new MarkerOptions().position(this.endPoint).icon(getEndBitmapDescriptor()).title("终点"));
    }

    public void zoomToSpan() {
        if (this.startPoint != null && this.mAMap != null) {
            try {
                this.mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(getLatLngBounds(), 100));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    protected LatLngBounds getLatLngBounds() {
        Builder b = LatLngBounds.builder();
        b.include(new LatLng(this.startPoint.latitude, this.startPoint.longitude));
        b.include(new LatLng(this.endPoint.latitude, this.endPoint.longitude));
        return b.build();
    }

    public void setNodeIconVisibility(boolean visible) {
        try {
            this.nodeIconVisible = visible;
            List list = this.stationMarkers;
            if (list != null && list.size() > 0) {
                for (int i = 0; i < this.stationMarkers.size(); i++) {
                    ((Marker) this.stationMarkers.get(i)).setVisible(visible);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    protected void addStationMarker(MarkerOptions options) {
        if (options != null) {
            Marker marker = this.mAMap.addMarker(options);
            if (marker != null) {
                this.stationMarkers.add(marker);
            }
        }
    }

    protected void addPolyLine(PolylineOptions options) {
        if (options != null) {
            Polyline polyline = this.mAMap.addPolyline(options);
            if (polyline != null) {
                this.allPolyLines.add(polyline);
            }
        }
    }

    protected float getRouteWidth() {
        return 18.0f;
    }

    protected int getWalkColor() {
        return Color.parseColor("#6db74d");
    }

    protected int getBusColor() {
        return Color.parseColor("#537edc");
    }

    protected int getDriveColor() {
        return Color.parseColor("#537edc");
    }
}
