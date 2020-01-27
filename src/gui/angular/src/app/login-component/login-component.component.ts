import { Component, OnInit } from "@angular/core";
import { LoginServiceService } from "./login-service.service";
import { NotificationService } from "../utils/notificationService.service";
import { AuthenticationService } from "./Authentication.service";

export class User {
  email: string;
  login: string;
  password: string;
}

export enum Mode {
  LOGIN = "LOGIN",
  REGISTER = "REGISTER"
}

@Component({
  selector: "app-login-component",
  templateUrl: "./login-component.component.html",
  styleUrls: ["./login-component.component.css"]
})
export class LoginComponentComponent implements OnInit {
  user: User = new User();
  mode: Mode = Mode.LOGIN;

  constructor(
    private loginServiceService: LoginServiceService,
    private notificationService: NotificationService,
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit() {}

  login() {
    this.authenticationService.login(this.user);
    window.location.reload();
  }

  register() {
    console.log("register");
    this.loginServiceService.register(this.user).subscribe(
      res => {
        this.notificationService.success("Zarejestrowano pomyślnie");
        //this.authenticationService.login(this.user);
        this.login();
      },
      err => this.notificationService.failure(err)
    );
  }

  switchContext() {
    if (this.mode == Mode.LOGIN) {
      this.mode = Mode.REGISTER;
    } else {
      this.mode = Mode.LOGIN;
    }
  }

  tryToGetToAuthorizedMethod() {
    this.loginServiceService.tryToGetToAuthorizedMethod().subscribe(
      res => {
        console.log("tryToGetToAuthorizedMethod SUCCESS");
        console.log(res);
        this.notificationService.success()
      },
      err => {
        console.log("tryToGetToAuthorizedMethod failure");
        console.log(err);
        this.notificationService.failure();
      }
    );
  }

  checkIfAuthorized() {
    this.loginServiceService.checkIfAuthorized().subscribe(
      res => {
        this.notificationService.success()
      },      
      err => this.notificationService.failure()
    );
  }
}
