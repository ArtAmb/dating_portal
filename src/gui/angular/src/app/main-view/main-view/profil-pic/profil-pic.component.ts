import {Component, OnInit, Input, OnChanges} from "@angular/core";
import { GalleryService } from "../gallery/gallery.service";
import { NotificationService } from "../../../utils/notificationService.service";
import { UserProfilService } from "../profil/user-profil.service";
import { ImageInfo } from "../user-images/user-images.component";
import {ActivatedRoute, Router} from "@angular/router";
import { ProfilViewService } from "../../../profil-view/profil-view-service.component";
import {AuthenticationService} from "../../../login-component/Authentication.service";

@Component({
  selector: "app-profil-pic",
  templateUrl: "./profil-pic.component.html",
  styleUrls: ["./profil-pic.component.css"]
})
export class ProfilPicComponent implements OnInit {
  constructor(
    private galleryService: GalleryService,
    private notificationService: NotificationService,
    private userProfilService: UserProfilService,
    private profilViewService: ProfilViewService,
    private authService: AuthenticationService,
    private router : Router
  ) {
      this.router.events.subscribe((val)=>{this.getPic();}) }
  public avatarImage: ImageInfo = null;
  @Input() userId: number;

  isAuthenticated() {
    return this.authService.isAuthenticated();
  }


  ngOnInit(): void {
    this.getPic();
  }
  getPic() : void
  {

    if (this.userId) {
      this.profilViewService.findProfilByUserId(this.userId).subscribe(
          res => {
            this.notificationService.success("Udalo sie zaladowac uzytkownika");
            if (res.avatarImageId) {
              let tmp = new ImageInfo();
              tmp.imageId = res.avatarImageId.valueOf();

              this.avatarImage = tmp;
            }
          },
          err => {
            this.notificationService.showErrorMessage(
                "Nie ma takiego uzytkownika"
            );
          }
      );
    } else {
      this.userProfilService.findUserProfil().subscribe(
          res => {
            if (res.avatarImageId) {
              let tmp = new ImageInfo();
              tmp.imageId = res.avatarImageId.valueOf();

              this.avatarImage = tmp;
            }
          },
          err => this.notificationService.failure(err)
      );
    }
  }
}
