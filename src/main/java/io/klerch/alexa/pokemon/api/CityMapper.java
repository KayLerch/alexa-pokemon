package io.klerch.alexa.pokemon.api;

import java.io.IOException;

import io.klerch.alexa.pokemon.SkillConfig;
import org.joda.time.DateTime;

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

	private static final double originLongitude = -0.098242;

	private static final double originLatitude =  51.521591;

	public static String getRouteToPokemon(final double longitude, final double latitude) {
		try {
			GeoApiContext context = new GeoApiContext().setApiKey(SkillConfig.getProperty("GoogleApiKey"));
			DateTime now = new DateTime();
			DirectionsResult result = DirectionsApi.newRequest(context).mode(TravelMode.TRANSIT)
					.origin(new LatLng(originLatitude, originLongitude)).destination(new LatLng(latitude, longitude))
					.departureTime(now).await();

			String endAddress = null;
			String transitDepart = null;
			String transitArrive = null;

			if (result.routes.length > 0) {
				DirectionsRoute route1 = result.routes[0];
				if (route1.legs.length > 0) {
					int numLegs = route1.legs.length;
					DirectionsLeg lastLeg = route1.legs[numLegs - 1];

					endAddress = lastLeg.endAddress;
					endAddress = endAddress.replaceAll("(.*?)[,].*", "$1");
				}
			}

			

			for (DirectionsRoute route : result.routes) {
				for (DirectionsLeg leg : route.legs) {

					for (DirectionsStep step : leg.steps) {

						if (step.transitDetails != null) {
							transitDepart = step.transitDetails.departureStop.name;
							transitArrive = step.transitDetails.arrivalStop.name;
							break;

						}
					}
				}
			}

			StringBuilder builder = new StringBuilder();
			builder.append("Your directions are " + endAddress + ".");
			if (transitDepart != null && transitArrive != null) {
				builder.append(
						" Your journey begins at " + transitDepart + ", from where you will go to " + transitArrive);
			}

			return builder.toString();
		} catch (Exception e) {
			return "I could not find any directions.";
		}
	}
}
