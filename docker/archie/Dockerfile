# Use an official CentOS runtime as a parent image
FROM centos:6

LABEL name="Archie image based on CentOS 6" \
    vendor="hilel14" \
    license="GPLv2" \
    build-date="20171230"
    
# Set the working directory
WORKDIR /root/work

# Copy the current directory contents into the container working directory
ADD . /root/work

# Install any needed packages and perform configuration tasks
RUN /root/work/setup.sh

# Make port 80 available to the world outside this container
EXPOSE 80

# Define environment variables
ENV MAVEN_HOME /opt/apache/maven
ENV PATH /opt/apache/maven/bin:$PATH

# Run init when the container launches
# CMD ["/bin/bash"]
ENTRYPOINT ["/sbin/init"]



# Usefull commands:

# sudo docker build -t local/archie .
# sudo docker run -d -p 4000:80 local/archie
# sudo docker ps
# sudo docker exec -it $CONTAINER_ID /bin/bash
