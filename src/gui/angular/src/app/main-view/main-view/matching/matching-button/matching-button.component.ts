import { Component, OnInit, Input } from "@angular/core";
import {
  MatchingServiceService,
  PotentialPartner
} from "../matching-service.service";
import { NotificationService } from "src/app/utils/notificationService.service";
import { Router } from "@angular/router";
import { MatchedPartnerContainerService } from "../matched-partner-container.service";

@Component({
  selector: "app-matching-button",
  templateUrl: "./matching-button.component.html",
  styleUrls: ["./matching-button.component.css"]
})
export class MatchingButtonComponent implements OnInit {
  @Input() context: MatchingButtonComponentMode;

  constructor(
    private matchingService: MatchingServiceService,
    private notificationService: NotificationService,
    private router: Router,
    private matchedPartnerContainerService: MatchedPartnerContainerService 
  ) {}

  ngOnInit() {}

  matchingInProgress: boolean = false;
  potentialPartners: Array<PotentialPartner> = [];

  showRedirectButton: boolean = false;

  startMatching() {
    this.matchingInProgress = true;

    this.matchingService.matching().subscribe(
      res => {
        this.potentialPartners = res;
        
        this.matchingInProgress = false;
        this.showRedirectButton = true;
        this.matchedPartnerContainerService.save(this.potentialPartners);
        this.notificationService.success();
      },
      err => {
        this.matchingInProgress = false;

        this.notificationService.failure(err);
      }
    );
  }

  startBlindDate() {
    this.notificationService.success();
  }

  redirectToResult() {
    this.showRedirectButton = false;
    this.router.navigate(["/matching-result"]);
  }
}

export enum MatchingButtonComponentMode {
  DATE = "DATE",
  SEARCH = "SEARCH"
}
