package io.klerch.alexa.pokemon.handler;

import io.klerch.alexa.pokemon.api.CityMapper;
import io.klerch.alexa.pokemon.api.PokemonFinder;
import io.klerch.alexa.state.utils.AlexaStateException;
import io.klerch.alexa.tellask.model.AlexaInput;
import io.klerch.alexa.tellask.model.AlexaOutput;
import io.klerch.alexa.tellask.schema.AlexaIntentHandler;
import io.klerch.alexa.tellask.schema.annotation.AlexaIntentListener;
import io.klerch.alexa.tellask.util.AlexaRequestHandlerException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

@AlexaIntentListener(customIntents = "SearchForSpecificPokemonIntent")
public class SearchForSpecificPokemonIntentHandler implements AlexaIntentHandler {
    @Override
    public AlexaOutput handleRequest(AlexaInput input) throws AlexaRequestHandlerException, AlexaStateException {
        final String pokemon = input.getSlotValue("pokemon");

        final Optional<Pair<Double, Double>> longlat = PokemonFinder.find(pokemon);

        if (longlat.isPresent()) {
            final String ssml = CityMapper.getRouteToPokemon(longlat.get().getLeft(), longlat.get().getRight());

            if (!StringUtils.isNotBlank(ssml)) {
                return AlexaOutput.ask("SayFoundPokemon")
                        .putSlot("ssml", ssml)
                        .withReprompt(true)
                        .build();
            }
        }
        return AlexaOutput.ask("SayFoundNoPokemon")
                .putSlot("pokemon", pokemon)
                .build();
    }

    @Override
    public AlexaOutput handleError(final AlexaRequestHandlerException exception) {
        return AlexaOutput.tell("SaySorry").build();
    }

    @Override
    public boolean verify(final AlexaInput input) {
        return input.hasSlotNotBlank("pokemon");
    }
}
