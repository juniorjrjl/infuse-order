# Projeto proposto para oportunidade na Infuse

API que consome JSON e XML para salvar pedidos e consulta-los

![technology Java](https://img.shields.io/badge/techonolgy-Java-orange)
![technology Spark](https://img.shields.io/badge/techonolgy-Spark-blue)
![techonolgy Guice](https://img.shields.io/badge/techonolgy-Guice-green)
![technology JPA](https://img.shields.io/badge/techonolgy-JPA-blue)
![technology MySQL](https://img.shields.io/badge/techonolgy-MySQL-blue)


## Configurando ambiente

### banco de dados

Informe no arquivo persistence.xml as configurações de do banco de dados para comunicação do Hibernate.

Na classe main insira as mesmas informações no método responsavel por executar as migrations pelo Liquibase (localizadas em resources/db/changelogs/migrations)

obs: caso tenha docker instalado no computador, basta apensar rodar o comando `docker-compose up --build` para subir um container do MySQL

## Sobre o projeto

É uma API usando Java 8 e spark (conforme solicitado nos requisitos https://docs.google.com/document/d/1cmPEg1w1B81Ri2k9KMrCsIDy_UUfex2H/edit)

A configuração de injeção de dependências é feita pela lib Guice do Google

O projeto está seguindo a arquitetura Hexagonal, onde temos um core dependente somente do Java e de nenhuma lib, esse core utiliza ports para se comunicar com o banco de dados.

Temos testes unitários cpom JUnit5 e o Jacoco para verificar a cobertura