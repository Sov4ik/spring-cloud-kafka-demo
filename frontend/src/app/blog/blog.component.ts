import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {Book} from "../common/book";
import {BookService} from "../_services/book.service";

@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.css']
})
export class BlogComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
