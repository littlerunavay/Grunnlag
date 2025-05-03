package no.skattetaten.testutvikling.grunnlag.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static no.skattetaten.testutvikling.grunnlag.TestDataProvider.SUM_AKSJEHANDEL;
import static no.skattetaten.testutvikling.grunnlag.TestDataProvider.SUM_SALDO;
import static no.skattetaten.testutvikling.grunnlag.TestDataProvider.byggGrunnlag;
import static no.skattetaten.testutvikling.grunnlag.TestDataProvider.byggOppgave;

import java.util.List;

import org.junit.jupiter.api.Test;

import no.skattetaten.testutvikling.grunnlag.model.data.Grunnlag;

class GrunnlagValidatorTest {

    @Test
    void validerMedRiktigeSummer() {
        Grunnlag grunnlag = byggGrunnlag(List.of(byggOppgave(500, 1000), byggOppgave(500, 1000)));
        assertDoesNotThrow(() -> GrunnlagValidator.valider(grunnlag));
    }

    @Test
    void validerKasterExcaptionNaarSumSaldoIkkeStemmer() {
        Grunnlag grunnlag = byggGrunnlag(List.of(byggOppgave(500, 1000), byggOppgave(50, 1000)));

        IllegalArgumentException unntak =
            assertThrows(IllegalArgumentException.class, () -> GrunnlagValidator.valider(grunnlag));
        assertEquals(
            String.format("Summen av saldo (%d) fra alle oppgaver stemmer ikke overens med oppgitt sumSaldo (%d).", 550,
                SUM_SALDO),
            unntak.getMessage());
    }

    @Test
    void validerKasterExcaptionNaarSumAksjehandelIkkeStemmer() {
        Grunnlag grunnlag = byggGrunnlag(List.of(byggOppgave(500, 1000), byggOppgave(500, 100)));

        IllegalArgumentException unntak =
            assertThrows(IllegalArgumentException.class, () -> GrunnlagValidator.valider(grunnlag));
        assertEquals(String.format(
                "Summen av aksjeandel (%d) fra alle oppgaver stemmer ikke overens med oppgitt sumAksjehandel (%d).",
                1100,
                SUM_AKSJEHANDEL),
            unntak.getMessage());
    }

}
