# Notes

### markdown syntext

* https://help.github.com/articles/markdown-basics/
* https://help.github.com/articles/github-flavored-markdown/

### Maven archetype creation:

http://maven.apache.org/archetype/maven-archetype-plugin/advanced-usage.html

* mvn archetype:create-from-project
* cd target/generated-sources/archetype
* mvn install
* cd ~/NetBeansProjects
* mvn archetype:generate -DarchetypeCatalog=local
