import { Injectable } from "@angular/core";
import { NotificationService } from "src/app/utils/notificationService.service";
import {
  TagConfService,
  UserTag,
  TagPriority,
  TagType
} from "src/app/admin/tags-configuration/tag-conf.service";

@Injectable({
  providedIn: "root"
})
export class UserTagsContainerService {
   
  constructor(
    private notifyService: NotificationService,
    private tagsService: TagConfService
  ) {}

  allUserTags: Array<UserTag> = [];
  
  public fill() {
    this.tagsService.findAllUserTags().subscribe(
      res => {
        this.allUserTags = res;
      },
      err => {
        this.notifyService.failure(err);
      }
    );
  }

  public findMyTag(tagId: number, context: TagType): UserTag {
    let tag = this.allUserTags.find(userTag => (userTag.tagId == tagId) && (userTag.type == context));

    if(tag == null) {
      let newTag = new UserTag();
      newTag.tagId = tagId;
      newTag.checked = false;
      newTag.priority = TagPriority.NORMAL;
      newTag.type = context;

      return newTag; 
    }

    return tag;
  }

  replaceUserTag(userTag: UserTag) {
    let tags = this.allUserTags.filter(tag => (tag.tagId != userTag.tagId) && (tag.type != userTag.type));
    tags.push(userTag);
    this.allUserTags = tags;
  }
}
