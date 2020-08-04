# Setup

Extract solr-x.y.z.tgz and cd to the extracted folder

bin/solr start
bin/solr create -c books
curl http://localhost:8983/solr/books/schema -H 'Content-type:application/json' -d '{ "add-field": { "name": "title", "type": "string" } }'
bin/solr stop

SOLR_HOME=src/test/resources/server
mkdir $SOLR_HOME
mv server/solr/solr.xml $SOLR_HOME
mkdir $SOLR_HOME/books
mv server/solr/books/conf $SOLR_HOME/books
mv server/solr/books/core.properties $SOLR_HOME/books
echo "solr.data.dir=/tmp/solr-test" >> $SOLR_HOME/books/core.properties
