package io.klerch.alexa.pokemon.handler;

import io.klerch.alexa.pokemon.PokemonSpeechletHandler;
import io.klerch.alexa.pokemon.SkillConfig;
import io.klerch.alexa.test.asset.AlexaAsset;
import io.klerch.alexa.test.client.AlexaClient;
import io.klerch.alexa.test.client.endpoint.AlexaEndpoint;
import io.klerch.alexa.test.client.endpoint.AlexaRequestStreamHandlerEndpoint;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kaylerch on 5/4/17.
 */
public class GetRouteIntentTest {

    @Test
    @Ignore
    public void findPokemon() throws Exception {
        final AlexaEndpoint ep = AlexaRequestStreamHandlerEndpoint.create(PokemonSpeechletHandler.class).build();

        final AlexaClient client = AlexaClient.create(ep).withApplicationId(SkillConfig.getAlexaAppId()).withLocale("en-GB").build();


        client.startSession()
                .intent("GetRouteIntent", "s1", "swaps")
                    .assertMatches(AlexaAsset.OutputSpeechSsml, ".*pickachu.*")
                .done().endSession();

    }

}