package io.klerch.alexa.pokemon.api;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.maps.errors.ApiException;



public class CityMapperTest {

	@Test
	public void testCityMapper() throws ApiException, InterruptedException, IOException {
		String response = CityMapper.getRouteToPokemon(-0.128069, 51.508039);
		Assert.assertEquals(
				"Your nearest pokemon is at 5 Trafalgar Square. Your journey begins at Piccadilly Circus Station, from where you will go to Charing Cross Underground Station",
				response);
	}

}
