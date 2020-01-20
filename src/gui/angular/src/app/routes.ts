import { LoginComponentComponent } from "./login-component/login-component.component";
import { MainViewComponent } from "./main-view/main-view/main-view.component";
import {ProfilEditComponent} from "./profil-edit/profil-edit.component";

export const APP_ROUTES = [{ path: "", component: MainViewComponent },{path:"profil-edit",component: ProfilEditComponent}];
