import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../../environments/environment";
import { Observable } from "rxjs";
@Injectable({
  providedIn: "root"
})
export class ChatService {
  constructor(private http: HttpClient) {}

  public getAllChats(): Observable<Array<ChatViewDTO>> {
    return this.http.get<Array<ChatViewDTO>>(
      environment.server_url + "/get-all-chats"
    );
  }

  public getChatLogin(userLogin: String, chatLogin: String): String {
    return chatLogin ? chatLogin : userLogin;
  }

  public sendMessage(arg0: {
    usersIds: number[];
    userId: number;
    message: String;
  }): Observable<any> {
    return this.http.post(environment.server_url + "/add-message", arg0);
  }


  public addNewMessage(arg0: {
    chatId: number;
    userId: number;
    message: String;
  }): Observable<any> {
    return this.http.post(environment.server_url + "/new-message", arg0);
  }
}

export class ChatViewDTO {
  chatId: number;
  lastMessage: {
    userId: number;
    message: String;
    dateTime: any;
  };

  myUserId: number;

  recipientUserId: number;
  recipientLogin: String;
  recipientChatLogin: String;
}
