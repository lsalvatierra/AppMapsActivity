package idat.edu.pe.appmapsactivity;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //click sobre el mapa
        mMap.setOnMapClickListener(this);
        //eventos de marcadores
        mMap.setOnMarkerDragListener(this);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        Marker markerSidney = mMap.addMarker(new
                MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
                .snippet("Ahora mismo no estamos aqui")
                .draggable(true)
                .flat(true)
                //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location))
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // Customise the styling of the base map using a JSON object defined
        // in a string resource file. First create a MapStyleOptions object
        // from the JSON styles string, then pass this to the setMapStyle
        // method of the GoogleMap object.
        boolean success = mMap.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_json)));
        if (!success) {
            Log.e("TAG_STYLE", "Style parsing failed.");
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

        mMap.addMarker(
                new MarkerOptions().position(latLng)
                .title("Nuevo marker")
                .draggable(true)

        );
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        marker.hideInfoWindow();
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng posicion = marker.getPosition();
        marker.setSnippet(posicion.latitude+", "+posicion.longitude);
        marker.showInfoWindow();
    }
}
