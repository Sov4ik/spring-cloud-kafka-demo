import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {Book} from "../common/book";
import {BookService} from "../_services/book.service";

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {
  books: Observable<Book[]>;

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
    this.books = this.bookService.getBookList();
  }

}
