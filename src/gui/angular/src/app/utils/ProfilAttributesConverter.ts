import {GENDER} from "../profil-edit/profil-edit.component";

export function ConvertGender(g : any) : any {

    switch (g) {
        case "None": return "Brak";
        case "Female": return "Kobieta";
        case "Male": return "Mężczyzna";
        default: return "Brak";
    }

}

export function ConvertEyeColor(g : any) : any {

    switch (g) {
        case "Blue": return "Niebieskie";
        case "Brown": return "Brązowe";
        case "Green": return "Zielone";
        case "Hazel": return "Piwne";
        case "Gray": return "Szare";
        case "Other": return "Inne";
        default: return "Brak";
    }

}

export function ConvertHairColor(g : any) : any {

    switch (g) {
        case "Brown": return "Brązowy";
        case "Blonde": return "Blond";
        case "Black": return "Czarny";
        case "Red": return "Rudy";
        case "Other": return "Inne";
        default: return "Brak";
    }

}

export function ConvertRegion(g : any) : any {

    switch (g) {
        case "Dolnoslaskie": return "dolnośląskie";
        case "Kujawsko_pomorskie": return "kujawsko-pomorskie";
        case "Lubelskie": return "lubelskie";
        case "Lubuskie": return "lubuskie";
        case "Lodzkie": return "łódzkie";
        case "Malopolskie": return "małopolskie";
        case "Mazowieckie": return "mazowieckie";
        case "Opolskie": return "opolskie";
        case "Podkarpackie": return "podkarpackie";
        case "Podlaskie": return "podlaskie";
        case "Pomorskie": return "pomorskie";
        case "Slaskie": return "śląskie";
        case "Swietokrzyskie": return "świętokrzyskie";
        case "Warminsko_mazurskie": return "warmińsko-mazurskie";
        case "Wielkopolskie": return "wielkopolskie";
        case "Zachodniopomorskie": return "zachodniopomorskie";
        default: return "Brak";
    }

}




