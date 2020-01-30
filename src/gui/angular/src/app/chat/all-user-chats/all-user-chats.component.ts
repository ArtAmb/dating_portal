import { Component, OnInit } from "@angular/core";
import { NotificationService } from "src/app/utils/notificationService.service";
import { ChatService, ChatViewDTO } from "./chat.service";

@Component({
  selector: "app-all-user-chats",
  templateUrl: "./all-user-chats.component.html",
  styleUrls: ["./all-user-chats.component.css"]
})
export class AllUserChatsComponent implements OnInit {
  chats: Array<ChatViewDTO> = [];

  constructor(
    private chatService: ChatService,
    private notificationService: NotificationService
  ) {}

  ngOnInit() {
    this.chatService.getAllChats().subscribe(
      res => {
        this.chats = res;
        this.notificationService.success();
      },
      err => {
        this.notificationService.failure(err);
      }
    );
  }

  public getLogin(chat: ChatViewDTO): String {
    return this.chatService.getChatLogin(chat.recipientLogin, chat.recipientChatLogin);
  }

  public openChat(chat: ChatViewDTO) {
    this.notificationService.showMessage("Im working!!!");
  }

}
