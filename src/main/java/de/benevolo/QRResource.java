package de.benevolo;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.smallrye.common.annotation.Blocking;

import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/qrs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QRResource {

    @Inject Mailer mailer;

    @GET
    @Path("/{ticketId}/{buyerId}")
    @Produces("image/png")
    public byte[] generateQRCode(@PathParam("ticketId") String ticketId, @PathParam("buyerId") String buyerId) throws WriterException, IOException{
        Map<String, Object> qrData = new HashMap<>();
        qrData.put("ticket_id", ticketId);
        qrData.put("buyer_id", buyerId);
        qrData.put("concert_date", "2024-05-15");
        qrData.put("venue", "Konzertsaal A");
        qrData.put("seat", "Row 5, Seat 10");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(qrData);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitMatrix matrix = new QRCodeWriter().encode(json, BarcodeFormat.QR_CODE, 200, 200);
        MatrixToImageWriter.writeToStream(matrix, "PNG", baos);
        return baos.toByteArray();
    }

    @GET
    @Path("/mail")
    @Blocking
    public void sendEmail() {
        mailer.send(
                Mail.withText("example@example.com",
                        "Ahoy from Quarkus",
                        "A simple email sent from a Quarkus application."
                )
        );
    }

}

