import {Component, OnInit} from "@angular/core";
import {AuthenticationService} from "../login-component/Authentication.service";
import {ProfilEditService} from "./profil-edit-service.component";
import {NotificationService} from "../utils/notificationService.service";



export class Profil{
    id :Number;
    displayLogin : Text;
    description : Text;
    birthDate : Date;
    gender : GENDER;
    height : Number;
    weight : Number;
    eyeColor : EYE_COLOR;
    hairColor : HAIR_COLOR;
    region : REGION;

}

export enum GENDER {
    Male = 1,
    Female = 2,
    None = 3
}

export enum EYE_COLOR {
    Blue=0,
    Brown=1,
    Green=2,
    Hazel=3,
    Gray=4,
    Other=5
}

export enum HAIR_COLOR {
    Brown=0,
    Blonde=1,
    Black=2,
    Red=3,
    Other=4
}

export enum REGION {
    Dolnoslaskie,
    Kujawsko_pomorskie,
    Lubelskie,
    Lubuskie,
    Lodzkie,
    Malopolskie,
    Mazowieckie,
    Opolskie,
    Podkarpackie,
    Podlaskie,
    Pomorskie,
    Slaskie,
    Swietokrzyskie,
    Warminsko_mazurskie,
    Wielkopolskie,
    Zachodniopomorskie
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
