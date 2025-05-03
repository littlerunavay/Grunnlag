package no.skattetaten.testutvikling.grunnlag.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import no.skattetaten.testutvikling.grunnlag.model.dto.GrunnlagDTO;
import no.skattetaten.testutvikling.grunnlag.service.GrunnlagService;

@RestController
@RequestMapping("/api/grunnlag")
public class GrunnlagController {

    private static final String FOEDSELSNUMMER_STI = "/{foedselsnummer}";
    private static final String FOEDSELSNUMMER_IDENTIFIKATOR = "foedselsnummer";

    private final GrunnlagService grunnlagService;

    @Autowired
    public GrunnlagController(GrunnlagService grunnlagService) {
        this.grunnlagService = grunnlagService;
    }

    @GetMapping(FOEDSELSNUMMER_STI)
    public ResponseEntity<GrunnlagDTO> hentGrunnlag(@PathVariable(FOEDSELSNUMMER_IDENTIFIKATOR) String foedselsnummer) {
        return new ResponseEntity<>(grunnlagService.hentGrunnlag(foedselsnummer), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GrunnlagDTO> registrerGrunnlag(@Valid @RequestBody GrunnlagDTO grunnlagDTO) {
        return new ResponseEntity<>(grunnlagService.registrerGrunnlag(grunnlagDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(FOEDSELSNUMMER_STI)
    public ResponseEntity<Void> fjernGrunnlag(@PathVariable(FOEDSELSNUMMER_IDENTIFIKATOR) String foedselsnummer) {
        grunnlagService.fjernGrunnlag(foedselsnummer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
