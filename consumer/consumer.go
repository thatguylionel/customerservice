package main

import (
	"fmt"
	_ "github.com/go-sql-driver/mysql"
)

type Order struct {
	CustomerName string `json:"customer_name"`
	OrderDetails string `json:"order_details"`
	Status       string `json:"status"`
}

/**
 * This consumer application reads messages from the Kafka topic and inserts them into the MySQL database.
 */
func main() {
	fmt.Println("LAUNCH APPLICATION")
	orderService := NewOrderService()
	orderService.create()
}
