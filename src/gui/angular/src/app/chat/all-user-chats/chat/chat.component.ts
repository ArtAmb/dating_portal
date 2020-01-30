import { Component, OnInit, Input, OnDestroy } from "@angular/core";
import * as Stomp from "stompjs";
import * as SockJS from "sockjs-client";
import { Observable } from "rxjs";
import { environment } from "./../../../../environments/environment";
import { Client } from "stompjs";
import { ChatViewDTO, ChatService } from "../chat.service";
import { NotificationService } from "src/app/utils/notificationService.service";

@Component({
  selector: "app-chat",
  templateUrl: "./chat.component.html",
  styleUrls: ["./chat.component.css"]
})
export class ChatComponent implements OnInit {
  constructor(
    private chatService: ChatService,
    private notificationService: NotificationService
  ) {}

  getOlderMessages() {
    this.chatService.getMessages(this.chat.chatId, this.messages.length, 10).subscribe(
      res => {
        let olderMessages = res;
        this.messages = olderMessages.concat(this.messages);
      },
      err => {
        this.notificationService.failure(err);
      }
    );
  }

  ngOnInit() {
    this.chatService.getMessages(this.chat.chatId, 0, 10).subscribe(
      res => {
        this.messages = res;
        this._connectToServer();
      },
      err => {
        this.notificationService.failure(err);
      }
    );
  }

  // ngOnDestroy() {
  //   this.client.disconnect(() => {
  //     console.log("DISCONECTED!!!");
  //   }, {});
  // }

  @Input() chat: ChatViewDTO;

  private client: Client;
  inputChatMessage: String;

  messages: Array<ChatMessage> = [];

  public getLogin(chat: ChatViewDTO): String {
    return this.chatService.getChatLogin(
      chat.recipientLogin,
      chat.recipientChatLogin
    );
  }

  public sendMsg() {
    this.chatService
      .addNewMessage({
        userId: this.chat.myUserId,
        chatId: this.chat.chatId,
        message: this.inputChatMessage
      })
      .subscribe(
        res => {
          // this.messages.push(res);
          this.inputChatMessage = null;
        },
        err => {
          this.notificationService.failure(err);
        }
      );
  }

  private _subscribeForNotifications() {
    this.client.send(
      "/app/send/message",
      {},
      JSON.stringify({
        userId: this.chat.myUserId,
        chatId: this.chat.chatId
      })
    );
  }

  private _connectToServer() {
    this.client = Stomp.over(new SockJS(environment.server_url + "/chat"));
    this.client.connect({}, () => {
      this._subscribeForNotifications();

      this.client.subscribe("/topic/refresh/messages", msg => {
        console.log(msg);
        let obj = JSON.parse(msg.body);
        if (obj.message.chatId == this.chat.chatId) {
          this.messages.push(obj.message);
        }

        this._subscribeForNotifications();
      });
    });
  }
}

export class ChatMessage {
  userId: number;
  dateTime: any;
  message: String;
}
