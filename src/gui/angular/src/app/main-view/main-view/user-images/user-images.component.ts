import { Component, OnInit, Input } from "@angular/core";
import { GalleryService } from "../gallery/gallery.service";
import { NotificationService } from "src/app/utils/notificationService.service";
import { Observable } from "rxjs";

export class ImageInfo {
  imageId: number;
  description: String;
}

export class Gallery {
  id: Number;
  title: Number;
  accessScope: GalleryAccessibility;
  images: Array<ImageInfo>;
}

export enum GalleryAccessibility {
  PRIVATE = "PRIVATE",
  PUBLIC = "PUBLIC"
}

@Component({
  selector: "app-user-images",
  templateUrl: "./user-images.component.html",
  styleUrls: ["./user-images.component.css"]
})
export class UserImagesComponent implements OnInit {
  constructor(
    private galleryService: GalleryService,
    private notifyService: NotificationService
  ) {}

  @Input() detailModeOn = false;
  @Input() userId: Number;

  ngOnInit() {
    console.log(this.userId);
    this._findUserGalleries().subscribe(
      res => {
        this.allGalleries = res;
      },
      err => this.notifyService.failure(err)
    );
  }

  private _findUserGalleries(): Observable<any> {
    if (this.userId == null) {
      return this.galleryService.findUserGalleries();
    } else {
      return this.galleryService.findUserGalleriesByUserId(this.userId.valueOf());
    }
  }

  allGalleries: Gallery[] = [];

  galleryName = "";
  newGalleryModeEnable = false;

  enableNewGallery() {
    this.newGalleryModeEnable = true;
  }

  addNewGallery() {
    this.galleryService
      .addNewGallery({
        galleryName: this.galleryName
      })
      .subscribe(
        res => {
          this.notifyService.success();
          this.newGalleryModeEnable = false;
          this.galleryName = "";
          if(!res.images) {
            res.images=[];
          }
          this.allGalleries.push(res);
        },
        err => this.notifyService.failure(err)
      );
  }
}
