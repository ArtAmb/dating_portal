import { Component, OnInit, Inject } from "@angular/core";
import { PotentialPartner } from "../../../matching-service.service";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";

@Component({
  selector: "app-potential-partner-dialog",
  templateUrl: "./potential-partner-dialog.component.html",
  styleUrls: ["./potential-partner-dialog.component.css"]
})
export class PotentialPartnerDialogComponent implements OnInit {
  potentialPartner: PotentialPartner;
  
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    this.potentialPartner = data.potentialPartner;
  }

  ngOnInit() {}
}
