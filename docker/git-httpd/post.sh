# create first user
htpasswd -c /var/www/git/.htpasswd user1

# create first project
git init --bare /var/www/git/project-one.git
chown -R apache /var/www/git/project-one.git
