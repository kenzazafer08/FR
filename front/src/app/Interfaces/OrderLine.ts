import { Order } from "./Order";
import { Product } from "./Product";

export interface OrderLine {
    id: number; 
    quantity: number;
    price: number; 
    order: Order;
    product: Product;
  }