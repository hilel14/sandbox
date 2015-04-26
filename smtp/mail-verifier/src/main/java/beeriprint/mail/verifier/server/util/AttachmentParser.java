/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.mail.verifier.server.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extracts attachments names from an email message.
 *
 * @author hilel
 */
public class AttachmentParser {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentParser.class);

    /**
     * Iterate through each part of a multi-part message body.
     *
     * @param message the original email message
     * @return set of attachment names
     * @throws IOException
     * @throws MessagingException
     */
    public Set<String> parseMessage(MimeMessage message) throws IOException, MessagingException {
        Set<String> attachments = new HashSet<>();
        Object content = message.getContent();
        if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                handlePart(multipart.getBodyPart(i),attachments);
            }
        }
        return attachments;
    }

    /**
     *
     * @param part a part of a multi-part body of a message. Attachment
     * disposition is parts are collected, all other types are ignored.
     * @throws MessagingException
     * @throws IOException
     */
    private void handlePart(BodyPart part, Set<String> attachments) throws MessagingException, IOException {
        String disposition = part.getDisposition();
        String contentType = part.getContentType();
        if (disposition == null) {
            // this is just body
        } else if (disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
            attachments.add(MimeUtility.decodeText(part.getFileName()).toLowerCase());
        } else if (disposition.equalsIgnoreCase(Part.INLINE)) {
            // ignore inline attachments
        } else {
            // this should never happen
        }
    }

}
