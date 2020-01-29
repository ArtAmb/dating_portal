import { Component, OnInit } from "@angular/core";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Observable } from "rxjs";
import { environment } from "./../../../../environments/environment";
import { Client } from "stompjs";

@Component({
  selector: "app-chat",
  templateUrl: "./chat.component.html",
  styleUrls: ["./chat.component.css"]
})
export class ChatComponent implements OnInit {
  constructor() {}

  ngOnInit() {
    this._connectToServer();
  }

  private client: Client;
  inputChatMessage: String;


  public sendMsg() {
    this.client.send("/app/send/message", {}, JSON.stringify(this.inputChatMessage));
    this.inputChatMessage = null;
  }

  private _connectToServer() {
    this.client = Stomp.over(new SockJS(environment.server_url + "/chat"));
    this.client.connect({}, () => { 
      this.client.subscribe("/topic/refresh/messages", msg => {
        console.log(msg);
        console.log("Im working!!!");
      });
    });
  }
}
