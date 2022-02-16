package com.lachlan.kingofthecourt.fragments;


import static com.lachlan.kingofthecourt.BuildConfig.MAPS_API_KEY;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.lachlan.kingofthecourt.databinding.FragmentFinderBinding;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.ui.viewmodel.FinderViewModel;

import java.util.ArrayList;
import java.util.List;


public class FinderFragment extends Fragment implements OnMapReadyCallback {

    private FinderViewModel finderViewModel;
    private FragmentFinderBinding binding;
    private GoogleMap mMap;
    private MapView mMpaView;
    private PlacesClient placesClient;
    private boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastKnownLocation;
    private static final int DEFAULT_ZOOM = 15;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        finderViewModel =
                new ViewModelProvider(this).get(FinderViewModel.class);

        binding = FragmentFinderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        mMpaView = binding.mapView;
        mMpaView.onCreate(savedInstanceState);
        mMpaView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMpaView.getMapAsync(this);

        Places.initialize(getContext(), MAPS_API_KEY);
        placesClient = Places.createClient(getContext());

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();
        updateCourtLocations();

        NavController navController = NavHostFragment.findNavController(this);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                // Determine which court marker has been selected and instantiate the object
                Court court = finderViewModel.getCourtsList().getValue().get((int) marker.getTag());

                NavDirections nav = FinderFragmentDirections.actionNavigationFinderToNavigationCourt(court);
                navController.navigate(nav);
                return false;
            }
        });
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public void updateCourtLocations() {
        finderViewModel.getCourtsList().observe(this, new Observer<List<Court>>() {
            @Override
            public void onChanged(List<Court> courts) {
                for (Court court : courts) {
                    com.lachlan.kingofthecourt.data.entity.Location location = court.getLocation();
                    Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title(court.getLocationName()));
                    marker.setTag(courts.indexOf(court));
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null)
            return;

        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.e("DEFAULT", "Current location is null. Reverting to default.");
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-27.45, 153.055), 15));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMpaView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMpaView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMpaView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMpaView.onLowMemory();
    }


}