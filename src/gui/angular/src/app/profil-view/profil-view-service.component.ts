import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Profil} from "../profil-edit/profil-edit.component";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";


@Injectable({
    providedIn: "root"
})
export class ProfilViewService
{
    constructor(private http: HttpClient) {
    }

    findProfilByUserId(userId : any) : Observable<any>{



        return this.http.get(environment.server_url + "/user/profil-view/?userId="+userId);
    }

}