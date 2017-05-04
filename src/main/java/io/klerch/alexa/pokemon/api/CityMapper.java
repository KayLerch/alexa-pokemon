package io.klerch.alexa.pokemon.api;

import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.Test;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

public class CityMapper {

	private static final double originLongitude = -0.136138;

	private static final double originLatitude = 51.510453;
	

	public static String getRouteToPokemon(final double longitude, final double latitude) throws ApiException, InterruptedException, IOException {
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBiUikU1C1pYgqE5DWbFYqZGmn_PGktu2k");
		DateTime now = new DateTime();
		DirectionsResult result = DirectionsApi.newRequest(context).mode(TravelMode.TRANSIT)
				.origin(new LatLng(originLatitude, originLongitude))
				.destination(new LatLng(latitude, longitude)).departureTime(now).await();
		
		DirectionsRoute route1 = result.routes[0];
		int numLegs = route1.legs.length;
		DirectionsLeg lastLeg = route1.legs[numLegs - 1];
		
		String endAddress = lastLeg.endAddress;
		endAddress = endAddress.replaceAll("\\d*?(.*?)[,].*", "$1");
		
		System.err.println(endAddress);

		for (DirectionsRoute route : result.routes) {
			for (DirectionsLeg leg : route.legs) {
				System.err.println(leg.startAddress);
				System.err.println(leg.endAddress);

				for (DirectionsStep step : leg.steps) {
					System.err.println(step.travelMode);
					if (step.transitDetails != null) {
						System.err.println(step.transitDetails.departureStop.name);
						System.err.println(step.transitDetails.arrivalStop.name);
						System.err.println(step.transitDetails.line.name);

					}
				}
			}
		}

		return "Your nearest pokemon is at " + endAddress;
	}
}
