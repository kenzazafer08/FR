import { Category } from "./Category";
import { OrderLine } from "./OrderLine";
import { Pet } from "./Pet";

export interface Product {
    id: number;
    name: string;
    description: string;
    quantity: number;
    image: string;
    price: number;
    category: Category;
    pet: Pet;
  }