package no.skattetaten.testutvikling.grunnlag.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class InnsenderDTO {

    @NotNull
    @Size(min = 2, message = "Navn må være minst 2 tegn")
    private String navn;

    @NotNull
    @Size(min = 11, max = 11, message = "Fødselsnummer må være nøyaktig 11 sifre")
    private String foedselsnummer;

    public InnsenderDTO() {
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getFoedselsnummer() {
        return foedselsnummer;
    }

    public void setFoedselsnummer(String foedselsnummer) {
        this.foedselsnummer = foedselsnummer;
    }
}
