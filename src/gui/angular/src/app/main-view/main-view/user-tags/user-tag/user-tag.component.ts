import { Component, OnInit, Input } from "@angular/core";
import {
  Tag,
  TagConfService,
  TagPriority,
  TagType
} from "src/app/admin/tags-configuration/tag-conf.service";
import { NotificationService } from "src/app/utils/notificationService.service";
import { UserTagsContainerService } from "./user-tags-container.service";

@Component({
  selector: "app-user-tag",
  templateUrl: "./user-tag.component.html",
  styleUrls: ["./user-tag.component.css"]
})
export class UserTagComponent implements OnInit {
  @Input() tag: Tag;
  @Input() context: UserTagComponentContext;

  constructor(
    private tagService: TagConfService,
    private notifyService: NotificationService,
    private userTagContainer: UserTagsContainerService
  ) {}

  checked: boolean = false;
  tagPriority: TagPriority = TagPriority.NORMAL;

  ngOnInit() {
    let userTag = this.userTagContainer.findMyTag(this.tag.id);
    this.checked = userTag.checked;
  }

  updateUserTag() {
    this.tagService
      .updateUserTag(
        {
          tagId: this.tag.id,
          checked: this.checked,
          tagPriority: this.tagPriority
        },
        this._getContext()
      )
      .subscribe(
        res => {
          this.userTagContainer.replaceUserTag(res);
          this.notifyService.success();
        },
        err => this.notifyService.failure(err)
      );
  }

  private _getContext() {
    switch (this.context) {
      case UserTagComponentContext.MYSELF:
        return TagType.MY;
      case UserTagComponentContext.PARTNER:
        return TagType.PARTNER;
    }

    let errMsg = "Kontekst " + this.context + " nie jest obsługiwany!";
    this.notifyService.showErrorMessage(errMsg);
    throw new Error(errMsg);
  }
}

enum UserTagComponentContext {
  MYSELF = "MYSELF",
  PARTNER = "PARTNER"
}
