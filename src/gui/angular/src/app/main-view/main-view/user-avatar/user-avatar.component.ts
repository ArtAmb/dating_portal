import { Component, OnInit } from "@angular/core";
import { ImageInfo } from "../user-images/user-images.component";
import { GalleryService } from "../gallery/gallery.service";
import { NotificationService } from "src/app/utils/notificationService.service";
import { UserProfilService } from "../profil/user-profil.service";
import { EventBusService } from "src/app/utils/event-bus.service";

@Component({
  selector: "app-user-avatar",
  templateUrl: "./user-avatar.component.html",
  styleUrls: ["./user-avatar.component.css"]
})
export class UserAvatarComponent implements OnInit {
  constructor(
    private galleryService: GalleryService,
    private notificationService: NotificationService,
    private userProfilService: UserProfilService,
    private eventBusService: EventBusService
  ) {
    console.log("A3");
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

  public avatarImage: ImageInfo = null;
  public image: File;
  public imageBase64 = null;

  ngOnInit() {}

  _reset() {
    this.imageBase64 = null;
    this.image = null;
  }

  onFileChanged($event) {
    this.image = $event.target.files[0];
    var myReader: FileReader = new FileReader();

    myReader.onloadend = e => {
      this.imageBase64 = myReader.result;
    };

    myReader.readAsDataURL(this.image);
  }

  publish() {
    var uploadData = new FormData();
    uploadData.append("file", this.image, this.image.name);

    this.galleryService.publishAvatarImage(uploadData).subscribe(
      res => {
        this.notificationService.success();
        this.eventBusService.getSubscriber().next("PROFIL_IMAGE_REFRESH");
        this._reset();
        this.avatarImage = res;
      },
      err => this.notificationService.failure(err)
    );
  }
}
