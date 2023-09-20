import { HttpClient} from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserModel } from 'src/app/model/user.model';
import { UserRegistrationService } from 'src/app/service/user-registration.service';
// import { UserRegistrationService } from '../user-registration.service';
// import { UserModel } from '../user.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  dropdownList:any;
  dropdownSettings:any;
  public signupform !: FormGroup;
  public signupObj = new UserModel();
  public matching:boolean = false;

  constructor(private fromBuilder:FormBuilder, private http:HttpClient, private router:Router, private userServiceApi:UserRegistrationService) { }

  ngOnInit(): void {
    this.dropdownList = this.getData();
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All'
    };
    this.signupform = this.fromBuilder.group({
      strUserName:['', Validators.required],
      strUserEmail:['', Validators.required],
      password:['', Validators.required],
      confirmPassword:['', Validators.required],
      strUserType:[''],
      interests:['', Validators.required],
    })
  }

  signUp(){
    console.log(this.signupform.value);
    this.signupObj.strUserName = this.signupform.value.strUserName;
    this.signupObj.strUserEmail = this.signupform.value.strUserEmail;
    this.signupObj.strUserType = "Basic";
    this.signupObj.strpassword = this.signupform.value.password;
    this.signupObj.strConfirmPassword = this.signupform.value.confirmPassword;
    this.signupObj.arrUserInterest = this.signupform.value.interests;
    if(this.signupform.value.password == this.signupform.value.confirmPassword){
      this.matching = true;
    }

    this.userServiceApi.signUp(this.signupObj).subscribe(res => {
    });
    this.router.navigate(['login']);
  }

  getData() : Array<any>{
    return [
      "Java", "HTML", "CSS", "Spring", "Others"
    ];
  }
}
