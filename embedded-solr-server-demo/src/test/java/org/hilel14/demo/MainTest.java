package org.hilel14.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author hilel
 */
public class MainTest {

    static final Logger LOGGER = Logger.getLogger(MainTest.class);
    static SolrClient client;

    @BeforeClass
    public static void setUpClass() throws IOException {
        String solrHome = "src/test/resources/server";
        String defaultCoreName = "books";
        Path dataDir = Paths.get("/tmp/solr-test");
        initDataDir(dataDir);
        client = new EmbeddedSolrServer(Paths.get(solrHome), defaultCoreName);
        LOGGER.info("Solr home: " + solrHome);
        LOGGER.info("Core name: " + defaultCoreName);
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        client.close();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddDocument() throws Exception {
        LOGGER.info("testAddDocument");
        // make sure index is empty
        SolrDocumentList list = getAllDocuments();
        Assert.assertTrue(list.getNumFound() == 0);
        // add document to index
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", 1);
        doc.addField("title", "One");
        client.add(doc);
        client.commit();
        // test
        list = getAllDocuments();
        Assert.assertTrue(list.getNumFound() == 1);
        int id = Integer.parseInt(list.get(0).get("id").toString());
        Assert.assertTrue(id == 1);
    }

    @Test
    public void testUpdateDocument() throws Exception {
        LOGGER.info("testUpdateDocument");
        // make sure index contains the document added before
        SolrDocumentList list = getAllDocuments();
        Assert.assertTrue(list.getNumFound() == 1);
        // change document's title
        SolrInputDocument input = new SolrInputDocument();
        input.addField("id", 1);
        Map<String, Object> update = new HashMap<>();
        update.put("set", "New title");
        input.addField("title", update);
        client.add(input);
        client.commit();
        // test
        list = getAllDocuments();
        Assert.assertTrue(list.getNumFound() == 1);
        SolrDocument doc = list.get(0);
        int id = Integer.parseInt(doc.get("id").toString());
        Assert.assertTrue(id == 1);
        Assert.assertTrue(doc.get("title").equals("New title"));
    }

    @Test
    public void testDeleteDocument() throws Exception {
        LOGGER.info("testDeleteDocument");
        // make sure index contains the document added before
        SolrDocumentList list = getAllDocuments();
        Assert.assertTrue(list.getNumFound() == 1);
        // delete the document
        client.deleteById("1");
        client.commit();
        // test
        list = getAllDocuments();
        Assert.assertTrue(list.getNumFound() == 0);
    }

    static void initDataDir(Path dataDir) throws IOException {
        LOGGER.info("Preparing data dir " + dataDir);
        if (Files.exists(dataDir)) {
            try (Stream<Path> walk = Files.walk(dataDir)) {
                walk.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        //.peek(System.out::println)
                        .forEach(File::delete);
            }
        }
        Files.createDirectories(dataDir);
    }

    private SolrDocumentList getAllDocuments() throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery();
        query.set("q", "id:*");
        query.setFields("id", "title");
        QueryResponse response = client.query(query);
        SolrDocumentList list = response.getResults();
        LOGGER.debug(list.getNumFound() + " documents found");
        for (int i = 0; i < list.getNumFound(); i++) {
            SolrDocument doc = list.get(i);
            LOGGER.debug("id = " + doc.get("id"));
            LOGGER.debug(doc.getFieldNames());
        }
        return list;
    }

}
