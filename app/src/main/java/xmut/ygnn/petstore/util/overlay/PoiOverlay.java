package xmut.ygnn.petstore.util.overlay;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.PoiItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PoiOverlay {
    private AMap mAMap;
    private ArrayList<Marker> mPoiMarks = new ArrayList();
    private List<PoiItem> mPois;

    public PoiOverlay(AMap amap, List<PoiItem> pois) {
        this.mAMap = amap;
        this.mPois = pois;
    }

    public void addToMap() {
        int i = 0;
        while (i < this.mPois.size()) {
            try {
                Marker marker = this.mAMap.addMarker(getMarkerOptions(i));
                marker.setObject(Integer.valueOf(i));
                this.mPoiMarks.add(marker);
                i++;
            } catch (Throwable e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void removeFromMap() {
        Iterator it = this.mPoiMarks.iterator();
        while (it.hasNext()) {
            ((Marker) it.next()).remove();
        }
    }

    public void zoomToSpan() {
        try {
            List list = this.mPois;
            if (list != null && list.size() > 0) {
                if (this.mAMap != null) {
                    if (this.mPois.size() == 1) {
                        this.mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(((PoiItem) this.mPois.get(0)).getLatLonPoint().getLatitude(), ((PoiItem) this.mPois.get(0)).getLatLonPoint().getLongitude()), 18.0f));
                    } else {
                        this.mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(getLatLngBounds(), 5));
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private LatLngBounds getLatLngBounds() {
        Builder b = LatLngBounds.builder();
        for (int i = 0; i < this.mPois.size(); i++) {
            b.include(new LatLng(((PoiItem) this.mPois.get(i)).getLatLonPoint().getLatitude(), ((PoiItem) this.mPois.get(i)).getLatLonPoint().getLongitude()));
        }
        return b.build();
    }

    private MarkerOptions getMarkerOptions(int index) {
        return new MarkerOptions().position(new LatLng(((PoiItem) this.mPois.get(index)).getLatLonPoint().getLatitude(), ((PoiItem) this.mPois.get(index)).getLatLonPoint().getLongitude())).title(getTitle(index)).snippet(getSnippet(index)).icon(getBitmapDescriptor(index));
    }

    protected BitmapDescriptor getBitmapDescriptor(int index) {
        return null;
    }

    protected String getTitle(int index) {
        return ((PoiItem) this.mPois.get(index)).getTitle();
    }

    protected String getSnippet(int index) {
        return ((PoiItem) this.mPois.get(index)).getSnippet();
    }

    public int getPoiIndex(Marker marker) {
        for (int i = 0; i < this.mPoiMarks.size(); i++) {
            if (((Marker) this.mPoiMarks.get(i)).equals(marker)) {
                return i;
            }
        }
        return -1;
    }

    public PoiItem getPoiItem(int index) {
        if (index >= 0) {
            if (index < this.mPois.size()) {
                return (PoiItem) this.mPois.get(index);
            }
        }
        return null;
    }
}
