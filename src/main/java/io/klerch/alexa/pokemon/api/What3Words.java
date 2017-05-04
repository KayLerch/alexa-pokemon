package io.klerch.alexa.pokemon.api;

import io.klerch.alexa.pokemon.SkillConfig;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;


public class What3Words {
    public static Optional<Pair<Double, Double>> resolve(final String s1, final String s2, final String s3) {

        try {
            final URI uri = new URIBuilder(SkillConfig.getProperty("What3WordsForwardEndpoint"))
                    .addParameter("addr", s1 + "." + s2 + "." + s3)
                    .addParameter("format", "xml")
                    .addParameter("key", SkillConfig.getProperty("What3WordsApiKey"))
                    .build();

            final HttpGet httpGet = new HttpGet(uri.toURL().toString());

            final HttpResponse response = HttpClientBuilder.create().build().execute(httpGet);

            Validate.inclusiveBetween(200, 399, response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());

            final String html = IOUtils.toString(response.getEntity().getContent());
            final Document doc = Jsoup.parse(html);

            final String latitude = doc.getElementsByTag("response").first()
                    .getElementsByTag("geometry").first()
                    .getElementsByTag("lat").first()
                    .text();

            final String longitude = doc.getElementsByTag("response").first()
                    .getElementsByTag("geometry").first()
                    .getElementsByTag("lng").first()
                    .text();

            return Optional.of(new ImmutablePair<>(Double.parseDouble(longitude), Double.parseDouble(latitude)));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
