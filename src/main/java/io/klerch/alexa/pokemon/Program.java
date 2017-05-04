package io.klerch.alexa.pokemon;

import io.klerch.alexa.pokemon.api.What3Words;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

/**
 * Created by kaylerch on 5/4/17.
 */
public class Program {

    public static void main(String[] args) {
        final Optional<Pair<Double, Double>> longlat = What3Words.resolve("swaps", "string", "bland");
        if (longlat.isPresent()) {
            System.out.println(longlat.get().getLeft());
            System.out.println(longlat.get().getRight());
        }
    }
}
