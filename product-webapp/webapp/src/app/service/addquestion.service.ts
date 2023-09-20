import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AddquestionService {

  constructor(private _http:HttpClient) { }

  addQuestion(questionObj: any){
    console.log(questionObj);
    return this._http.post<any>("http://localhost:8085/api/v1/questions/addquestion", questionObj)
  }
}
