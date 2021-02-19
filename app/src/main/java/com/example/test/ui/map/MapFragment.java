package com.example.test.ui.map;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.test.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    ArrayList<HashMap<String, String>> map = new ArrayList<HashMap<String, String>>();
    GoogleMap mMap;
    MapView mapView;
    boolean stakloBool = true;
    boolean plastikaBool = true;
    boolean kartonBool = true;
    boolean pickupBool = true;
    boolean locationPermissionGranted = true;
    private MapViewModel mapViewModel;
    Double l, t;
    Activity v;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            v = (Activity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        v = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapView mapView = v.findViewById(R.id.mapa);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        this.mapView = mapView;
        getDeviceLocation();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel =
                ViewModelProviders.of(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        return root;
    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            System.out.println("TRUE");
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(v,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
    }

    private void getDeviceLocation() {
        Double latitude, longitude;
        System.out.println("GETTING LOCATION");

        try {
            if (locationPermissionGranted) {
                System.out.println("PERMISISON GRANTED");
                LocationManager lm = (LocationManager) v.getSystemService(Context.LOCATION_SERVICE);
                //noinspection MissingPermission
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location == null) {
                    System.out.println("LOCATON NULL");
                    final LocationListener locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(final Location location) {

                            // getting location of user
                            Double latitude, longitude;
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            System.out.println(longitude.toString() + latitude.toString());
                            l = latitude;
                            t = longitude;
                            // do something with Latlng
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                            // do something
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                            // notify user "GPS or Network provider" not available
                        }
                    };
                    //noinspection MissingPermission
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 500, locationListener);
                    //noinspection MissingPermission
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 500, locationListener);
                } else {
                    System.out.println("LOCATON TRUE");
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    System.out.println(longitude.toString() + latitude.toString());
                    l = latitude;
                    t = longitude;
                }
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        final GoogleMap mMap = googleMap;
        final List<Marker> markers = new ArrayList<Marker>();
        this.mMap = googleMap;
        getLocationPermission();
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("coordinates");
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            v, R.raw.map_style));

            if (!success) {
                Log.e("mapa", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("mapa", "Can't find style. Error: ", e);
        }

        myRef.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    FloatingActionButton fab = v.findViewById(R.id.fab);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String, String> tempHash = new HashMap<String, String>();
                            tempHash.put("latitude", l.toString());
                            tempHash.put("longitude", t.toString());
                            tempHash.put("name", "pickup");
                            int tempS = map.size();
                            myRef.child(String.valueOf(tempS)).setValue(tempHash);

                        }
                    });
                    FloatingActionButton fab_locate = v.findViewById(R.id.fab_locate);
                    fab_locate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "Locating..", Toast.LENGTH_LONG).show();
                            LatLng locate = new LatLng(l, t);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locate, 17));
                        }
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                if (v != null) {
                    map = (ArrayList<HashMap<String, String>>) dataSnapshot.getValue();
                    final Chip chipPlastika;
                    final Chip chipHartija;
                    final Chip chipStaklo;
                    final Chip chipPickup;

                    chipPlastika = v.findViewById(R.id.plasticChip);
                    chipHartija = v.findViewById(R.id.kartonChip);
                    chipStaklo = v.findViewById(R.id.stakloChip);
                    chipPickup = v.findViewById(R.id.chipPickup);

                    chipStaklo.toggle();
                    chipPlastika.toggle();
                    chipHartija.toggle();
                    chipPickup.toggle();

                    chipStaklo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            stakloBool = !stakloBool;
                            if (stakloBool) {
                                chipStaklo.setChipBackgroundColorResource(R.color.stakloCrumb);
                            } else if (!stakloBool)
                                chipStaklo.setChipBackgroundColorResource(R.color.disabledCrumb);
                            for (Marker m : markers) {
                                try {
                                    if ((m.getTitle().equals("staklo"))) {
                                        m.setVisible(stakloBool);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    chipPlastika.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            plastikaBool = !plastikaBool;
                            if (plastikaBool) {
                                chipPlastika.setChipBackgroundColorResource(R.color.plasticaCrumb);
                            } else if (!plastikaBool)
                                chipPlastika.setChipBackgroundColorResource(R.color.disabledCrumb);
                            for (Marker m : markers) {
                                try {
                                    if ((m.getTitle().equals("plastika"))) {
                                        m.setVisible(plastikaBool);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    chipHartija.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            kartonBool = !kartonBool;
                            if (kartonBool) {
                                chipHartija.setChipBackgroundColorResource(R.color.hartijaCrumb);
                            } else if (!kartonBool)
                                chipHartija.setChipBackgroundColorResource(R.color.disabledCrumb);
                            for (Marker m : markers) {
                                try {
                                    if ((m.getTitle().equals("hartija"))) {
                                        m.setVisible(kartonBool);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    chipPickup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickupBool = !pickupBool;
                            if (pickupBool) {
                                chipPickup.setChipBackgroundColorResource(R.color.pickupCrumb);
                            } else if (!pickupBool)
                                chipPickup.setChipBackgroundColorResource(R.color.disabledCrumb);
                            for (Marker m : markers) {
                                try {
                                    if ((m.getTitle().equals("pickup"))) {
                                        m.setVisible(pickupBool);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else {

                }



                for (HashMap<String, String> t : map) {
                    try {
                        if (t.get("name") != null) {
                            if (t.get("name").equals("СТАКЛО")) {
                                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(t.get("latitude")), Double.parseDouble(t.get("longitude")))).title("staklo").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))));
                            } else if (t.get("name").equals("ПЛАСТИКА, ЛИМЕНКИ")) {
                                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(t.get("latitude")), Double.parseDouble(t.get("longitude")))).title("plastika").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
                            } else if (t.get("name").equals("ХАРТИЈА, КОМПОЗИТ")) {
                                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(t.get("latitude")), Double.parseDouble(t.get("longitude")))).title("hartija").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))));
                            } else if (t.get("name").equals("pickup")) {
                                markers.add(mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(t.get("latitude")), Double.parseDouble(t.get("longitude")))).title("pickup").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))));
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
