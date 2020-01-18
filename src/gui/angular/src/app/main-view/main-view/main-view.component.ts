import { Component, OnInit } from "@angular/core";
import { AuthenticationService } from "src/app/login-component/Authentication.service";

@Component({
  selector: "app-main-view",
  templateUrl: "./main-view.component.html",
  styleUrls: ["./main-view.component.css"]
})
export class MainViewComponent implements OnInit {
  constructor(private authService: AuthenticationService) {}

  ngOnInit() {}

  isAuthenticated() {
    return this.authService.isAuthenticated();
  }
}
