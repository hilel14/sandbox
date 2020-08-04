# Karaf setup
feature:repo-add camel
feature:install camel-blueprint camel-jackson

# ActiveMQ commands
activemq:producer --user karaf --password karaf --destination queue://my.test --messageCount 1 --message '{"title":"item 1", "data": ["a","b","c"]}'
activemq:browse --user karaf --password karaf my.test

# bundle commands
bundle:install --start mvn:org.hilel14/camel-splitter-demo/1.0-SNAPSHOT
camel:route-show camel-splitter-demo route-1
bundle:uninstall camel-splitter-demo
