#!/usr/bin/env bash
sudo apt-get update
echo "Installing Git.."
sudo apt-get install -y git
echo "Installing Maven.."
sudo apt-get install -y maven
echo "Installing Java 7.."
sudo apt-get install -y software-properties-common python-software-properties
echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
sudo add-apt-repository ppa:webupd8team/java -y
sudo apt-get update
sudo apt-get install oracle-java8-installer
echo "Setting environment variables for Java 8.."
sudo apt-get install -y oracle-java8-set-default
echo "install PostgreSQL"
sudo apt-get install postgresql postgresql-contrib -y
echo "set password for postgres"
sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'postgres';"
echo "clone from github"
sudo git clone https://github.com/kunaksergey/RestContacts.git
sudo "run script for create table"
sudo -u postgres psql < RestContacts/src/main/resources/postgress.sql
sudo "run script for inser date"
sudo -u postgres psql < RestContacts/src/main/resources/user_dataH2.sql
sudo "assemble pakage"
sudo mvn clean package -f RestContacts/pom.xml
sudo "run application"
sudo java -jar RestContacts/target/restContacts-1.0.1.jar
