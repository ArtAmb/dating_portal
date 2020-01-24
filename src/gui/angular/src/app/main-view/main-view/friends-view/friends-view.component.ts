import {Component, OnInit} from "@angular/core";
import {AuthenticationService} from "../../../login-component/Authentication.service";
import {ProfilEditService} from "../../../profil-edit/profil-edit-service.component";
import {NotificationService} from "../../../utils/notificationService.service";
import {ActivatedRoute} from "@angular/router";
import {GENDER, Profil} from "../../../profil-edit/profil-edit.component";
import {FriendsViewService} from "./friends-view-service.component.cs";
import {ConvertRegion,ConvertHairColor,ConvertEyeColor,ConvertGender} from "../../../utils/ProfilAttributesConverter";

@Component({
    selector: "app-friends-view",
    templateUrl: "./friends-view.component.html",
    styleUrls: ["./friends-view.component.css"]
})
export class FriendsViewComponent implements OnInit {
    constructor(private authService: AuthenticationService,
                private friendsViewService: FriendsViewService,
                private notificationService: NotificationService) {
    }

    array: Array<Profil> = new Array<Profil>()
    _convertGender(g :any) : any
    {
        return ConvertGender(g);
    }
    _convertEyeColor(g:any) : any
    {
        return ConvertEyeColor(g);
    }
    _convertHairColor(g:any) : any
    {
        return ConvertHairColor(g);
    }
    _convertRegion(g:any) : any
    {
        return ConvertRegion(g);
    }

    ngOnInit() {

      this.friendsViewService.findAllProfils().subscribe(res=>{this.notificationService.success("Udalo się wczytać użytkowników!");  this.array = res;})
    }

    isAuthenticated()
    {
        return this.authService.isAuthenticated();
    }

}