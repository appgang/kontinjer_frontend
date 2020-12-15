package com.example.test.ui.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.test.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    ArrayList<HashMap<String, String>> map = new ArrayList<HashMap<String, String>>();
    GoogleMap mMap;
    MapView mapView;

    private HomeViewModel homeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        MapView mapView = root.findViewById(R.id.mapa);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        this.mapView=mapView;
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final GoogleMap mMap = googleMap;
        this.mMap=googleMap;
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("coordinates");
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), R.raw.map_style));

            if (!success) {
                Log.e("mapa", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("mapa", "Can't find style. Error: ", e);
        }

        myRef.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                map = (ArrayList<HashMap<String, String>>) dataSnapshot.getValue();
                for (HashMap<String, String> t : map) {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(t.get("latitude")), Double.parseDouble(t.get("longitude")))).title(t.get("name")));
                }
            }

            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        LatLng skopje = new LatLng(41.9979484, 21.4333326);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(skopje, 13.26f));
    }
    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


}
