import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "./../../../../environments/environment";
import { UserTag, TagPriority } from "src/app/admin/tags-configuration/tag-conf.service";
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
  imageAvatarId: number;
  tags: Array<TagView>;
  matchingRate: number;

  description: String;
  login: string;
}

export class TagView {
  name: string;
  priority: TagPriority;
}