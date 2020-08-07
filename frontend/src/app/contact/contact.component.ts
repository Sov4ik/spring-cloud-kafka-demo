import {Component, OnInit} from '@angular/core';
import {OwlOptions} from 'ngx-owl-carousel-o';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  constructor() { }

  customOptions: OwlOptions = {
    loop: true,
    margin: 0,
    nav: true,
    autoplay: false,
    autoplayTimeout: 10000,
    items: 6,
    navText: ['<i class="zmdi zmdi-chevron-left"></i>', '<i class="zmdi zmdi-chevron-right"></i>' ],
    dots: false,
    lazyLoad: true,
    responsive: {
      0: {
        items: 2
      },
      1920: {
        items: 6
      },
      768: {
        items: 4
      },
      576: {
        items: 3
      },
      420: {
        items: 3
      }
    }
};

  ngOnInit(): void {
  }

}
