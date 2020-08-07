import {Component, OnInit} from '@angular/core';
import $ from 'jquery';
import {OwlOptions} from 'ngx-owl-carousel-o';
import {Observable} from "rxjs";
import {Book} from "../common/book";
import {BookService} from "../_services/book.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  books: Observable<Book[]>;

  customOptions: OwlOptions = {
    loop: true,
    margin: 0,
    nav: false,
    autoplay: false,
    autoplayTimeout: 10000,
    items: 1,
    navText: ['<i class="zmdi zmdi-chevron-left"></i>', '<i class="zmdi zmdi-chevron-right"></i>' ],
    dots: false,
    lazyLoad: true,
    responsive: {
      0: {
        items: 1
      },
      1920: {
        items: 1
      }
    }
  };

  customOptions2: OwlOptions = {
    loop: true,
    margin: 0,
    nav: true,
    autoplay: false,
    autoplayTimeout: 10000,
    items: 4,
    navText: ['<i class="zmdi zmdi-chevron-left"></i>', '<i class="zmdi zmdi-chevron-right"></i>' ],
    dots: false,
    lazyLoad: true,
    responsive: {
      0: {
        items: 1
      },
      576: {
        items: 2
      },
      768: {
        items: 3
      },
      992: {
        items: 4
      },
      1920: {
        items: 4
      }
    }
  };

  customOptions3: OwlOptions = {
    loop: true,
    margin: 0,
    nav: true,
    autoplay: false,
    autoplayTimeout: 10000,
    items: 4,
    navText: ['<i class="zmdi zmdi-chevron-left"></i>', '<i class="zmdi zmdi-chevron-right"></i>' ],
    dots: false,
    lazyLoad: true,
    responsive: {
      0: {
        items: 1
      },
      576: {
        items: 2
      },
      768: {
        items: 3
      },
      992: {
        items: 4
      },
      1920: {
        items: 4
      }
    }
  };

  constructor(private bookService: BookService,
              private router: Router) {}

  ngOnInit(): void {
    this.books = this.bookService.getBookList();
  }

  bookDetails(id: number){
    this.router.navigate(['details', id]);
  }

}
