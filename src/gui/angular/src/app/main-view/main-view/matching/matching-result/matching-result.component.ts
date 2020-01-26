import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { MatchedPartnerContainerService } from "../matched-partner-container.service";

@Component({
  selector: "app-matching-result",
  templateUrl: "./matching-result.component.html",
  styleUrls: ["./matching-result.component.css"]
})
export class MatchingResultComponent implements OnInit {
  constructor(private route: ActivatedRoute, private matchedPartnerContainerService: MatchedPartnerContainerService) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      console.log(params)
      console.log(params["potentialPartners"]);
    })
  }

  public getPartners() {
    return this.matchedPartnerContainerService.potentialPartners;
  }
}
