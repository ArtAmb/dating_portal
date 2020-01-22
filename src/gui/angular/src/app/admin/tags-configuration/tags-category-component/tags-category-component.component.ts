import { Component, OnInit, Input } from "@angular/core";
import { TagConfService, TagsCategory } from "../tag-conf.service";
import { NotificationService } from "src/app/utils/notificationService.service";

@Component({
  selector: "app-tags-category-component",
  templateUrl: "./tags-category-component.component.html",
  styleUrls: ["./tags-category-component.component.css"]
})
export class TagsCategoryComponentComponent implements OnInit {
  constructor(
    private tagConfService: TagConfService,
    private notifyService: NotificationService
  ) {}

  @Input() tagCategory: TagsCategory;
  newTagName = null;

  ngOnInit() {
    this.newName = this.tagCategory.name;
  }

  editMode = false;
  newName: String = null;

  enableEdit() {
    this.newName = this.tagCategory.name;
    this.editMode = true;
  }

  disableEdit() {
    this.editMode = false;
    this.newName = this.tagCategory.name;
  }

  edit() {
    this.tagConfService.updateTagCategory({
      tagCategoryId: this.tagCategory.id,
      name: this.newName.valueOf()
    }).subscribe(res => {
      this.tagCategory.name = this.newName.valueOf();
      this.disableEdit();
      this.notifyService.success();
    }, err => this.notifyService.failure(err)) ;
  }

  addAddNewTag() {
    if(this.newTagName == null) {
      this.notifyService.showMessage("Brak nazwy..");
      return;
    }

    this.tagConfService
      .addTag({
        categoryId: this.tagCategory.id,
        name: this.newTagName.valueOf()
      })
      .subscribe(
        res => {
          if(!this.tagCategory.tags) {
            this.tagCategory.tags = [];
          }
          this.tagCategory.tags.push(res);
          this.newTagName = null;
          this.notifyService.success();
        },
        err => {
          this.notifyService.failure(err);
        }
      );
  }
}
