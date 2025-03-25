# Fadesp - Desafio Técnico Nv1

O desafio envolve o desenvolvimento de uma API destinada a viabilizar o recebimento de pagamentos de débitos tanto de pessoas físicas quanto jurídicas. A API oferece a realização, consulta e exclusão de pagamentos, bem como a atualização do status do pagamento.

## Objetivo

Ao receber um pagamento, a API deverá armazená-lo no banco de dados com o status "Pendente de Processamento". Posteriormente, uma aplicação externa processará o pagamento e fará uma solicitação à API para atualizar o status do pagamento de "Pendente" para "Processado".

Além disso, a API deve permitir a listagem de todos os pagamentos recebidos e oferecer ao cliente a opção de filtrá-los conforme necessário.

## Tecnologias

- **Spring Boot**: Framework utilizado para o desenvolvimento da API.
- **Java**: Versão 17
- **H2**: Banco de dados em memória para armazenamento dos pagamentos.
- **Swagger**: Interface interativa para explorar e testar os endpoints da API.

## Execução

Para executar a aplicação no modo convencional, basta clonar o repositório com ```git clone```, executar o comando ```mvn clean install``` e, em seguida, executar a aplicação com o comando ```mvn spring-boot:run```.

A aplicação também está disponível em um container Docker. Para isso, basta ter o Docker instalado em sua máquina e executar o seguinte comando na raiz do projeto:

```
docker-compose up
```
Para abrir o console do H2 database, após levantar o projeto, acesse o seguinte endereço:

```
http://localhost:8080/h2
```

## Documentação

A documentação da API encontra-se disponível no Swagger a partir da URL raiz (```localhost:8080```) ou no seguinte link:

```
http://localhost:8080/swagger
```
Nela você encontrará informações detalhadas sobre cada endpoint da API, incluindo os parâmetros necessários, os tipos de dados esperados, os códigos de resposta e exemplos de requisições e respostas.

## Scripts e arquivos adicionais

Na pasta ```src/main/resources/collections``` há uma coleção de requisições que podem ser feitas na API. O arquivo pode ser importando tanto no Postman quanto no Imsomnia.

Além disto, na pasta ```src/main/resources``` há um script SQL de nome ```inserts.sql``` com alguns dados para serem inseridos na base e testar a API.

**DICA:** Alterando o nome do arquivo de script SQL para ```data.sql```, este script é executado automaticamente pelo Spring Boot ao inicializar a aplicação.




