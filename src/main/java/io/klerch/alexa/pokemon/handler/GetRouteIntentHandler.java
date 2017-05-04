package io.klerch.alexa.pokemon.handler;

import com.google.maps.errors.ApiException;
import io.klerch.alexa.pokemon.api.CityMapper;
import io.klerch.alexa.pokemon.api.What3Words;
import io.klerch.alexa.state.utils.AlexaStateException;
import io.klerch.alexa.tellask.model.AlexaInput;
import io.klerch.alexa.tellask.model.AlexaOutput;
import io.klerch.alexa.tellask.schema.AlexaIntentHandler;
import io.klerch.alexa.tellask.schema.annotation.AlexaIntentListener;
import io.klerch.alexa.tellask.util.AlexaRequestHandlerException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Optional;

@AlexaIntentListener(customIntents = "GetRouteIntent")
public class GetRouteIntentHandler implements AlexaIntentHandler {
    @Override
    public AlexaOutput handleRequest(AlexaInput input) throws AlexaRequestHandlerException, AlexaStateException {
        final String s1 = input.getSlotValue("wordOne");
        final String s2 = input.getSlotValue("wordTwo");
        final String s3 = input.getSlotValue("wordThree");

        try {
            final Optional<Pair<Double, Double>> longlat = What3Words.resolve(s1, s2, s3);
            if (longlat.isPresent()) {
                final String ssml = CityMapper.getRouteToPokemon(longlat.get().getLeft(), longlat.get().getRight());

                if (StringUtils.isNotBlank(ssml)) {
                    return AlexaOutput.tell("SayFoundLocation")
                            .putSlot("ssml", "Here is your direction to " + s1 + " " + s2  + " " + s3  + ". " + ssml)
                            .withReprompt(true)
                            .build();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return AlexaOutput.tell("SayFoundNoLocation")
                .putSlot("s1", s1)
                .putSlot("s2", s1)
                .putSlot("s3", s1)
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
