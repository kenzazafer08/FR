import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from 'src/app/Interfaces/Product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrl = 'http://localhost:8080/product';

  constructor(private http: HttpClient) { }

  addProduct(product: Product): Observable<Product> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<Product>(`${this.baseUrl}/add`, product, { headers });
  }

  getAllProducts(page: number, size: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.baseUrl}/list?page=${page}&size=${size}`);
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.baseUrl}/id/${id}`);
  }

  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  updateProduct(id: number, product: Product): Observable<Product> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<Product>(`${this.baseUrl}/${id}`, product, { headers });
  }
}
