import { Component, OnInit } from '@angular/core';
import {Book} from "../common/book";
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../_services/book.service";
import {Observable} from "rxjs";
import {OwlOptions} from "ngx-owl-carousel-o";

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.css']
})
export class BookDetailsComponent implements OnInit {

  id: number;
  book: Book;
  books: Observable<Book[]>;

  customOptions: OwlOptions = {
    loop:true,
    margin:0,
    nav:true,
    autoplay: false,
    autoplayTimeout: 10000,
    items:3,
    navText: ['<i class="zmdi zmdi-chevron-left"></i>', '<i class="zmdi zmdi-chevron-right"></i>' ],
    dots: false,
    lazyLoad: true,
    responsive:{
      0:{
        items:1
      },
      576:{
        items:2
      },
      768:{
        items:3
      },
      1920:{
        items:3
      }
    }
  };

  customOptions2: OwlOptions = {
    loop:true,
    margin:0,
    nav:false,
    autoplay: false,
    autoplayTimeout: 10000,
    items:6,
    navText: ['<i class="zmdi zmdi-chevron-left"></i>', '<i class="zmdi zmdi-chevron-right"></i>' ],
    dots: false,
    lazyLoad: true,
    responsive:{
      0:{
        items:2
      },
      1920:{
        items:6
      },
      768:{
        items:4
      },
      576:{
        items:3
      },
      420:{
        items:3
      }
    }
  };

  constructor(private route: ActivatedRoute,
              private bookService: BookService) { }

  ngOnInit(): void {

    this.books = this.bookService.getBookList();

    this.id = this.route.snapshot.params.id;

    this.bookService.getBook(this.id)
      .subscribe(data => {
        console.log(data);
        this.book = data;
      }, error => console.log(error));
  }



}
