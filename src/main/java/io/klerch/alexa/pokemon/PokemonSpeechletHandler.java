package io.klerch.alexa.pokemon;

import io.klerch.alexa.tellask.model.wrapper.AlexaRequestStreamHandler;
import io.klerch.alexa.tellask.schema.UtteranceReader;
import io.klerch.alexa.tellask.util.resource.ResourceUtteranceReader;

import java.util.Collections;
import java.util.Set;

public class PokemonSpeechletHandler extends AlexaRequestStreamHandler {
    @Override
    public Set<String> getSupportedApplicationIds() {
        return Collections.singleton(SkillConfig.getAlexaAppId());
    }

    @Override
    public UtteranceReader getUtteranceReader() {
        return new ResourceUtteranceReader("out/");
    }
}
