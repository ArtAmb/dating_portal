import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "./../../../../environments/environment";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class UserProfilService {
  constructor(private httpClient: HttpClient) {}


  findUserProfil(): Observable<UserProfil> {
    return this.httpClient.get<UserProfil>(environment.server_url + "/user/profil");
  }
}

export class UserProfil {
  id: Number;
  userId: Number;
  displayLogin: String;

  avatarImageId: Number;
}
