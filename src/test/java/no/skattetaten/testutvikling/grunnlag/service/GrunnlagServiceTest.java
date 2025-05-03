package no.skattetaten.testutvikling.grunnlag.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static no.skattetaten.testutvikling.grunnlag.TestDataProvider.FOEDSELSNUMMER;
import static no.skattetaten.testutvikling.grunnlag.TestDataProvider.SUM_AKSJEHANDEL;
import static no.skattetaten.testutvikling.grunnlag.TestDataProvider.SUM_SALDO;
import static no.skattetaten.testutvikling.grunnlag.TestDataProvider.byggGrunnlag;
import static no.skattetaten.testutvikling.grunnlag.TestDataProvider.byggOppgave;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import no.skattetaten.testutvikling.grunnlag.datastore.InMemoryGrunnlagLager;
import no.skattetaten.testutvikling.grunnlag.model.data.Grunnlag;
import no.skattetaten.testutvikling.grunnlag.model.dto.GrunnlagDTO;

@ExtendWith(MockitoExtension.class)
class GrunnlagServiceTest {

    @Mock
    private InMemoryGrunnlagLager grunnlagLager;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private GrunnlagService grunnlagService;

    @Test
    void hentGrunnlag() {
        Grunnlag grunnlag = byggGrunnlag();
        when(grunnlagLager.hent(FOEDSELSNUMMER)).thenReturn(grunnlag);
        when(modelMapper.map(grunnlag, GrunnlagDTO.class)).thenReturn(new GrunnlagDTO());

        GrunnlagDTO grunnlagDTO = grunnlagService.hentGrunnlag(FOEDSELSNUMMER);

        assertNotNull(grunnlagDTO);
    }

    @Test
    void hentGrunnlagKasterExceptionNaarFoedselsnummerIkkeFinnes() {
        when(grunnlagLager.hent(FOEDSELSNUMMER)).thenReturn(null);

        NoSuchElementException unntak = assertThrows(NoSuchElementException.class, () -> {
            grunnlagService.hentGrunnlag(FOEDSELSNUMMER);
        });
        assertEquals("Grunnlag ikke funnet for foedselsnummer: " + FOEDSELSNUMMER, unntak.getMessage());
    }

    @Test
    void fjernGrunnlag() {
        assertDoesNotThrow(() -> grunnlagService.fjernGrunnlag(FOEDSELSNUMMER));
        verify(grunnlagLager, times(1)).fjern(FOEDSELSNUMMER);
    }

    @Test
    void registrerGrunnlag() {
        GrunnlagDTO grunnlagDTO = new GrunnlagDTO();
        Grunnlag grunnlag = byggGrunnlag();
        when(modelMapper.map(grunnlagDTO, Grunnlag.class)).thenReturn(grunnlag);

        assertDoesNotThrow(() -> grunnlagService.registrerGrunnlag(grunnlagDTO));
        verify(grunnlagLager, times(1)).leggTil(FOEDSELSNUMMER, grunnlag);
    }

    @Test
    void registrerGrunnlagKasterExceptionNaarSumSaldoIkkeStemmer() {
        GrunnlagDTO grunnlagDTO = new GrunnlagDTO();
        when(modelMapper.map(grunnlagDTO, Grunnlag.class)).thenReturn(
            byggGrunnlag(List.of(byggOppgave(500, 1000), byggOppgave(50, 1000))));

        IllegalArgumentException unntak =
            assertThrows(IllegalArgumentException.class, () -> grunnlagService.registrerGrunnlag(grunnlagDTO));
        assertEquals(
            String.format("Summen av saldo (%d) fra alle oppgaver stemmer ikke overens med oppgitt sumSaldo (%d).", 550,
                SUM_SALDO),
            unntak.getMessage());
        verify(grunnlagLager, times(0)).leggTil(any(), any());
    }

    @Test
    void registrerGrunnlagKasterExceptionNaarSummAksjehandelIkkeStemmer() {
        GrunnlagDTO grunnlagDTO = new GrunnlagDTO();
        when(modelMapper.map(grunnlagDTO, Grunnlag.class)).thenReturn(
            byggGrunnlag(List.of(byggOppgave(500, 1000), byggOppgave(500, 100))));

        IllegalArgumentException unntak =
            assertThrows(IllegalArgumentException.class, () -> grunnlagService.registrerGrunnlag(grunnlagDTO));
        assertEquals(String.format(
                "Summen av aksjeandel (%d) fra alle oppgaver stemmer ikke overens med oppgitt sumAksjehandel (%d).",
                1100,
                SUM_AKSJEHANDEL),
            unntak.getMessage());
        verify(grunnlagLager, times(0)).leggTil(any(), any());
    }
}
