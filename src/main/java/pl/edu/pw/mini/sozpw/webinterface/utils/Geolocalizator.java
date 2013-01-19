package pl.edu.pw.mini.sozpw.webinterface.utils;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.user.client.Window;
import com.google.maps.gwt.client.Geocoder;
import com.google.maps.gwt.client.Geocoder.Callback;
import com.google.maps.gwt.client.GeocoderAddressComponent;
import com.google.maps.gwt.client.GeocoderRequest;
import com.google.maps.gwt.client.GeocoderResult;
import com.google.maps.gwt.client.GeocoderStatus;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;

public class Geolocalizator {
	
	private static final LatLng defaultPosition = LatLng.create(52.229830, 21.011733);
	
	public interface GeoCallback{
		void geocodeSuccess(String location);
	}
	
	public static void putStringLocation(LatLng position, final GeoCallback geoCallback){
		
		Geocoder geocoder = Geocoder.create();
		GeocoderRequest gr = GeocoderRequest.create();
		gr.setLocation(position);
		
		geocoder.geocode(gr, new Callback() {
			public void handle(JsArray<GeocoderResult> results, GeocoderStatus status) {
				if (status == GeocoderStatus.OK) {
					if (results.length() > 0) {
						GeocoderResult geocoderResult = results.get(0);
						StringBuffer sb = new StringBuffer();
						JsArray<GeocoderAddressComponent> addressComponents = geocoderResult
								.getAddressComponents();

						for (int i = 0; i < addressComponents.length(); i++) {
							if (i > 0) {
								sb.append(", ");
							}
							sb.append(addressComponents.get(i).getLongName());
						}

						geoCallback.geocodeSuccess(sb.toString());
						
					} else {
						Window.alert("No results found");
					}
				} else {
					Window.alert("Geocode failed due to: " + status);
				}
			}
		});
		
	}
	
	public static void setToDefaultPosition(final GoogleMap map){
		map.panTo(defaultPosition);
	}
	
	public static void setToCurrentLatLng(final GoogleMap map){
		if (Geolocation.isSupported()) {
			Geolocation.getIfSupported().getCurrentPosition(
					new com.google.gwt.core.client.Callback<Position, PositionError>() {
						@Override
						public void onSuccess(Position result) {
							double lat = result.getCoordinates().getLatitude();
							double lng = result.getCoordinates().getLongitude();
							map.panTo(LatLng.create(lat, lng));
						}

						@Override
						public void onFailure(PositionError reason) {
							Window.alert("Geolocation.getIfSupported().getCurrentPosition service failed.");
						}
					});
		} else {
			Window.alert("Twoja przeglądarka nie wspiera geolokalizacji.");
		}
	}

}
