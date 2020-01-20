import { Component, OnInit } from "@angular/core";
import { GalleryService } from "../gallery/gallery.service";
import { NotificationService } from "src/app/utils/notificationService.service";

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

  ngOnInit() {
    this.galleryService.findUserGalleries().subscribe(
      res => {
        this.allGalleries = res;
      },
      err => this.notifyService.failure(err)
    );
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
        },
        err => this.notifyService.failure(err)
      );
  }
}
