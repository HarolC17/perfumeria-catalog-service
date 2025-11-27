# Catalog Service - Mi Perfumería

Microservicio de gestión del catálogo de productos para la aplicación Mi Perfumería.

> 📦 Parte del proyecto [Mi Perfumería](https://github.com/HarolC17/mi-perfumeria-app)

## Descripción

Servicio encargado de la gestión de productos (perfumes), incluyendo operaciones CRUD con control de acceso por roles.

## Tecnologías

- Java 17
- Spring Boot 3.x
- PostgreSQL
- REST API

## Endpoints

| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|--------|
| GET | `/api/products` | Listar todos los productos | Público |
| GET | `/api/products/{id}` | Obtener producto por ID | Público |
| POST | `/api/products` | Crear nuevo producto | ADMIN |
| PUT | `/api/products/{id}` | Actualizar producto | ADMIN |
| DELETE | `/api/products/{id}` | Eliminar producto | ADMIN |

## Instalación

git clone https://github.com/HarolC17/catalog-service.git
cd catalog-service
