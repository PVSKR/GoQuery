import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Login } from 'src/app/model/login.model';
import { AuthenticationService } from 'src/app/service/authentication.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public loginform !: FormGroup;
  public credentials = new Login();

  constructor(private formBuilder:FormBuilder, private authService:AuthenticationService, private router:Router) { }

  ngOnInit(): void {
    this.loginform = this.formBuilder.group({
      strUserEmail : ['',Validators.required],
      strpassword : ['',Validators.required]
    });
  }

  logIn(){
    this.credentials.strUserEmail = this.loginform.value.strUserEmail;
    this.credentials.strpassword = this.loginform.value.strpassword;
    this.authService.logIn(this.credentials).subscribe(
      response =>{
        console.log(response);
        this.authService.loginUser(response.token,response.userEmail);
        window.location.href = "/dashboard";
      },
      error =>{
        console.log(error.error);
        alert(error.error);
      }
    )
    console.log(this.credentials);
  }
}




