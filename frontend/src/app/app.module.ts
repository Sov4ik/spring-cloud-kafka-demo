import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {RegisterComponent} from './register/register.component';
import {FormsModule} from '@angular/forms';
import {ProfileComponent} from './profile/profile.component';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import {authInterceptorProviders} from "./_jwt/auth.interceptor";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CarouselModule} from "ngx-owl-carousel-o";
import {OwlModule} from "ngx-owl-carousel";
import {ContactComponent} from "./contact/contact.component";
import {BlogComponent} from "./blog/blog.component";
import {ShopComponent} from "./shop/shop.component";
import {BookDetailsComponent} from "./book-details/book-details.component";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    RegisterComponent,
    ProfileComponent,
    BookDetailsComponent,
    ContactComponent,
    BlogComponent,
    ShopComponent

  ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        AppRoutingModule,
        RouterModule,
        OwlModule,
        CarouselModule,
        BrowserAnimationsModule,
    ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
