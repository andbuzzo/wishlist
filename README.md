# Wishlist

Aplicativo **Wishlist** para gerenciamento de listas de desejos.

---

## 🧩 Sobre

Este projeto é uma aplicação back-end desenvolvida em **Java 21** com **Spring Boot 3.5.3**, que implementa um gerenciador de listas de desejos seguindo os princípios da **Clean Architecture**.  
Os dados são armazenados em um banco **MongoDB**, e a aplicação é totalmente containerizada com **Docker**.

---

## 🛠️ Tecnologias Utilizadas

- Java 21  
- Spring Boot 3.5.3  
- MongoDB  
- Docker & Docker Compose  
- Swagger/OpenAPI  
- Cucumber (para testes BDD)  

---

## 🚀 Iniciando a aplicação com Docker

### 1. Clone o projeto e entre na pasta:

```bash
git clone https://github.com/andbuzzo/wishlist.git
cd wishlist
```

### 2. Suba os containers com o Docker Compose:

```bash
docker-compose up --build
```

Este comando irá:

- Construir a imagem Docker da aplicação.
- Inicializar o MongoDB.
- Subir a API na URL: [`http://localhost:8081`](http://localhost:8081)

---

## 🔄 Utilizando as APIs com Swagger

A documentação da API está disponível em:

```
http://localhost:8081/swagger-ui/index.html

```

Com o Swagger você pode visualizar, testar e explorar todos os endpoints disponíveis na aplicação.

---

## 📫 Autor

**André Luiz Buzzo**  
[LinkedIn](https://www.linkedin.com/in/andrelbuzzo)