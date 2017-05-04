package io.klerch.alexa.pokemon.api;

import java.io.IOException;

import org.junit.Test;

import com.google.maps.errors.ApiException;

public class CityMapperTest {
	
	@Test
	public void testCityMapper() throws ApiException, InterruptedException, IOException {
		String response = CityMapper.getRouteToPokemon(-0.128069, 51.508039);
		System.err.println(response);
	}

}
