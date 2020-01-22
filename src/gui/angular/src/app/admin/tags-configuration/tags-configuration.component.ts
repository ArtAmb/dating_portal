import { Component, OnInit } from "@angular/core";
import { TagConfService, TagsCategory } from "./tag-conf.service";
import { NotificationService } from "src/app/utils/notificationService.service";

@Component({
  selector: "app-tags-configuration",
  templateUrl: "./tags-configuration.component.html",
  styleUrls: ["./tags-configuration.component.css"]
})
export class TagsConfigurationComponent implements OnInit {
  constructor(
    private tagConfService: TagConfService,
    private notifyService: NotificationService
  ) {}

  tagCategoryName: String = null;

  allTagsCategory: Array<TagsCategory> = [];

  ngOnInit() {
    this.tagConfService.findAllTags().subscribe(
      res => {
        this.allTagsCategory = res;
      },
      err => this.notifyService.failure(err)
    );
  }

  addAddNewCategory() {
    if(this.tagCategoryName == null) {
      this.notifyService.showMessage("Brak nazwy..");
      return;
    }

    this.tagConfService
      .addTagCategory({
        name: this.tagCategoryName.valueOf()
      })
      .subscribe(
        res => {
          this.allTagsCategory.push(res);
          this.tagCategoryName = null;
          this.notifyService.success();
        },
        err => {
          this.notifyService.failure(err);
        }
      );
  }
}
