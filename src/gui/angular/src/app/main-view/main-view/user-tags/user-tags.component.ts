import { Component, OnInit } from "@angular/core";
import {
  TagsCategory,
  TagConfService
} from "src/app/admin/tags-configuration/tag-conf.service";
import { NotificationService } from "src/app/utils/notificationService.service";
import { UserTagsContainerService } from "./user-tag/user-tags-container.service";

@Component({
  selector: "app-user-tags",
  templateUrl: "./user-tags.component.html",
  styleUrls: ["./user-tags.component.css"]
})
export class UserTagsComponent implements OnInit {
  constructor(
    private tagService: TagConfService,
    private notifyService: NotificationService,
    private userTagsContainerService: UserTagsContainerService
  ) {}

  allTagsCategory: Array<TagsCategory> = [];
  ngOnInit() {
    this.userTagsContainerService.fill();
    
    this.tagService.findAllTags().subscribe(
      res => {
        this.allTagsCategory = res;
      },
      err => {
        this.notifyService.failure(err);
      }
    );
  }

}
