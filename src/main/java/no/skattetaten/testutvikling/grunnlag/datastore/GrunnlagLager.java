package no.skattetaten.testutvikling.grunnlag.datastore;

import no.skattetaten.testutvikling.grunnlag.model.data.Grunnlag;

public interface GrunnlagLager {

    Grunnlag hent(String foedselsnummer);

    void leggTil(String foedselsnummer, Grunnlag grunnlag);

    void fjern(String foedselsnummer);
}
