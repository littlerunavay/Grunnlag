package no.skattetaten.testutvikling.grunnlag.service;

import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.skattetaten.testutvikling.grunnlag.datastore.GrunnlagLager;
import no.skattetaten.testutvikling.grunnlag.model.data.Grunnlag;
import no.skattetaten.testutvikling.grunnlag.model.dto.GrunnlagDTO;
import no.skattetaten.testutvikling.grunnlag.validator.GrunnlagValidator;

@Service
public class GrunnlagService {

    private static final Logger LOG = LoggerFactory.getLogger(GrunnlagService.class);

    private final GrunnlagLager grunnlagLager;

    private final ModelMapper modelMapper;

    @Autowired
    public GrunnlagService(GrunnlagLager grunnlagLager, ModelMapper modelMapper) {
        this.grunnlagLager = grunnlagLager;
        this.modelMapper = modelMapper;
    }

    public GrunnlagDTO hentGrunnlag(String foedselsnummer) {
        LOG.info("Henter grunnlag for foedselsnummer {}", foedselsnummer);
        Grunnlag grunnlag = grunnlagLager.hent(foedselsnummer);
        if (grunnlag == null) {
            throw new NoSuchElementException("Grunnlag ikke funnet for foedselsnummer: " + foedselsnummer);
        }
        return modelMapper.map(grunnlag, GrunnlagDTO.class);
    }

    public GrunnlagDTO registrerGrunnlag(GrunnlagDTO grunnlagDTO) {
        LOG.info("Registrerer grunnlag");
        Grunnlag grunnlag = modelMapper.map(grunnlagDTO, Grunnlag.class);
        GrunnlagValidator.valider(grunnlag);
        grunnlagLager.leggTil(grunnlag.getInnsender().getFoedselsnummer(), grunnlag);
        return grunnlagDTO;
    }

    public void fjernGrunnlag(String foedselsnummer) {
        LOG.info("Fjerner grunnlag for foedselsnummer {}", foedselsnummer);
        grunnlagLager.fjern(foedselsnummer);
    }
}
