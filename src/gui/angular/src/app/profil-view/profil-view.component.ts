import {Component, OnInit} from "@angular/core";
import {AuthenticationService} from "../login-component/Authentication.service";
import {ProfilEditService} from "../profil-edit/profil-edit-service.component";
import {NotificationService} from "../utils/notificationService.service";
import {Profil} from "../profil-edit/profil-edit.component";

@Component({
    selector: "app-main-view",
    templateUrl: "./profil-view.component.html",
    styleUrls: ["./profil-view.component.css"]
})
export class ProfilViewComponent implements OnInit {
    constructor(private authService: AuthenticationService, private profilEditService: ProfilEditService, private notificationService: NotificationService) {
    }

    profil: Profil = new Profil();

    ngOnInit() {
    }

    isAuthenticated()
    {
        return this.authService.isAuthenticated();
    }

}