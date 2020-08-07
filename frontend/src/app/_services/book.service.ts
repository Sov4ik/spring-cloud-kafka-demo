import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable, pipe} from 'rxjs';
import {map} from 'rxjs/operators';
import {Book} from '../common/book';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private baseUrl = 'http://localhost:8080/api/books';

  constructor(private http: HttpClient) { }

  getBook(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createBook(book: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}`, book);
  }

  updateBook(id: number, value: any): Observable<Object> {
    return this.http.put(`${this.baseUrl}/${id}`, value);
  }

  deleteBook(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }

  getBookList(): Observable<any> {
    return this.http.get(this.baseUrl)
      .pipe(map(response => response));
  }
}
