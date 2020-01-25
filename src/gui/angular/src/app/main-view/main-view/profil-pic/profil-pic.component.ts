import {Component, OnInit} from "@angular/core";
import {GalleryService} from "../gallery/gallery.service";
import {NotificationService} from "../../../utils/notificationService.service";
import {UserProfilService} from "../profil/user-profil.service";
import {ImageInfo} from "../user-images/user-images.component";
import {ActivatedRoute} from "@angular/router";
import {ProfilViewService} from "../../../profil-view/profil-view-service.component";


@Component({
    selector: "app-profil-pic",
    templateUrl: "./profil-pic.component.html",
    styleUrls: ["./profil-pic.component.css"]
})
export class ProfilPicComponent implements OnInit {
    constructor(
        private galleryService: GalleryService,
        private notificationService: NotificationService,
        private userProfilService: UserProfilService,
        private profilViewService : ProfilViewService,
        private route : ActivatedRoute

    ) {}
    public avatarImage: ImageInfo = null;
    private userId : number;

    ngOnInit() {

        this.route.params.subscribe(params => {
            this.userId = +params['id'];

        });

        if(!isNaN(this.userId))
        {
            this.profilViewService.findProfilByUserId(this.userId).subscribe(res => {
                this.notificationService.success("Udalo sie zaladowac uzytkownika");
                if (res.avatarImageId) {
                    let tmp = new ImageInfo();
                    tmp.imageId = res.avatarImageId.valueOf();

                    this.avatarImage = tmp;
                }
            }, err => {
                this.notificationService.failure("Nie ma takiego uzytkownika");
            });
        }
        else {
            this.userProfilService.findUserProfil().subscribe(
                res => {
                    if (res.avatarImageId) {
                        let tmp = new ImageInfo();
                        tmp.imageId = res.avatarImageId.valueOf();

                        this.avatarImage = tmp;
                    }
                },
                err => this.notificationService.failure(err)
            );
        }




    }
}