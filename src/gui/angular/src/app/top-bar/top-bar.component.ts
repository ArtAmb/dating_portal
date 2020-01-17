import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../login-component/Authentication.service';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService) { }

  public userLoggedContext = false;

  ngOnInit() {
    this.authenticationService.getSubscriber().subscribe(event => {
      if(event == "LOGIN") {
        this.userLoggedContext = true;
      }
      if(event == "LOGOUT") {
        this.userLoggedContext = false;
      }
    })
  }

  logout() {
    this.authenticationService.logout();
  }

}


/*
Copyright Google LLC. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/