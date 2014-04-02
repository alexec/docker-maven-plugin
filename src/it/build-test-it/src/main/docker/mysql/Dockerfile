FROM centos

VOLUME /var/lib/mysql

RUN yum -y install mysql-server
RUN touch /etc/sysconfig/network

#RUN sed -i -e"s/^bind-address\s*=\s*127.0.0.1/bind-address = 0.0.0.0/" /etc/my.cnf

RUN service mysqld start &&  \
	sleep 5s && \
	mysql -e "GRANT ALL ON *.* to 'root'@'%'; FLUSH PRIVILEGES"

EXPOSE 3306

CMD ["/usr/bin/mysqld_safe"]