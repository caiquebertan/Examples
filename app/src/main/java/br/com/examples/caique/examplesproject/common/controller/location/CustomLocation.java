package br.com.examples.caique.examplesproject.common.controller.location;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import br.com.examples.caique.examplesproject.common.controller.PrefManager;
import de.greenrobot.event.EventBus;


public class CustomLocation implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

	private static final String TAG = CustomLocation.class.getSimpleName();
	private Context ctx;
	private MyLocationChangedDelegate locationDelegate;
	private GoogleApiClient mGoogleApiClient;
	private LocationRequest mLocationRequest;
	private Location location;
	private EventBus eventBus;

	public CustomLocation(Context ctx, MyLocationChangedDelegate delegate) {
		this.ctx = ctx;
		this.locationDelegate = delegate;
		eventBus = EventBus.getDefault();
		mGoogleApiClient = new GoogleApiClient.Builder(ctx).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
	}

	/**
	 * if the location is permitted, connects with the google api client. When is done, callback is called.
	 * @return itself
     */
	public CustomLocation connect() {
		try {
			if(PrefManager.getLocationPermissionGranted())
				mGoogleApiClient.connect();
		} catch (Exception e) {}
		return this;
	}

	/**
	 * Disconnect the google api client
	 */
	public void disconnect() {
		try {
			if(PrefManager.getLocationPermissionGranted())
				mGoogleApiClient.disconnect();
		} catch (Exception e) {}
	}

	@Override
	public void onConnected(Bundle bundle) {
		mLocationRequest = LocationRequest.create();
		mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
		.setFastestInterval(1000) //rode primeira vez com 1 segundo
		.setInterval(1000 * 60 * 60) //rode num intervalo de 1 hora
		.setSmallestDisplacement(100); // ou a cada 100 metros

		try {
			LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
		} catch (SecurityException e) {}
	}

	@Override
	public void onConnectionSuspended(int i) {
		Log.i(TAG, "GoogleApiClient connection has been suspend");
		Location location = null;

		try {
			location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
		} catch (SecurityException e) {}

		if (location != null) {
			onLocationChanged(location);
		}
		else {
			onLocationChanged(getLastKnowLocation(ctx));
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Log.i(TAG, "GoogleApiClient connection has failed");
		Location location = null;
		try {
			location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
		} catch (SecurityException e) {}

		if (location != null) {
			onLocationChanged(location);
		}
		else {
			onLocationChanged(getLastKnowLocation(ctx));
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.v(TAG, "onLocationChanged()");
		locationDelegate.onLocationChanged(location);
		eventBus.post(location);
		this.location = location;
	}

	public Location getLastLocation() {
		return location;
	}

	/**
	 * static method to get the last know location from device, if it exists
	 * @param ctx
	 * @return the last location if it exists, if not returns null
     */
	public static Location getLastKnowLocation(Context ctx) {
		try {
			LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			String bestProvider = lm.getBestProvider(criteria, false);
			Log.v(TAG, "bestProvider: " + bestProvider);
			if (bestProvider != null) {
				Location location = lm.getLastKnownLocation(bestProvider);
				if (location == null) {
					if ("network".equals(bestProvider)) {
						bestProvider = "gps";
					} else {
						bestProvider = "network";
					}
				}
				location = lm.getLastKnownLocation(bestProvider);
				return location;
			}
		} catch (SecurityException e) {}
		return null;
	}

	public interface MyLocationChangedDelegate {
		void onLocationChanged(Location location);
	}
}