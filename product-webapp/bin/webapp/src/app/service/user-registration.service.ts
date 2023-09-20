import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

export class UserRegistrationService {

  public registerURL = "http://localhost:8090/api/v1/registration";

  constructor(private _http:HttpClient) { }

  signUp(userObj: any){
    console.log(userObj);
    return this._http.post<any>(this.registerURL + "/addUser", userObj)
  }
}


