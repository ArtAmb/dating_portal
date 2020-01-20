import { Component, OnInit, Input } from "@angular/core";
import { GalleryService } from "./gallery.service";
import { NotificationService } from "src/app/utils/notificationService.service";
import { Gallery } from "../user-images/user-images.component";

@Component({
  selector: "app-gallery",
  templateUrl: "./gallery.component.html",
  styleUrls: ["./gallery.component.css"]
})
export class GalleryComponent implements OnInit {
  constructor(
    private galleryService: GalleryService,
    private notificationService: NotificationService
  ) {}

  ngOnInit() {}

  galleryEditionEnable = false;
  newGalleryName = null;

  imageBase64 = null;
  file: File = null;
  description: string = "";

  @Input() gallery: Gallery;

  _reset() {
    this.imageBase64 = null;
    this.file = null;
    this.description = "";
  }

  onFileChanged($event) {
    this.file = $event.target.files[0];
    var myReader: FileReader = new FileReader();

    myReader.onloadend = e => {
      this.imageBase64 = myReader.result;
    };

    myReader.readAsDataURL(this.file);
  }

  publish() {
    var uploadData = new FormData();
    uploadData.append("file", this.file, this.file.name);
    uploadData.append("description", this.description);
    uploadData.append("galleryId", this.gallery.id.toString());

    this.galleryService.uploadImage(uploadData).subscribe(
      res => {
        this.notificationService.success();
        this._reset();
        this.gallery.images.push(res);
      },
      err => this.notificationService.failure(err)
    );
  }

  enableGalleryEdition() {
    this.newGalleryName = this.gallery.title;
    this.galleryEditionEnable = true;
  }

  disableGalleryEdition() {
    this.newGalleryName = null;
    this.galleryEditionEnable = false;
  }

  saveGallery() {
    this.galleryService
      .changeGalleryName({
        galleryId: this.gallery.id,
        galleryName: this.newGalleryName
      })
      .subscribe(
        res => {
          this.notificationService.success("Nazwa zmieniona");
          this.gallery.title = this.newGalleryName;
          this.galleryEditionEnable = false;
          this.newGalleryName = null;
        },
        err => this.notificationService.failure(err)
      );
  }
}
