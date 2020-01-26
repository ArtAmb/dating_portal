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
import { GalleryComponent } from "./main-view/main-view/gallery/gallery.component";
import { UserImagesComponent } from "./main-view/main-view/user-images/user-images.component";
import {
  ImageComponent,
  ImageDialogComponent
} from "./main-view/main-view/gallery/image/image.component";
import { MatDialogModule } from "@angular/material/dialog";
import {ProfilEditComponent} from "./profil-edit/profil-edit.component";
import { UserAvatarComponent } from "./main-view/main-view/user-avatar/user-avatar.component";
import { TagsConfigurationComponent } from "./admin/tags-configuration/tags-configuration.component";
import { TagComponentComponent } from "./admin/tags-configuration/tags-category-component/tag-component/tag-component.component";
import { TagsCategoryComponentComponent } from "./admin/tags-configuration/tags-category-component/tags-category-component.component";
import { UserTagsComponent } from "./main-view/main-view/user-tags/user-tags.component";
import { UserTagComponent } from "./main-view/main-view/user-tags/user-tag/user-tag.component";
import {ProfilViewComponent} from "./profil-view/profil-view.component";
import {FriendsViewComponent} from "./main-view/main-view/friends-view/friends-view.component";
import { MatchingButtonComponent } from "./main-view/main-view/matching/matching-button/matching-button.component";
import { MatchingResultComponent } from "./main-view/main-view/matching/matching-result/matching-result.component";
import {ProfilPicComponent} from "./main-view/main-view/profil-pic/profil-pic.component";
import { PotentialPartnerComponent } from "./main-view/main-view/matching/matching-result/potential-partner/potential-partner.component";
import { PotentialPartnerDialogComponent } from "./main-view/main-view/matching/matching-result/potential-partner/potential-partner-dialog/potential-partner-dialog.component";
import { AllUserChatsComponent } from "./chat/all-user-chats/all-user-chats.component";

@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    MatDialogModule,
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
    MainViewComponent,
    GalleryComponent,
    AllUserChatsComponent,
    PotentialPartnerDialogComponent,
    ImageDialogComponent,
    UserImagesComponent,
    UserAvatarComponent,
    UserTagComponent,
    ImageComponent,
    PotentialPartnerComponent,
    ProfilEditComponent,
    UserTagsComponent,
    MatchingButtonComponent,
    MatchingResultComponent,
    TagsConfigurationComponent,
    TagsCategoryComponentComponent,
    TagComponentComponent,
    ProfilViewComponent,
    FriendsViewComponent,
    ProfilPicComponent
  ],
  bootstrap: [AppComponent],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpRequestInterceptor,
      multi: true
    }
  ],
  entryComponents: [ImageComponent, ImageDialogComponent, PotentialPartnerDialogComponent]
})
export class AppModule {}
// RouterModule.forRoot([{ path: '', component: LoginComponentComponent }])
