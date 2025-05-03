package no.skattetaten.testutvikling.grunnlag.validator;

import no.skattetaten.testutvikling.grunnlag.model.data.Grunnlag;
import no.skattetaten.testutvikling.grunnlag.model.data.Oppgave;

public class GrunnlagValidator {

    public static void valider(Grunnlag grunnlag) {
        validerSaldoSum(grunnlag);
        validerAksjehandelSum(grunnlag);
    }

    private static void validerSaldoSum(Grunnlag grunnlag) {
        int sumSaldo = grunnlag.getOppgave().stream().mapToInt(Oppgave::getSaldo).sum();
        int forventetSumSaldo = grunnlag.getOppgaveoppsummering().getSumSaldo();
        if (sumSaldo != forventetSumSaldo) {
            throw new IllegalArgumentException(
                String.format("Summen av saldo (%d) fra alle oppgaver stemmer ikke overens med oppgitt sumSaldo (%d).",
                    sumSaldo, forventetSumSaldo));
        }
    }

    private static void validerAksjehandelSum(Grunnlag grunnlag) {
        int sumAksjehandel = grunnlag.getOppgave().stream().mapToInt(Oppgave::getAksjeandel).sum();
        int forventetSumAksjehandel = grunnlag.getOppgaveoppsummering().getSumAksjehandel();
        if (sumAksjehandel != forventetSumAksjehandel) {
            throw new IllegalArgumentException(
                String.format(
                    "Summen av aksjeandel (%d) fra alle oppgaver stemmer ikke overens med oppgitt sumAksjehandel (%d).",
                    sumAksjehandel, forventetSumAksjehandel));
        }
    }
}
