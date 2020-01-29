import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../login-component/Authentication.service';

@Component({
  selector: 'app-right-bar',
  templateUrl: './right-bar.component.html',
  styleUrls: ['./right-bar.component.css']
})
export class RightBarComponent implements OnInit {

  constructor(private authService: AuthenticationService) { }

  ngOnInit() {
  }

  isAuth() {
    return this.authService.isAuthenticated();
  }

}
