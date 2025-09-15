package gaetanomiscio.Capstone.tools;

import gaetanomiscio.Capstone.entities.User;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class MailgunSender {
    private String apiKey;
    private String domain;
    //

    public MailgunSender(@Value("${mailgun.apikey}") String apiKey, @Value("${mailgun.domain}") String domain/*, @Value("${mailgun.sender.email}") String senderEmail*/) {
        this.apiKey = apiKey;
        this.domain = domain;
        //this.senderEmail = senderEmail;
    }

    public void sendRegistrationEmail(User recipient) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domain + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", "tanomiscio@gmail.com")
                .queryString("to", recipient.getEmail()) // Qua dobbiamo metterci un indirizzo email autorizzato a ricevere
                .queryString("subject", "Registrazione completata!")
                .queryString("text", "Benvenuto " + recipient.getUsername() + " sulla nostra piattaforma!")
                .asJson();
        System.out.println(response.getBody());
    }

    public void sendBillingEmail() {
    }
}
