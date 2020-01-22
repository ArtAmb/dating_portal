import { Injectable } from "@angular/core";
import { User } from "./login-component.component";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "./../../environments/environment";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class LoginServiceService {
  constructor(private http: HttpClient) {}

  ENV = environment;

  register(user: User) {
    console.log("LoginServiceService - register ");
    return this.http.post(this.ENV.server_url + "/register", user);
  }

  tryToGetToAuthorizedMethod() {
    // var myHeader = new HttpHeaders();
    // myHeader.append("SET-COOKIE", "JSESSIONID=<jsessionid>");
    
    return this.http.get(this.ENV.server_url + "/test/magic");
  }

  checkIfAuthorized(): Observable<any> {
    return this.http.get(this.ENV.server_url + "/am/i/authorized");
  }
}
