import { Component, OnInit, Input, OnChanges } from "@angular/core";
import { GalleryService } from "../gallery/gallery.service";
import { NotificationService } from "../../../utils/notificationService.service";
import { UserProfilService } from "../profil/user-profil.service";
import { ImageInfo } from "../user-images/user-images.component";
import { ActivatedRoute, Router } from "@angular/router";
import { ProfilViewService } from "../../../profil-view/profil-view-service.component";
import { AuthenticationService } from "../../../login-component/Authentication.service";
import { EventBusService } from "src/app/utils/event-bus.service";

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
    private eventBusService: EventBusService
  ) {
    this.eventBusService.getSubscriber().subscribe(msg => {
      if (msg == "PROFIL_IMAGE_REFRESH") {
        this.refreshPicture();
      }
    });
  }
  public avatarImage: ImageInfo = null;
  @Input() userId: number;

  isAuthenticated() {
    return this.authService.isAuthenticated();
  }

  ngOnInit(): void {
    this.refreshPicture();
  }

  refreshPicture(): void {
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
      console.log("A2");
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
