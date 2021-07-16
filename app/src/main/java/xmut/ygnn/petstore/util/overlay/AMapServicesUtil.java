package xmut.ygnn.petstore.util.overlay;

import android.graphics.Bitmap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class AMapServicesUtil {
    public static int BUFFER_SIZE = 2048;

    AMapServicesUtil() {
    }

    public static byte[] inputStreamToByte(InputStream in) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        while (true) {
            int read = in.read(data, 0, BUFFER_SIZE);
            int count = read;
            if (read == -1) {
                return outStream.toByteArray();
            }
            outStream.write(data, 0, count);
        }
    }

    public static LatLonPoint convertToLatLonPoint(LatLng latlon) {
        return new LatLonPoint(latlon.latitude, latlon.longitude);
    }

    public static LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }

    public static ArrayList<LatLng> convertArrList(List<LatLonPoint> shapes) {
        ArrayList<LatLng> lineShapes = new ArrayList();
        for (LatLonPoint point : shapes) {
            lineShapes.add(convertToLatLng(point));
        }
        return lineShapes;
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, float res) {
        if (bitmap == null) {
            return null;
        }
        return Bitmap.createScaledBitmap(bitmap, (int) (((float) bitmap.getWidth()) * res), (int) (((float) bitmap.getHeight()) * res), true);
    }
}
