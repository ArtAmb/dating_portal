import { Injectable } from "@angular/core";
import { LoginServiceService } from "./login-service.service";
import { User } from "./login-component.component";
import { NotificationService } from "../utils/notificationService.service";
import {
  HttpClient,
  HttpResponse,
  HttpHeaderResponse,
  HttpHeaders
} from "@angular/common/http";
import { environment } from "./../../environments/environment";
import { Observable, BehaviorSubject } from "rxjs";
import { Router } from "@angular/router";
import { resolve } from "url";

@Injectable({
  providedIn: "root"
})
export class AuthenticationService {
  constructor(
    private notifyService: NotificationService,
    private http: HttpClient,
    private router: Router,
    private loginServiceService: LoginServiceService
  ) {}

  ENV = environment;
  private _behaviorSubject = new BehaviorSubject(null);
  private authenticatedUser: UserInfo = null;

  private AUTH_KEY = "funny_authentication";
  private LOGGED_USER_KEY = "logged_user";

  public login(user: User) {
    this._login(user).subscribe(
      res => {
        this.notifyService.success("Zalogowano");
        this._saveAuthenticatedUser();

        this.loginServiceService.checkIfAuthorized().subscribe(
          authObj => {
            console.log(authObj);
            localStorage.setItem(this.LOGGED_USER_KEY, JSON.stringify(authObj));
          },
          err => this.notifyService.failure(err)
        );

        localStorage.setItem(this.AUTH_KEY, "true");
        this._behaviorSubject.next("LOGIN");
      },
      err => {
        this.notifyService.failure(err);
      }
    );
  }

  private _saveAuthenticatedUser() {
    this._getUserInfo().subscribe(res => {
      this.authenticatedUser = res;
    }, err => {
      this.notifyService.failure(err);
    });
  }

  public logout() {
    this._logout().subscribe(
      res => {
        this.authenticatedUser = null;
        this.notifyService.success("Wylogowano pomyślnie");
        localStorage.setItem(this.AUTH_KEY, "false");
        this._behaviorSubject.next("LOGOUT");

        this.router.navigate([""]);
      },
      err => {
        this.notifyService.failure(err);
      }
    );
  }

  public getSubscriber() {
    return this._behaviorSubject;
  }

  public isAuthenticated() {
    return localStorage.getItem(this.AUTH_KEY) == "true";
  }

  public isAdmin() {
    let loggedUser = JSON.parse(localStorage.getItem(this.LOGGED_USER_KEY));
    return loggedUser.authorities.map(x => x.authority).includes("ROLE_ADMIN");
  }

  private _login(user: User): Observable<HttpResponse<any>> {
    console.log(user);
    var headers = new HttpHeaders();
    headers.append("SET-COOKIE", "JSESSIONID=<jsessionid>");

    return this.http.post(
      this.ENV.server_url +
        "/login?login=" +
        user.login +
        "&password=" +
        user.password,
      null,
      {
        headers: headers,
        observe: "response"
      }
    );
  }

  private _logout(): Observable<any> {
    var headers = new HttpHeaders();
    headers.append("SET-COOKIE", "JSESSIONID=<jsessionid>");

    return this.http.get(this.ENV.server_url + "/logout", {
      headers: headers,
      withCredentials: true
    });
  }

  private _getUserInfo(): Observable<any> {
    return this.http.get(this.ENV.server_url + "/user-info");
  }

  public getUserInfo(): Promise<UserInfo> {
    return new Promise((resolve, reject) => {
      if (this.authenticatedUser) {
        resolve(this.authenticatedUser);
      } else {
        this._getUserInfo().subscribe(res => {
          this.authenticatedUser = res;
          resolve(this.authenticatedUser);
        }, err => {
          this.notifyService.failure(err);
          reject();
        });
      }
    });
  }
}

export class UserInfo {
  login: string;
  userId: number;
}
