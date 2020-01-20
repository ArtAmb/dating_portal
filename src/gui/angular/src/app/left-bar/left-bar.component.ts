import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../login-component/Authentication.service";

@Component({
  selector: 'app-left-bar',
  templateUrl: './left-bar.component.html',
  styleUrls: ['./left-bar.component.css']
})
export class LeftBarComponent implements OnInit {

  constructor(private authService : AuthenticationService) { }
  isAuthenticated()
  {
    return this.authService.isAuthenticated();
  }

  ngOnInit() {
  }

}
