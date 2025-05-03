package no.skattetaten.testutvikling.grunnlag.datastore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import no.skattetaten.testutvikling.grunnlag.model.data.Grunnlag;

@Component
public class InMemoryGrunnlagLager implements GrunnlagLager {

    private static final Map<String, Grunnlag> GRUNNLAG_DATABASE = new ConcurrentHashMap<>();

    @Override
    public Grunnlag hent(String foedselsnummer) {
        return GRUNNLAG_DATABASE.get(foedselsnummer);
    }

    @Override
    public void leggTil(String foedselsnummer, Grunnlag grunnlag) {
        GRUNNLAG_DATABASE.put(foedselsnummer, grunnlag);
    }

    @Override
    public void fjern(String foedselsnummer) {
        GRUNNLAG_DATABASE.remove(foedselsnummer);
    }
}
