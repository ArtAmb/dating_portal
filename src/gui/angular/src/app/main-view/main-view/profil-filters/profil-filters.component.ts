import {Component, OnInit} from "@angular/core";
import {GENDER, REGION} from "src/app/profil-edit/profil-edit.component";
import {AuthenticationService} from "../../../login-component/Authentication.service";
import {ProfilFiltersService} from "./profil-filters-service.component";
import {NotificationService} from "../../../utils/notificationService.service";

@Component({
    selector: "app-profil-filters",
    templateUrl: "./profil-filters.component.html",
    styleUrls: ["./profil-filters.component.css"]
})
export class ProfilFiltersComponent implements OnInit{


    preferredGender : GENDER = GENDER.Default;
    preferredRegion : REGION = REGION.Default;

    prefAgeMin : number = 0;
    prefAgeMax : number = 100;


    constructor(private profilFiltersService : ProfilFiltersService,private notificationService : NotificationService) {

    }

    ngOnInit(): void {

    }

    update(){
        this.profilFiltersService.updateSearchInfo({gender: this.preferredGender,region: this.preferredRegion}).subscribe(
            res=>{this.notificationService.success("Filtry zaktualizowane"),
                    err=>{this.notificationService.failure("Błąd")}});
    }

}

export class Preferred{
    gender : GENDER = GENDER.Default;
}
