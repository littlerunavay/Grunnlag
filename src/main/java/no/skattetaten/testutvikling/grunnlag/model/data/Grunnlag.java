package no.skattetaten.testutvikling.grunnlag.model.data;

import java.util.List;

public class Grunnlag {

    private Innsender innsender;

    private List<Oppgave> oppgave;

    private Oppgaveoppsummering oppgaveoppsummering;

    public Innsender getInnsender() {
        return innsender;
    }

    public void setInnsender(Innsender innsender) {
        this.innsender = innsender;
    }

    public List<Oppgave> getOppgave() {
        return oppgave;
    }

    public void setOppgave(List<Oppgave> oppgave) {
        this.oppgave = oppgave;
    }

    public Oppgaveoppsummering getOppgaveoppsummering() {
        return oppgaveoppsummering;
    }

    public void setOppgaveoppsummering(Oppgaveoppsummering oppgaveoppsummering) {
        this.oppgaveoppsummering = oppgaveoppsummering;
    }
}