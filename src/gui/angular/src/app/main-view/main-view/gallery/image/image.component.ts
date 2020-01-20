import { Component, OnInit, Input, Inject } from "@angular/core";
import { ImageInfo } from "../../user-images/user-images.component";
import { NotificationService } from "src/app/utils/notificationService.service";
import { GalleryService } from "../gallery.service";
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Overlay } from "@angular/cdk/overlay";

@Component({
  selector: "app-image",
  templateUrl: "./image.component.html",
  styleUrls: ["./image.component.css"]
})
export class ImageComponent implements OnInit {
  @Input() image: ImageInfo;
  @Input() fullPictureContext: boolean = false;

  constructor(
    private galleryService: GalleryService,
    private notificationService: NotificationService,
    private dialog: MatDialog,
    private overlay: Overlay
  ) {}

  ngOnInit() {}

  imageOnClick() {
    const scrollStrategy = this.overlay.scrollStrategies.reposition();
    this.dialog.open(ImageDialogComponent, {
      data: {
        image: this.image,
        fullPictureContext: true
      }
    });
  }

  createImgUrl() {
    return this.galleryService.createImageUrl(this.image.imageId);
  }
}


@Component({
  selector: "app-image-dialog",
  templateUrl: "./image-dialog.component.html",
  styleUrls: ["./image.dialog.component.css"]
})
export class ImageDialogComponent implements OnInit {
  @Input() image: ImageInfo;
  @Input() fullPictureContext: boolean = false;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    
  }
  ngOnInit() {}

}
