import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {TokenStorageService} from "./token-storage.service";

const API_URL = 'http://localhost:8080/api/test/';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all',  httpOptions);
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', httpOptions);
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod',  httpOptions);
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin',  httpOptions);
  }
}
