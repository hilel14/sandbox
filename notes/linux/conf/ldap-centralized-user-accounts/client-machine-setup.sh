#!/bin/sh
#
# Client Machine Setup
#

# Gloval variables
domainAdmins=NegevAdmins

# Install required packages
yum install openldap-clients
yum remove sssd

# Configure client to use LDAP authentication
authconfig --enableldap --update

# Configure sudoers:
cat >  /etc/sudoers.d/$domainAdmins << "EOF"
## Allows people in group $domainAdmins to run all commands
%$domainAdmins  ALL=(ALL)       ALL
EOF
