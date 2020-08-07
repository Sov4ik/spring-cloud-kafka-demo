import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from './_services/token-storage.service';
import $ from 'jquery';
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  private roles: string[];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username: string;
  title: Function;

  cartboxToggler(event: any) {
    event.preventDefault();
    const  container = $('.block-minicart');
    container.toggleClass('is-visible');
  }

  searchToggler(event: any) {
    event.preventDefault();
    const container = $('.search_active');
    container.toggleClass('is-visible');
  }

  settingOption(event: any) {
    event.preventDefault();
    const settingItem = $('.setting__block');
    settingItem.toggleClass('is-visible');
  }

  constructor(private tokenStorageService: TokenStorageService,
              private router: Router) { }

  ngOnInit() {

    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = user.username;
    }
  }

  logout() {
    this.tokenStorageService.signOut();
    this.router.navigate(['home']);
  }

}
