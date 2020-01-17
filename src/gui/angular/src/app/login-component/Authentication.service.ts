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

@Injectable({
  providedIn: "root"
})
export class AuthenticationService {
  constructor(
    private notifyService: NotificationService,
    private http: HttpClient
  ) {}

  ENV = environment;
  private _behaviorSubject = new BehaviorSubject(null);

  private AUTH_KEY = "funny_authentication";

  public login(user: User) {
    this._login(user).subscribe(
      res => {
        this.notifyService.success("Zalogowano");
        console.log(res);

        const keys = res.headers.keys();
        var headers = keys.map(key => `${key}: ${res.headers.get(key)}`);

        console.log(keys);
        console.log(headers);

        localStorage.setItem(this.AUTH_KEY, "true");
        this._behaviorSubject.next("LOGIN");
      },
      err => {
        this.notifyService.failure(err);
      }
    );
  }

  public logout() {
    this._logout().subscribe(
      res => {
        this.notifyService.success("Wylogowano pomyślnie");
        localStorage.setItem(this.AUTH_KEY, "false");
        this._behaviorSubject.next("LOGOUT");
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
}
