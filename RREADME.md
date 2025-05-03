# Grunnlag REST API

### Løsningsbeskrivelse

Koden benytter seg av Java 21, Spring Boot og ModelMapper, som brukes for å konvertere objekter fra DTO til
dataobjekter.

Controller-klassen eksponerer tre endepunkter:

* POST /api/grunnlag
* GET /api/grunnlag/foedselsnummer
* DELETE /api/grunnlag/foedselsnummer

Data blir lagret i en `ConcurrentHashMap`. Hvis nye data med samme fødselsnummer blir lagt til, blir de overskrevet.
Hvis man prøver å fjerne et element med et ikke-eksisterende fødselsnummer, vil det bli returnert 204. Hvis man prøver å
hente et element med et ikke-eksisterende fødselsnummer, blir det kastet et unntak.

Spring Validator sjekker at alle objektene og feltene ikke er null, samt validerer at alle int-verdier har en verdi
større enn 0, at navn har minst 2 tegn, og at fødselsnummeret inneholder nøyaktig 11 tegn. I tillegg er
`GrunnlagValidator`-klassen ansvarlig for å sjekke om saldo og aksjehandler-sum er riktige.

Controller-klassen er dekket av integrasjonstester i klassen `GrunnlagControllerIntegrationTest`, og service- og
validator-klasser er dekket av enhetstester.