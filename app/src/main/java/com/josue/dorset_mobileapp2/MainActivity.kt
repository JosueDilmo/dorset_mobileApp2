package com.josue.dorset_mobileapp2

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.josue.dorset_mobileapp2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    //global variables
    private lateinit var binding: ActivityMainBinding
    private lateinit var map:GoogleMap
    private val LOCATION_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        //change text programmatically by view binding
        binding.helloWorldTextview.text = getString(R.string.hello_world)

        //fragment for the map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.maps_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //open fragment when app/activity is opened
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.main_fragment, MainFragment().apply {
                arguments = Bundle().apply {
                    putString("NEW_TEXT_HERE", "Text Changed in Fragment")
                }
            })
        }
    }


    //fun to open the map   /variable for google-maps
    override fun onMapReady(gm: GoogleMap) {
       map = gm

        //markers in MAP/ locations
        val sydney = LatLng(-34.0, 151.0)
        val brisbane = LatLng(-27.383333, 153.118332)

        //controls for zoom
        map.uiSettings.isZoomControlsEnabled = true

        //toolbar for +/- or compass
        map.uiSettings.isMapToolbarEnabled = false

        //Check permission to see phone location
        checkLocationPermission()

        //marker on the map
        map.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"))
        map.addMarker(
            MarkerOptions()
                .position(brisbane)
                .title("Marker in Brisbane")
        )

        //move camera to location specify
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        //camera zoom in to location specify
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(brisbane, 10F))

        //adding new marker to map and saving Name input
        map.setOnMapClickListener{ latlng ->
            val snackBar =
                Snackbar.make(binding.root,
                    "Do you want to add a marker?",
                    Snackbar.LENGTH_LONG)
            snackBar.setAction("Add"){
                map.addMarker(
                    MarkerOptions()
                        .position(latlng)
                        .title(binding.mapMarkerEditText.text.toString())
                )
                binding.mapMarkerEditText.setText("")
            }
            snackBar.show()
        }

    }

    //fun to check if permissions are already granted
    private fun checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //go check if location is enable
            enableLocation()
            return
        }

        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        requestPermissions(permissions, LOCATION_REQUEST_CODE)
    }

    //fun to check if location is enable
    @SuppressLint("MissingPermission")
    private fun enableLocation() {
        map.isMyLocationEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()){
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                            grantResults[1] == PackageManager.PERMISSION_GRANTED
                    ){
                        enableLocation()
                    } else Snackbar.make(
                        binding.root,
                        "Location features will now work",
                        Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

}

