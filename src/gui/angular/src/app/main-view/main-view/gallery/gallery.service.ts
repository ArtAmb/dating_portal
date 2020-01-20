import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "./../../../../environments/environment";
import { Observable } from "rxjs";
import { Gallery, ImageInfo } from "../user-images/user-images.component";

@Injectable({
  providedIn: "root"
})
export class GalleryService {
  constructor(private httpClient: HttpClient) {}

  public uploadImage(formData: FormData): Observable<ImageInfo> {
    return this.httpClient.post<ImageInfo>(environment.server_url + "/upload-image", formData);
  }

  public createImageUrl(imageId: number) {
    return environment.server_url + "/image/" + imageId;
  }

  public findUserGalleries(): Observable<any> {
    return this.httpClient.get(environment.server_url + "/user/galleries");
  }

  public addNewGallery(dto: any): Observable<any> {
    return this.httpClient.post(environment.server_url + "/user/galleries/add", dto);
  }
  
  public updateGallery(dto: any): Observable<any>  {
    return this.httpClient.post(environment.server_url + "/gallery/update", dto);
  }

  public publishAvatarImage(dto: any): Observable<any>  {
    return this.httpClient.post(environment.server_url + "/upload-avatar-image", dto);
  }
  
}
