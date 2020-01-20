import {Component, OnInit} from "@angular/core";
import {AuthenticationService} from "../login-component/Authentication.service";
import {ProfilEditService} from "./profil-edit-service.component";
import {NotificationService} from "../utils/notificationService.service";

export class Profil{
    id :Number;
    displayLogin : Text;
    description : Text;
}

@Component({
    selector: "app-main-view",
    templateUrl: "./profil-edit.component.html",
    styleUrls: ["./profil-edit.component.css"]
})
export class ProfilEditComponent implements OnInit {
    constructor(private authService: AuthenticationService,private profilEditService : ProfilEditService, private notificationService: NotificationService) {}

    profil : Profil = new Profil();

    ngOnInit() {
        this.profilEditService.findUserProfil().subscribe(res=>{this.profil= res});
    }

    isAuthenticated() {
        return this.authService.isAuthenticated();



    }

    save()
    {
        this.profilEditService.updateUserProfil(this.profil)
            .subscribe( res => { this.notificationService.success("Dane zaktualizowane!")},
                        err =>{this.notificationService.failure("Błąd aktualizacji danych")} );
    }


}
