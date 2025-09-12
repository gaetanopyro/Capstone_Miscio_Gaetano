/*package gaetanomiscio.Capstone.tools;

import org.springframework.stereotype.Component;

@Component
public class MailgunSender {
    private String apiKey;
    private String domain;

    public MailgunSender(@Value("${mailgun.apikey}") String apiKey, @Value("${mailgun.domain}") String domain) {
        this.apiKey = apiKey;
        this.domain = domain;
    }

    public void sendRegistrationEmail(User recipient) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domain + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", "riccardo.gulin@gmail.com")
                .queryString("to", recipient.getEmail()) // Qua dobbiamo metterci un indirizzo email autorizzato a ricevere
                .queryString("subject", "Registrazione completata!")
                .queryString("text", "Benvenuto " + recipient.getName() + " sulla nostra piattaforma!")
                .asJson();
        System.out.println(response.getBody());
    }
}
*/