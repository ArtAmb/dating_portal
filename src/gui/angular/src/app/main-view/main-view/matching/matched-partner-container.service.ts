import { Injectable } from "@angular/core";
import { PotentialPartner } from "./matching-service.service";

@Injectable({
  providedIn: "root"
})
export class MatchedPartnerContainerService {
  potentialPartners: Array<PotentialPartner> = [];

  constructor() {}


  save(potentialPartners: Array<PotentialPartner>) {
    this.potentialPartners = potentialPartners;
  }
}
