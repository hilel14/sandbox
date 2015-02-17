# sandbox
My github repository

Maven archetype creation:

http://maven.apache.org/archetype/maven-archetype-plugin/advanced-usage.html

mvn archetype:create-from-project

cd target/generated-sources/archetype

mvn install

cd /tmp

mvn archetype:generate -DarchetypeCatalog=local
