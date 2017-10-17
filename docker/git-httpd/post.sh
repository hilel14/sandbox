# create first user
htpasswd -c /var/lib/git/.htpasswd user1

# create first project
git init --bare /var/lib/git/project-one.git
echo "Project One" > /var/lib/git/project-one.git/description
chown -R apache /var/lib/git/project-one.git
