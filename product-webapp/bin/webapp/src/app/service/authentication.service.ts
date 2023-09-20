import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  public registerURL = "http://localhost:9090/api/v1/auth/login";

  constructor(private _http:HttpClient) { }

  logIn(credentials: any){
    console.log(credentials);
    return this._http.post<any>( this.registerURL, credentials);
  }


  loginUser(token, strUserEmail){
    localStorage.setItem("userEmail",strUserEmail);
    localStorage.setItem("token",token);
    return true;
  }

  isloggedIn(){
    let token = localStorage.getItem("token");
    if(token==undefined || token == "" || token == null){
      return false;
    }
    return true;
  }

  getEmail(){
        return localStorage.getItem("userEmail");
  }

  logOut(){
    localStorage.removeItem("token");
    localStorage.removeItem("userEmail");
    return true;
  }

 getToken(){
   return localStorage.getItem("token");
 }

}
