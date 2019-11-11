FROM php:7.3-apache
# Update package lists
RUN apt-get update
RUN mkdir -p /usr/share/man/man1
# Install mcrypt
RUN apt-get -y install openssl
# Configure apache2
COPY certificates /home/certificates
COPY apache /etc/apache2/sites-available/000-default.conf
# Copy WebApp to /var/www/html
COPY web /var/www/html
# Change ownership of /var/www
RUN chown www-data /var/www/ -R
# Change permissions of /var/www
RUN chmod 775 /var/www/ -R
# Enable mods
RUN a2enmod headers
RUN a2enmod ssl
# Restart webserver
RUN service apache2 restart