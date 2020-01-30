import { Component, Inject, OnInit } from "@angular/core";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { PotentialPartner } from "src/app/main-view/main-view/matching/matching-service.service";

@Component({
  selector: "app-welcome-message-dialog",
  templateUrl: "./welcome-message-dialog.component.html",
  styleUrls: ["./welcome-message-dialog.component.css"]
})
export class WelcomeMessageDialogComponent implements OnInit {
  potentialPartner: PotentialPartner;
  userId: Number;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    this.potentialPartner = data.potentialPartner;
    this.userId = data.userId;
  }
  ngOnInit() {}
}
