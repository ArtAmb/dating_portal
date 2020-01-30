import { Component, OnInit } from "@angular/core";
import {
  EYE_COLOR,
  GENDER,
  HAIR_COLOR,
  REGION
} from "src/app/profil-edit/profil-edit.component";
import { ProfilFiltersService } from "./profil-filters-service.component";
import { NotificationService } from "../../../utils/notificationService.service";

@Component({
  selector: "app-profil-filters",
  templateUrl: "./profil-filters.component.html",
  styleUrls: ["./profil-filters.component.css"]
})
export class ProfilFiltersComponent implements OnInit {
  preferredGender: GENDER = GENDER.Default;
  preferredRegion: REGION = REGION.Default;
  preferredEyeColor: EYE_COLOR = EYE_COLOR.Default;
  preferredHairColor: HAIR_COLOR = HAIR_COLOR.Default;

  prefAgeMin: number = 0;
  prefAgeMax: number = 100;

  constructor(
    private profilFiltersService: ProfilFiltersService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.profilFiltersService.getSearchInfo().subscribe(
      res => {
        // this.preferredGender = res.preferredGender
        //   ? GENDER[res.preferredGender]
        //   : GENDER.Default;

        // console.log(this.preferredGender);
        // this.preferredRegion = REGION[res.preferredRegion]
        //   ? res.preferredRegion
        //   : REGION.Default;
        // this.preferredEyeColor = EYE_COLOR[res.preferredEyeColor]
        //   ? res.preferredEyeColor
        //   : EYE_COLOR.Default;
        // this.preferredHairColor = HAIR_COLOR[res.preferredHairColor]
        //   ? res.preferredHairColor
        //   : HAIR_COLOR.Default;
      },
      err => {
        this.notificationService.failure(err);
      }
    );
  }

  update() {
    this.profilFiltersService
      .updateSearchInfo({
        gender: this.preferredGender,
        region: this.preferredRegion,
        eyeColor: this.preferredEyeColor,
        hairColor: this.preferredHairColor
      })
      .subscribe(
        res => {
          this.notificationService.success("Filtry zaktualizowane");
        },
        err => {
          this.notificationService.failure(err);
        }
      );
  }
}

export class Preferred {
  gender: GENDER = GENDER.Default;
}
