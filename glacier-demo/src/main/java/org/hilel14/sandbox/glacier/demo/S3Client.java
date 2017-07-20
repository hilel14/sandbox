package org.hilel14.sandbox.glacier.demo;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hilel
 */
public class S3Client {

    static final Logger LOGGER = Logger.getLogger(S3Client.class.getName());

    public static void main(String[] args) {
        try {
            listOpjects("hilel14-bucket-1");
            getOpject("hilel14-bucket-1", "Bull.jpg");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private static void listOpjects(String bucket) throws IOException {
        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        ObjectListing listing = s3.listObjects(bucket);
        List<S3ObjectSummary> summary = listing.getObjectSummaries();
        for (S3ObjectSummary o : summary) {
            //System.out.println(o);
            System.out.println(o.getKey());
        }
    }

    private static void getOpject(String bucket, String key) throws IOException {
        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        S3Object o = s3.getObject(bucket, key);
        try (
                S3ObjectInputStream inStream = o.getObjectContent();
                FileOutputStream outStream = new FileOutputStream(new File("/tmp/s3/" + key));) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, len);
            }
        }
        s3.deleteObject(bucket, key);
    }
}
