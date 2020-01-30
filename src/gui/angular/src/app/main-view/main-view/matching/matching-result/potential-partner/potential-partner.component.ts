import { Component, OnInit, Input } from "@angular/core";
import { PotentialPartner, TagView } from "../../matching-service.service";
import { NotificationService } from "src/app/utils/notificationService.service";
import { ImageInfo } from "../../../user-images/user-images.component";
import { MatDialog } from "@angular/material/dialog";
import { PotentialPartnerDialogComponent } from "./potential-partner-dialog/potential-partner-dialog.component";
import { WelcomeMessageDialogComponent } from "src/app/chat/all-user-chats/chat/welcome-message/welcome-message-dialog/welcome-message-dialog.component";
import { AuthenticationService } from "src/app/login-component/Authentication.service";

@Component({
  selector: "app-potential-partner",
  templateUrl: "./potential-partner.component.html",
  styleUrls: ["./potential-partner.component.css"]
})
export class PotentialPartnerComponent implements OnInit {
  @Input() potentialPartner: PotentialPartner;
  avatarImage: ImageInfo;
  constructor(
    private notificationService: NotificationService,
    private authenticationService: AuthenticationService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.avatarImage = this.getImage();
  }

  public getImage() {
    let avatarImage = new ImageInfo();
    avatarImage.imageId = this.potentialPartner.imageAvatarId;
    return avatarImage;
  }

  public chooseClass(tagView: TagView) {
    let className = "tag ";
    switch (tagView.priority) {
      case "LOVE":
        return className + "love";
      case "LIKE":
        return className + "like";
      case "NORMAL":
        return className + "normal";
      case "NOT_LIKE":
        return className + "notLike";
      case "HATE":
        return className + "hate";
    }
    this.notificationService.showErrorMessage(
      tagView.priority + " is not handled!"
    );
  }

  invite() {
    this.notificationService.showMessage("Wyslano zaproszenie");
  }
  sendMessage() {
    this.authenticationService.getUserInfo().then(userInfo => {
      this.dialog.open(WelcomeMessageDialogComponent, {
        data: {
          potentialPartner: this.potentialPartner,
          userId: userInfo.userId
        }
      });
    });
  }
  showMore() {
    this.dialog.open(PotentialPartnerDialogComponent, {
      data: {
        potentialPartner: this.potentialPartner
      }
    });
  }
}
