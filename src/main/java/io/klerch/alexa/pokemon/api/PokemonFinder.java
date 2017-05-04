package io.klerch.alexa.pokemon.api;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

public class PokemonFinder {

    public static Optional<Pair<Double, Double>> find(final String pokemon) {
        //TODO: find pokemon
        return Optional.of(new ImmutablePair<>(52.0, 12.0));
    }
}
