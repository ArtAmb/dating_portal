import { Component, OnInit, Input } from "@angular/core";
import { AuthenticationService } from "../login-component/Authentication.service";
import { ProfilEditService } from "../profil-edit/profil-edit-service.component";
import { NotificationService } from "../utils/notificationService.service";
import { Profil } from "../profil-edit/profil-edit.component";
import { ActivatedRoute } from "@angular/router";
import { ProfilViewService } from "./profil-view-service.component";
import { UserProfil } from "../main-view/main-view/profil/user-profil.service";
import {
  ConvertEyeColor,
  ConvertGender,
  ConvertHairColor,
  ConvertRegion
} from "../utils/ProfilAttributesConverter";

@Component({
  selector: "app-profil-view",
  templateUrl: "./profil-view.component.html",
  styleUrls: ["./profil-view.component.css"]
})
export class ProfilViewComponent implements OnInit {
  constructor(
    private authService: AuthenticationService,
    private profilViewService: ProfilViewService,
    private notificationService: NotificationService,
    private route: ActivatedRoute,
    private profilEditService: ProfilEditService
  ) {}

  profil: Profil = new Profil();
  @Input() userId: Number;

  _convertGender(g: any): any {
    return ConvertGender(g);
  }
  _convertEyeColor(g: any): any {
    return ConvertEyeColor(g);
  }
  _convertHairColor(g: any): any {
    return ConvertHairColor(g);
  }
  _convertRegion(g: any): any {
    return ConvertRegion(g);
  }

  ngOnInit() {
    if (this.userId) {
      this.route.params.subscribe(params => {
        this.userId = +params["id"];
        this._init();
      });
    } else {
      this._init();
    }
  }

  private _init() {
    if (this.userId) {
      this.profilViewService.findProfilByUserId(this.userId).subscribe(
        res => {
          this.notificationService.success("Udalo sie zaladowac uzytkownika");
          this.profil = res;
        },
        err => {
          this.notificationService.failure("Nie ma takiego uzytkownika");
        }
      );
    } else {
      this.profilEditService.findUserProfil().subscribe(
        res => {
          this.notificationService.success("Udalo sie zaladowac uzytkownika");
          this.profil = res;
        },
        err => {
          this.notificationService.failure("Nie ma takiego uzytkownika");
        }
      );
    }
  }

  isAuthenticated() {
    return this.authService.isAuthenticated();
  }
}
