package com.example.test.ui.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.test.Main2Activity;
import com.example.test.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    ArrayList<HashMap<String, String>> map = new ArrayList<HashMap<String, String>>();
    GoogleMap mMap;
    MapView mapView;
    boolean stakloBool = true;
    boolean plastikaBool = true;
    boolean kartonBool = true;
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
        final List<Marker> markers = new ArrayList<Marker>();
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

                System.out.println("first");
                map = (ArrayList<HashMap<String, String>>) dataSnapshot.getValue();
                final Chip chipPlastika;
                final Chip chipHartija;
                final Chip chipStaklo;
                try{
                    chipPlastika = getActivity().findViewById(R.id.plasticChip);
                    chipHartija = getActivity().findViewById(R.id.kartonChip);
                    chipStaklo = getActivity().findViewById(R.id.stakloChip);

                if(chipStaklo!=null) chipStaklo.toggle();
                chipPlastika.toggle();
                chipHartija.toggle();

                chipStaklo.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        stakloBool=!stakloBool;
                        Toast.makeText(getContext(), "User Click", Toast.LENGTH_LONG).show();
                        if(stakloBool){
                            chipStaklo.setChipBackgroundColorResource(R.color.stakloCrumb);
                        }
                        else if(!stakloBool) chipStaklo.setChipBackgroundColorResource(R.color.disabledCrumb);
                        for(Marker m : markers){
                            try{
                                if((m.getTitle().equals("staklo"))){
                                    m.setVisible(stakloBool);
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
                chipPlastika.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        plastikaBool=!plastikaBool;
                        Toast.makeText(getContext(), "User Click", Toast.LENGTH_LONG).show();
                        if(plastikaBool){
                            chipPlastika.setChipBackgroundColorResource(R.color.plasticaCrumb);
                        }
                        else if(!plastikaBool) chipPlastika.setChipBackgroundColorResource(R.color.disabledCrumb);
                        for(Marker m : markers){
                            try{
                                if((m.getTitle().equals("plastika"))){
                                    m.setVisible(plastikaBool);
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
                chipHartija.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        kartonBool=!kartonBool;
                        Toast.makeText(getContext(), "User Click", Toast.LENGTH_LONG).show();
                        if(kartonBool){
                            chipHartija.setChipBackgroundColorResource(R.color.hartijaCrumb);
                        }
                        else if(!kartonBool) chipHartija.setChipBackgroundColorResource(R.color.disabledCrumb);
                        for(Marker m : markers){
                            try{
                                if((m.getTitle().equals("hartija"))){
                                    m.setVisible(kartonBool);
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
                }
                catch(NullPointerException e){
                    e.getStackTrace();
                }
                for (HashMap<String, String> t : map) {

                    if(t.get("name").equals("СТАКЛО")){
                        markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(t.get("latitude")), Double.parseDouble(t.get("longitude")))).title("staklo").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))));
                    }
                    else if(t.get("name").equals("ПЛАСТИКА, ЛИМЕНКИ")){
                        markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(t.get("latitude")), Double.parseDouble(t.get("longitude")))).title("plastika").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
                    }
                    else if(t.get("name").equals("ХАРТИЈА, КОМПОЗИТ")){
                        markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(t.get("latitude")), Double.parseDouble(t.get("longitude")))).title("hartija").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))));
                    }

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
