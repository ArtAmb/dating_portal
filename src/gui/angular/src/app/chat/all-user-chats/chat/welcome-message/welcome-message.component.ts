import { Component, OnInit, Input } from "@angular/core";
import { PotentialPartner } from "src/app/main-view/main-view/matching/matching-service.service";
import { ChatService } from "../../chat.service";
import { NotificationService } from "src/app/utils/notificationService.service";

@Component({
  selector: "app-welcome-message",
  templateUrl: "./welcome-message.component.html",
  styleUrls: ["./welcome-message.component.css"]
})
export class WelcomeMessageComponent implements OnInit {
  constructor(
    private chatService: ChatService,
    private notificationService: NotificationService
  ) {}

  @Input() potentialPartner: PotentialPartner;
  @Input() userId: number;

  welcomeMessage: String;
  messageSent = false;

  ngOnInit() {}

  sendMsg() {
    this.chatService
      .sendMessage({
        usersIds: [this.potentialPartner.userId, this.userId],
        userId: this.userId,
        message: this.welcomeMessage
      })
      .subscribe(
        res => {
          this.messageSent = true;
          this.notificationService.success();
        },
        err => {
          this.notificationService.failure(err);
        }
      );
  }
}
