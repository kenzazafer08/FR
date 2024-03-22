import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/Interfaces/Product';
import { ProductService } from 'src/app/services/product/product.service';

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.scss']
})
export class StoreComponent implements OnInit {
  products: Product[] = [];
  newProduct: Product = {
    id: 0,
    name: '',
    description: '',
    quantity: 0,
    image: '',
    price: 0,
    category: { id: 0, name: '', description: '', image: ''},
    pet: { id: 0, name: '', description: '', image: ''},
  };

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getAllProducts(1, 10)
      .subscribe(products => {
        this.products = products;
      });
  }

  addProduct(): void {
    this.productService.addProduct(this.newProduct)
      .subscribe(() => {
        this.loadProducts();
        this.newProduct = {
          id: 0,
          name: '',
          description: '',
          quantity: 0,
          image: '',
          price: 0,
          category: { id: 0, name: '', description: '', image: ''},
          pet: { id: 0, name: '', description: '', image: ''},
        }
  })
}

  deleteProduct(id: number): void {
    this.productService.deleteProduct(id)
      .subscribe(() => {
        this.loadProducts();
      });
  }
}
