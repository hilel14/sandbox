# FYI

This is my personal playground. 
It is just a conveniet place for me to play with new technologies.
It does not suppose to be used by (or even make sense to) anyone else.

### Markdown syntext

* https://help.github.com/articles/markdown-basics/
* https://help.github.com/articles/github-flavored-markdown/
* https://addons.mozilla.org/en-US/firefox/addon/markdown-viewer/

### Maven archetype creation:

http://maven.apache.org/archetype/maven-archetype-plugin/advanced-usage.html

1. mvn archetype:create-from-project
2. cd target/generated-sources/archetype
3. mvn install
4. cd ~/NetBeansProjects
5. mvn archetype:generate -DarchetypeCatalog=local
