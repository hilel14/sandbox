package org.hilel14.sandbox.glacier.demo;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.transfer.ArchiveTransferManager;
import com.amazonaws.services.glacier.transfer.UploadResult;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hilel
 *
 */
public class GlacierClient {

    static final Logger LOGGER = Logger.getLogger(GlacierClient.class.getName());
    static final String END_POINT = "https://glacier.eu-west-1.amazonaws.com/";
    static final String VAULT_NAME = "Vault-1";

    public static void main(String[] args) {
        try {
            upload();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private static void upload() throws Exception {
        //AmazonGlacier client = AmazonGlacierClientBuilder.defaultClient();
        ProfileCredentialsProvider credentials = new ProfileCredentialsProvider();
        AmazonGlacierClient client = new AmazonGlacierClient(credentials);
        client.setEndpoint(END_POINT);
        ArchiveTransferManager manager = new ArchiveTransferManager(client, credentials);
        UploadResult result = manager.upload(VAULT_NAME, "archive-1", new File("/var/opt/data/1.jpg"));
        System.out.println("Archive ID: " + result.getArchiveId());
    }
}
