package no.skattetaten.testutvikling.grunnlag;

import java.util.List;

import no.skattetaten.testutvikling.grunnlag.model.data.Grunnlag;
import no.skattetaten.testutvikling.grunnlag.model.data.Innsender;
import no.skattetaten.testutvikling.grunnlag.model.data.Oppgave;
import no.skattetaten.testutvikling.grunnlag.model.data.Oppgaveoppsummering;
import no.skattetaten.testutvikling.grunnlag.model.dto.GrunnlagDTO;
import no.skattetaten.testutvikling.grunnlag.model.dto.InnsenderDTO;
import no.skattetaten.testutvikling.grunnlag.model.dto.OppgaveDTO;
import no.skattetaten.testutvikling.grunnlag.model.dto.OppgaveoppsummeringDTO;

public class TestDataProvider {

    public static final int SUM_SALDO = 1000;
    public static final int SUM_AKSJEHANDEL = 2000;
    public static final String FOEDSELSNUMMER = "26063643458";
    public static final String NAVN = "Ole Olsen";

    public static Grunnlag byggGrunnlag() {
        Grunnlag grunnlag = new Grunnlag();
        grunnlag.setInnsender(byggInnsender(NAVN, FOEDSELSNUMMER));
        grunnlag.setOppgave(List.of(byggOppgave(SUM_SALDO, SUM_AKSJEHANDEL)));
        grunnlag.setOppgaveoppsummering(byggOppgaveoppsummering(SUM_SALDO, SUM_AKSJEHANDEL));
        return grunnlag;
    }

    public static Grunnlag byggGrunnlag(List<Oppgave> oppgaver) {
        Grunnlag grunnlag = new Grunnlag();
        grunnlag.setInnsender(byggInnsender(NAVN, FOEDSELSNUMMER));
        grunnlag.setOppgave(oppgaver);
        grunnlag.setOppgaveoppsummering(byggOppgaveoppsummering(SUM_SALDO, SUM_AKSJEHANDEL));
        return grunnlag;
    }

    public static Grunnlag byggGrunnlag(Innsender innsender, List<Oppgave> oppgave,
        Oppgaveoppsummering oppgaveoppsummering) {
        Grunnlag grunnlag = new Grunnlag();
        grunnlag.setInnsender(innsender);
        grunnlag.setOppgave(oppgave);
        grunnlag.setOppgaveoppsummering(oppgaveoppsummering);
        return grunnlag;
    }

    public static Innsender byggInnsender(String navn, String foedselsnummer) {
        Innsender innsender = new Innsender();
        innsender.setNavn(navn);
        innsender.setFoedselsnummer(foedselsnummer);
        return innsender;
    }

    public static Oppgave byggOppgave(int saldo, int aksjeandel) {
        Oppgave oppgave = new Oppgave();
        oppgave.setSaldo(saldo);
        oppgave.setAksjeandel(aksjeandel);
        return oppgave;
    }

    public static Oppgaveoppsummering byggOppgaveoppsummering(int sumSaldo, int sumAksjehandel) {
        Oppgaveoppsummering oppgaveoppsummering = new Oppgaveoppsummering();
        oppgaveoppsummering.setSumSaldo(sumSaldo);
        oppgaveoppsummering.setSumAksjehandel(sumAksjehandel);
        return oppgaveoppsummering;
    }

    public static GrunnlagDTO byggGrunnlagDTO(InnsenderDTO innsender) {
        GrunnlagDTO grunnlag = new GrunnlagDTO();
        grunnlag.setInnsender(innsender);
        grunnlag.setOppgave(List.of(byggOppgaveDTO(SUM_SALDO, SUM_AKSJEHANDEL)));
        grunnlag.setOppgaveoppsummering(byggOppgaveoppsummeringDTO(SUM_SALDO, SUM_AKSJEHANDEL));
        return grunnlag;
    }

    public static GrunnlagDTO byggGrunnlagDTO() {
        GrunnlagDTO grunnlag = new GrunnlagDTO();
        grunnlag.setInnsender(byggInnsenderDTO(NAVN, FOEDSELSNUMMER));
        grunnlag.setOppgave(List.of(byggOppgaveDTO(SUM_SALDO, SUM_AKSJEHANDEL)));
        grunnlag.setOppgaveoppsummering(byggOppgaveoppsummeringDTO(SUM_SALDO, SUM_AKSJEHANDEL));
        return grunnlag;
    }

    public static GrunnlagDTO byggGrunnlagDTO(List<OppgaveDTO> oppgaver) {
        GrunnlagDTO grunnlag = new GrunnlagDTO();
        grunnlag.setInnsender(byggInnsenderDTO(NAVN, FOEDSELSNUMMER));
        grunnlag.setOppgave(oppgaver);
        grunnlag.setOppgaveoppsummering(byggOppgaveoppsummeringDTO(SUM_SALDO, SUM_AKSJEHANDEL));
        return grunnlag;
    }

    public static GrunnlagDTO byggGrunnlagDTO(InnsenderDTO innsender, List<OppgaveDTO> oppgave,
        OppgaveoppsummeringDTO oppgaveoppsummering) {
        GrunnlagDTO grunnlag = new GrunnlagDTO();
        grunnlag.setInnsender(innsender);
        grunnlag.setOppgave(oppgave);
        grunnlag.setOppgaveoppsummering(oppgaveoppsummering);
        return grunnlag;
    }

    public static InnsenderDTO byggInnsenderDTO(String navn, String foedselsnummer) {
        InnsenderDTO innsender = new InnsenderDTO();
        innsender.setNavn(navn);
        innsender.setFoedselsnummer(foedselsnummer);
        return innsender;
    }

    public static OppgaveDTO byggOppgaveDTO(int saldo, int aksjeandel) {
        OppgaveDTO oppgave = new OppgaveDTO();
        oppgave.setSaldo(saldo);
        oppgave.setAksjeandel(aksjeandel);
        return oppgave;
    }

    public static OppgaveoppsummeringDTO byggOppgaveoppsummeringDTO(int sumSaldo, int sumAksjehandel) {
        OppgaveoppsummeringDTO oppgaveoppsummering = new OppgaveoppsummeringDTO();
        oppgaveoppsummering.setSumSaldo(sumSaldo);
        oppgaveoppsummering.setSumAksjehandel(sumAksjehandel);
        return oppgaveoppsummering;
    }

}

