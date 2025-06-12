package com.example.pruebamapas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var btnNavegar: Button
    private lateinit var markerCersia: Marker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        btnNavegar = findViewById(R.id.btn_navegar)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val santiago = LatLng(42.8782, -8.5448)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(santiago, 13f))

        val cersia = LatLng(42.885379650416255, -8.516780818558765)
        markerCersia = mMap.addMarker(
            MarkerOptions()
                .position(cersia)
                .title("Edificio CERSIA")

        )!!

        mMap.setOnMarkerClickListener { marker ->
            if (marker == markerCersia) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cersia, 17f))
                btnNavegar.visibility = Button.VISIBLE
                true
            } else {
                false
            }
        }
        mMap.setOnMapClickListener {
            btnNavegar.visibility = Button.GONE
        }
        btnNavegar.setOnClickListener {
            val uri = "https://www.google.com/maps/dir/?api=1&destination=42.858710,-8.562530".toUri()
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }
    }
}
