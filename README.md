# Notes

### Markdown syntext

* https://help.github.com/articles/markdown-basics/
* https://help.github.com/articles/github-flavored-markdown/

### Maven archetype creation:

http://maven.apache.org/archetype/maven-archetype-plugin/advanced-usage.html

1. mvn archetype:create-from-project
2. cd target/generated-sources/archetype
3. mvn install
4. cd ~/NetBeansProjects
5. mvn archetype:generate -DarchetypeCatalog=local
