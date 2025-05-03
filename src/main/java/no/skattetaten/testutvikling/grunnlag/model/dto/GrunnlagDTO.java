package no.skattetaten.testutvikling.grunnlag.model.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class GrunnlagDTO {

    @Valid
    @NotNull
    private InnsenderDTO innsender;

    @Valid
    @NotNull
    private List<OppgaveDTO> oppgave;

    @Valid
    @NotNull
    private OppgaveoppsummeringDTO oppgaveoppsummering;

    public GrunnlagDTO() {
    }
    
    public InnsenderDTO getInnsender() {
        return innsender;
    }

    public void setInnsender(InnsenderDTO innsender) {
        this.innsender = innsender;
    }

    public OppgaveoppsummeringDTO getOppgaveoppsummering() {
        return oppgaveoppsummering;
    }

    public void setOppgaveoppsummering(
        OppgaveoppsummeringDTO oppgaveoppsummering) {
        this.oppgaveoppsummering = oppgaveoppsummering;
    }

    public List<OppgaveDTO> getOppgave() {
        return oppgave;
    }

    public void setOppgave(List<OppgaveDTO> oppgave) {
        this.oppgave = oppgave;
    }
}
