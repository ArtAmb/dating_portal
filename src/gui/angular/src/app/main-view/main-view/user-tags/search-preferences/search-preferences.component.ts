import { Component, OnInit } from "@angular/core";
import { NotificationService } from "src/app/utils/notificationService.service";
import {
  ProfilFiltersService,
  PartnerSearchAlgorithm
} from "../../profil-filters/profil-filters-service.component";

@Component({
  selector: "app-search-preferences",
  templateUrl: "./search-preferences.component.html",
  styleUrls: ["./search-preferences.component.css"]
})
export class SearchPreferencesComponent implements OnInit {
  constructor(
    private notificationService: NotificationService,
    private profilFiltersService: ProfilFiltersService
  ) {}

  algorithm: PartnerSearchAlgorithm;

  updateAlgorithm() {
    this.profilFiltersService
      .updateSearchAlgorithm({
        partnerSearchAlgorithm: this.algorithm
      })
      .subscribe(
        res => {
          this.notificationService.success("Udalo sie zaktualizowac");
        },
        err => {
          this.notificationService.failure(err);
        }
      );
  }

  ngOnInit() {
    this.profilFiltersService.getSearchInfo().subscribe(
      res => {
        this.algorithm = res.algorithmType;
      },
      err => {
        this.notificationService.failure(err);
      }
    );
  }
}
