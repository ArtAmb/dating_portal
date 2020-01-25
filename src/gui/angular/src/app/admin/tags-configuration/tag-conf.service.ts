import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "./../../../environments/environment";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class TagConfService {
  constructor(private httpClient: HttpClient) {}

  public findAllTags(): Observable<Array<TagsCategory>> {
    return this.httpClient.get<Array<TagsCategory>>(environment.server_url + "/all-tags");
  }

  public updateTag(arg0: { tagId: number; name: string; }): Observable<any> {
    return this.httpClient.post(environment.server_url + "/update-tag", arg0);
  }

  public updateTagCategory(arg0: { tagCategoryId: number; name: string; }): Observable<any> {
    return this.httpClient.post(environment.server_url + "/update-tag-category", arg0);
  }

  public addTagCategory(arg0: { name: string; }): Observable<TagsCategory> {
    return this.httpClient.post<TagsCategory>(environment.server_url + "/add-new-tag-category", arg0);
  }

  public addTag(arg0: { categoryId: number, name: string; }): Observable<Tag> {
    return this.httpClient.post<Tag>(environment.server_url + "/add-new-tag", arg0);
  }

  updateUserTag(arg0: { tagId: number; checked: boolean; tagPriority: TagPriority }, context: TagType): Observable<UserTag> {
    return this.httpClient.post<UserTag>(environment.server_url + "/update-user-tag/context/" + context, arg0);
  }

  findAllUserTags() {
    return this.httpClient.get<Array<UserTag>>(environment.server_url + "/all-user-tags");
  }
  
}

export class TagsCategory {
  id: number;
  name: string;
  archived: boolean;
  tags: Array<Tag>;
}

export class Tag {
  id: number;
  name: string;
  archived: boolean;
}

export class UserTag {
  id: number;
  tagId: number;
  checked: boolean;
  priority: TagPriority;
  type: TagType;
}

export enum TagPriority {
  LOVE = "LOVE",
  LIKE = "LIKE",
  NORMAL = "NORMAL",
  NOT_LIKE = "NOT_LIKE",
  HATE = "HATE"
}

export enum TagType {
  MY = "MY",
  PARTNER = "PARTNER"
}