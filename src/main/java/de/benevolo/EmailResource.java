package de.benevolo;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/email")
public class EmailResource {

    @Inject
    Template emailTemplate;

    @Inject
    Mailer mailer;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public void createEmail(EmailRequest emailRequest) {
        TemplateInstance templateInstance = emailTemplate
                .data("headline", emailRequest.getHeadline())
                .data("emailSubject", emailRequest.getSubject())
                .data("content", emailRequest.getContent());

        String renderedContent = templateInstance.render();

        mailer.send(
                Mail.withHtml(emailRequest.getRecipientEmail(), emailRequest.getSubject(), renderedContent)
        );
    }
}
