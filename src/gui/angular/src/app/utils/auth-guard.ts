import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from '../login-component/Authentication.service';

@Injectable({
    providedIn: 'root',
  })
  export class AuthGuard implements CanActivate {

    constructor(private authService: AuthenticationService) {

    }

    canActivate(
      next: ActivatedRouteSnapshot,
      state: RouterStateSnapshot): boolean {
        
      console.log('AuthGuard#canActivate called');
      return this.authService.isAuthenticated();
    }
  }