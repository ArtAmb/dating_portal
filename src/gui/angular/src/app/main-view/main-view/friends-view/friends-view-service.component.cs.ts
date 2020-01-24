import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment";

@Injectable({
    providedIn: "root"
})
export class FriendsViewService {

    constructor(private http: HttpClient) {
    }

    public findAllProfils() : Observable<any>
    {
        return this.http.get(environment.server_url + "/user/profil-all")
    }

}