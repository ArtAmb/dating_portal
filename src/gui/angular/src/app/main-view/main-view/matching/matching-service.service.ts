import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "./../../../../environments/environment";
import { UserTag } from "src/app/admin/tags-configuration/tag-conf.service";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class MatchingServiceService {
  constructor(private http: HttpClient) {}

  public matching(): Observable<Array<PotentialPartner>> {
    return this.http.get<Array<PotentialPartner>>(environment.server_url + "/match-partner");
  }
}

export class PotentialPartner {
  userId: number;
  userTags: Array<UserTag>;
  matchingRate: number;
}
