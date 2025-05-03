package no.skattetaten.testutvikling.grunnlag.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OppgaveoppsummeringDTO {

    @NotNull
    @Min(0)
    private int sumSaldo;

    @NotNull
    @Min(0)
    private int sumAksjehandel;

    public OppgaveoppsummeringDTO() {
    }

    public int getSumSaldo() {
        return sumSaldo;
    }

    public void setSumSaldo(int sumSaldo) {
        this.sumSaldo = sumSaldo;
    }

    public int getSumAksjehandel() {
        return sumAksjehandel;
    }

    public void setSumAksjehandel(int sumAksjehandel) {
        this.sumAksjehandel = sumAksjehandel;
    }
}
