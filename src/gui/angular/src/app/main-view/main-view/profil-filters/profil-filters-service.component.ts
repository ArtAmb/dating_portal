import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "../../../../environments/environment";
import {
  EYE_COLOR,
  GENDER,
  HAIR_COLOR,
  REGION
} from "src/app/profil-edit/profil-edit.component";

@Injectable({
  providedIn: "root"
})
export class ProfilFiltersService {
  constructor(private httpClient: HttpClient) {}

  public updateSearchInfo(arg0: {
    gender: GENDER;
    region: REGION;
    eyeColor: EYE_COLOR;
    hairColor: HAIR_COLOR;
  }): Observable<any> {
    return this.httpClient.post(
      environment.server_url + "/update-search-info",
      arg0
    );
  }

  public getSearchInfo(): Observable<UserSearchInfo> {
    return this.httpClient.get<UserSearchInfo>(
      environment.server_url + "/user/search-info"
    );
  }

  public updateSearchAlgorithm(dto: {
    partnerSearchAlgorithm: PartnerSearchAlgorithm;
  }) {
    return this.httpClient.post(
      environment.server_url + "/update-search-algorithm",
      dto
    );
  }
}

export class UserSearchInfo {
  userId: number;

  algorithmType: PartnerSearchAlgorithm;
  preferredGender: GENDER;
  preferredRegion: REGION;
  preferredEyeColor: EYE_COLOR;
  preferredHairColor: HAIR_COLOR;
}

export enum PartnerSearchAlgorithm {
  LOOKING_FOR_MYSELF = "LOOKING_FOR_MYSELF",
  LOOKING_FOR_MY_PREFERENCES = "LOOKING_FOR_MY_PREFERENCES"
}
