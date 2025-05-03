package no.skattetaten.testutvikling.grunnlag.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OppgaveDTO {

    @NotNull
    @Min(0)
    private int saldo;

    @NotNull
    @Min(0)
    private int aksjeandel;

    public OppgaveDTO() {
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getAksjeandel() {
        return aksjeandel;
    }

    public void setAksjeandel(int aksjeandel) {
        this.aksjeandel = aksjeandel;
    }
}
