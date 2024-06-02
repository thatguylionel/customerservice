# Producer-Consumer Application with Kafka and Docker

This project consists of a producer service built with Kotlin, Java, Spring Boot, and Maven, and a consumer service
built with Go. The producer service provides a RESTful API to manage customers and products, while the consumer service
reads these data from a Kafka topic and stores them in a MySQL database. The entire application can be run using Docker
Compose.

## Technologies Used

- Kotlin: 1.9.24
- Java: 21
- Spring Boot: 3.3.0
- Maven (Wrapper included)
- Go (Still in progress, but will be completed soon)
- Kafka (using KRaft mode)
- Kafdrop
- Docker / Docker Compose
- MySQL
- H2 Database (for educational purposes)
- MongoDB (for educational purposes)

## Features

The producer service provides the following features:

- Add a new customer
- Get a customer by ID
- Get all customers
- Add a new product
- Get all products

The consumer service reads the customer and product data from a Kafka topic and stores them in a MySQL database.

## API Endpoints

- `POST /api/v1/customers`: Add a new customer
- `GET /api/v1/customers/{id}`: Get a customer by ID
- `GET /api/v1/customers`: Get all customers
- `POST /api/v1/products`: Add a new product
- `GET /api/v1/products`: Get all products

## Setup

To run this project, you need to have Docker installed on your machine.

1. Clone the repository
2. Navigate to the project directory
3. Run the application using the command: `docker-compose up`

## Testing

You can test the application using any API testing tool such as Postman or CURL. Here are some sample requests:

### Create Customer

```http
POST http://localhost:8080/api/v1/customers
Content-Type: application/json

{
  "name": "Tendulkar INC",
  "email": "something@someone.id"
}
```

### Get all Customers

```http
GET http://localhost:8080/api/v1/customers
```

### Create Product

```http
POST http://localhost:8080/api/v1/products
Content-Type: application/json

{
  "name": "Product 1",
  "description": "Product 1 Description",
  "price": 1000.99,
  "category": "Category 1"
}
```

### Get all Products

```http
GET http://localhost:8080/api/v1/products
```

## Kafdrop: Kafka Web UI

This project also includes Kafdrop, a web UI for viewing Kafka topics and browsing consumer groups. Kafdrop provides a bird's-eye view of your Kafka clusters and allows you to drill down to the topics, partitions, and messages.

### Accessing Kafdrop

Once your Docker Compose setup is running, you can access Kafdrop at `http://localhost:9000`. Replace `localhost` with the appropriate IP if you're accessing it from a different machine.

### Features

- View Kafka brokers along with their jmx details
- View Kafka topics and partitions
- View consumer groups and their lag
- View topic configuration
- View and query messages
- Delete a topic (This is a destructive operation and cannot be reversed)

Please note that Kafdrop is not a management UI. While it allows you to view topic configuration and delete topics, it does not support other management functions such as creating topics or altering topic configurations.

## Acknowledgements

A tip of the hat to all my fellow engineers who work so hard on these technologies and make them available for the rest
of us to use.