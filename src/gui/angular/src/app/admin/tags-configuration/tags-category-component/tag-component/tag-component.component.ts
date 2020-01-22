import { Component, OnInit, Input } from '@angular/core';
import { Tag, TagConfService } from '../../tag-conf.service';
import { NotificationService } from 'src/app/utils/notificationService.service';

@Component({
  selector: 'app-tag-component',
  templateUrl: './tag-component.component.html',
  styleUrls: ['./tag-component.component.css']
})
export class TagComponentComponent implements OnInit {

  constructor(
    private tagConfService: TagConfService,
    private notifyService: NotificationService
  ) {}
  @Input() tag: Tag;

  ngOnInit() {
    this.newName = this.tag.name;
  }


  editMode = false;
  newName: String;

  enableEdit() {
    this.newName = this.tag.name;
    this.editMode = true;
  }

  disableEdit() {
    this.editMode = false;
    this.newName = this.tag.name;
  }

  edit() {
    this.tagConfService.updateTag({
      tagId: this.tag.id,
      name: this.newName.valueOf()
    }).subscribe(res => {
      this.tag.name = this.newName.valueOf();
      this.disableEdit();
      this.notifyService.success();
    }, err => this.notifyService.failure(err)) ;
  }
  

}
