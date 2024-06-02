package main

import (
	"context"
	"database/sql"
	"encoding/json"
	"fmt"
	"github.com/segmentio/kafka-go"
	"log"
)

type OrderService struct {
	db *sql.DB
	r  *kafka.Reader
}

func NewOrderService() *OrderService {
	db, err := sql.Open("mysql", "root:password@tcp(mysql:3306)/orders_db")
	if err != nil {
		log.Fatal(err)
	}

	r := kafka.NewReader(kafka.ReaderConfig{
		Brokers:   []string{"kafka-1:19092", "kafka-2:19092"},
		Topic:     "orders_topic",
		Partition: 0,
		MinBytes:  10e3,
		MaxBytes:  10e6,
	})

	return &OrderService{
		db: db,
		r:  r,
	}
}

func (o *OrderService) create() {
	for {
		m, err := o.r.ReadMessage(context.Background())
		if err != nil {
			log.Println("Failed to read message:", err)
			continue
		}
		var order Order
		err = json.Unmarshal(m.Value, &order)
		if err != nil {
			log.Println("Failed to unmarshal order:", err)
			continue
		}

		_, err = o.db.Exec("INSERT INTO orders (customer_name, order_details, status) VALUES (?, ?, ?)", order.CustomerName, order.OrderDetails, "pending")
		if err != nil {
			log.Println("Failed to insert order:", err)
			continue
		}

		fmt.Println("Order inserted successfully:", order)
	}
}
