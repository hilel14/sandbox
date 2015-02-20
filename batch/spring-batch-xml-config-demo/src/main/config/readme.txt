Create batch database and tables
Create product table in test database
Create user1 with password 123456
Grant user1 access to batch database and test database

Run the job:
org.springframework.batch.core.launch.support.CommandLineJobRunner classpath:/job1.xml job1 inputFile=/var/opt/data/sample-data-2.csv r=1