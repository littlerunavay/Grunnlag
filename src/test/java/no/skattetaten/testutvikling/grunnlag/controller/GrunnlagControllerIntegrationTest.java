package no.skattetaten.testutvikling.grunnlag.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static no.skattetaten.testutvikling.grunnlag.TestDataProvider.FOEDSELSNUMMER;
import static no.skattetaten.testutvikling.grunnlag.TestDataProvider.NAVN;
import static no.skattetaten.testutvikling.grunnlag.TestDataProvider.byggGrunnlagDTO;
import static no.skattetaten.testutvikling.grunnlag.TestDataProvider.byggInnsenderDTO;
import static no.skattetaten.testutvikling.grunnlag.TestDataProvider.byggOppgaveDTO;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import no.skattetaten.testutvikling.grunnlag.BaseTest;
import no.skattetaten.testutvikling.grunnlag.model.dto.GrunnlagDTO;

@SpringBootTest
class GrunnlagControllerIntegrationTest extends BaseTest {

    private static final String GRUNNLAG_STI = "/api/grunnlag";
    private static final String FOEDSELSNUMMER_STI = "/{foedselsnummer}";

    @AfterEach
    void ryddOpp() throws Exception {
        fjernGrunnlag(FOEDSELSNUMMER);
    }

    @Test
    void hentGrunnlag() throws Exception {
        GrunnlagDTO grunnlagDTO = registrerOgReturnerGrunnlag(byggGrunnlagDTO());

        ResultActions resultat = hentGrunnlag(FOEDSELSNUMMER);

        sammenlignResponsMedForventetGrunnlagDTO(resultat, grunnlagDTO);
    }

    @Test
    void hentGrunnlagReturnerer404NaarIkkeFinnes() throws Exception {
        sjekkHentGrunnlagReturnererForventetStatus(FOEDSELSNUMMER, status().isNotFound());
    }

    @Test
    void registrerGrunnlag() throws Exception {
        GrunnlagDTO grunnlagDTO = byggGrunnlagDTO();

        ResultActions resultat = registrerGrunnlag(grunnlagDTO);

        sammenlignResponsMedForventetGrunnlagDTO(resultat, grunnlagDTO);
    }

    @Test
    void registrerGrunnlagReturnerer400NaarSumSaldoIkkeStemmer() throws Exception {
        GrunnlagDTO grunnlagDTO = byggGrunnlagDTO(List.of(byggOppgaveDTO(500, 1000), byggOppgaveDTO(50, 1000)));

        sjekkRegistrerGrunnlagReturnererForventetStatus(grunnlagDTO, status().isBadRequest());
    }

    @Test
    void registrerGrunnlagReturnerer400NaarSumAksjehandelIkkeStemmer() throws Exception {
        GrunnlagDTO grunnlagDTO = byggGrunnlagDTO(List.of(byggOppgaveDTO(500, 1000), byggOppgaveDTO(500, 100)));

        sjekkRegistrerGrunnlagReturnererForventetStatus(grunnlagDTO, status().isBadRequest());
    }

    @Test
    void registrerGrunnlagReturnerer400NaarFoedselsnummerErUgyldig() throws Exception {
        GrunnlagDTO grunnlagDTO = byggGrunnlagDTO(byggInnsenderDTO(NAVN, "1234"));

        String repons =
            sjekkRegistrerGrunnlagReturnererForventetStatusOgReturnerRespons(grunnlagDTO, status().isBadRequest());
        assertTrue(repons.contains("Fødselsnummer må være nøyaktig 11 sifre"));
    }

    @Test
    void registrerGrunnlagReturnerer400NaarNavnErForKort() throws Exception {
        GrunnlagDTO grunnlagDTO = byggGrunnlagDTO(byggInnsenderDTO("E", FOEDSELSNUMMER));

        String repons =
            sjekkRegistrerGrunnlagReturnererForventetStatusOgReturnerRespons(grunnlagDTO, status().isBadRequest());
        assertTrue(repons.contains("Navn må være minst 2 tegn"));
    }

    @Test
    void fjernGrunnlag() throws Exception {
        registrerOgReturnerGrunnlag(byggGrunnlagDTO());

        fjernGrunnlag(FOEDSELSNUMMER);

        sjekkHentGrunnlagReturnererForventetStatus(FOEDSELSNUMMER, status().isNotFound());
    }

    private GrunnlagDTO registrerOgReturnerGrunnlag(GrunnlagDTO grunnlagDTO) throws Exception {
        MvcResult resultat = getMockMvc().perform(hentPostBuilder(GRUNNLAG_STI, grunnlagDTO))
            .andExpect(status().isCreated())
            .andReturn();
        return parseResponsTilObjekt(resultat, GrunnlagDTO.class);
    }

    private ResultActions registrerGrunnlag(GrunnlagDTO grunnlagDTO) throws Exception {
        return getMockMvc().perform(hentPostBuilder(GRUNNLAG_STI, grunnlagDTO))
            .andExpect(status().isCreated());
    }

    private ResultActions hentGrunnlag(String foedselsnummer) throws Exception {
        return getMockMvc().perform(hentGetBuilder(GRUNNLAG_STI + FOEDSELSNUMMER_STI, foedselsnummer))
            .andExpect(status().isOk());
    }

    private void fjernGrunnlag(String foedselsnummer) throws Exception {
        getMockMvc().perform(hentDeleteBuilder(GRUNNLAG_STI + FOEDSELSNUMMER_STI, foedselsnummer))
            .andExpect(status().isNoContent());
    }

    private void sjekkHentGrunnlagReturnererForventetStatus(String foedselsnummer, ResultMatcher status)
        throws Exception {
        getMockMvc().perform(hentGetBuilder(GRUNNLAG_STI + FOEDSELSNUMMER_STI, foedselsnummer))
            .andExpect(status);
    }

    private void sjekkRegistrerGrunnlagReturnererForventetStatus(GrunnlagDTO grunnlagDTO, ResultMatcher status)
        throws Exception {
        getMockMvc().perform(hentPostBuilder(GRUNNLAG_STI, grunnlagDTO)).andExpect(status);
    }

    private String sjekkRegistrerGrunnlagReturnererForventetStatusOgReturnerRespons(GrunnlagDTO grunnlagDTO,
        ResultMatcher status)
        throws Exception {
        return getMockMvc().perform(hentPostBuilder(GRUNNLAG_STI, grunnlagDTO)).andExpect(status).andReturn()
            .getResponse().getContentAsString();
    }

    private void sammenlignResponsMedForventetGrunnlagDTO(ResultActions resultat, GrunnlagDTO grunnlagDTO)
        throws Exception {
        resultat
            .andExpect(jsonPath("$.innsender").isNotEmpty())
            .andExpect(jsonPath("$.oppgaveoppsummering").isNotEmpty())
            .andExpect(jsonPath("$.oppgave").isNotEmpty())
            .andExpect(jsonPath("$.oppgave").isArray())
            .andExpect(jsonPath("$.innsender.navn").value(grunnlagDTO.getInnsender().getNavn()))
            .andExpect(jsonPath("$.innsender.foedselsnummer").value(grunnlagDTO.getInnsender().getFoedselsnummer()))
            .andExpect(
                jsonPath("$.oppgaveoppsummering.sumSaldo").value(grunnlagDTO.getOppgaveoppsummering().getSumSaldo()))
            .andExpect(jsonPath("$.oppgaveoppsummering.sumAksjehandel").value(
                grunnlagDTO.getOppgaveoppsummering().getSumAksjehandel()))
            .andExpect(jsonPath("$.oppgave.length()").value(grunnlagDTO.getOppgave().size()));

        for (int i = 0; i < grunnlagDTO.getOppgave().size(); i++) {
            resultat
                .andExpect(jsonPath("$.oppgave[" + i + "].saldo").value(
                    grunnlagDTO.getOppgave().get(i).getSaldo()))
                .andExpect(jsonPath("$.oppgave[" + i + "].aksjeandel").value(
                    grunnlagDTO.getOppgave().get(i).getAksjeandel()));
        }
    }
}
