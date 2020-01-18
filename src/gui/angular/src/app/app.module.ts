import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { RouterModule } from "@angular/router";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";

import { AppComponent } from "./app.component";
import { TopBarComponent } from "./top-bar/top-bar.component";
import { LoginComponentComponent } from "./login-component/login-component.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ToastrModule } from "ngx-toastr";
import { LeftBarComponent } from "./left-bar/left-bar.component";
import { RightBarComponent } from "./right-bar/right-bar.component";
import { HttpRequestInterceptor } from "./interceptors/http-credential-interceptor";
import { APP_ROUTES } from "./routes";
import { MainViewComponent } from "./main-view/main-view/main-view.component";

@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgbModule,
    FormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      timeOut: 1500
      // positionClass: 'toast-bottom-right'
    }),
    RouterModule.forRoot(APP_ROUTES)
  ],
  declarations: [
    AppComponent,
    TopBarComponent,
    LoginComponentComponent,
    LeftBarComponent,
    RightBarComponent,    
    MainViewComponent
  ],
  bootstrap: [AppComponent],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpRequestInterceptor,
      multi: true
    }
  ]
})
export class AppModule {}
// RouterModule.forRoot([{ path: '', component: LoginComponentComponent }])
