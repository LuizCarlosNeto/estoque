#### Projeto estoque ####

#Para criar base estoque para o mySQL rodar o seguinte sql
create database esquema

#Clonar o projeto
git clone https://github.com/aguiartiago/estoque.git

#Configurar o projeto para o eclipse
mvn eclipse:clean && mvn eclipse:eclipse

#Construir o projeto no maven 3
mvn clean install

#Subir o projeto no jetty
mvn jetty:run

#Acessar no navegador
localhost:8080/estoque

=======
