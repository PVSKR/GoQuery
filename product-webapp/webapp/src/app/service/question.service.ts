import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private http: HttpClient) { }
  

  getQuestions(){
    return this.http.get("http://localhost:8085/api/v1/questions/getquestions");
  }
  getQuestionById(id){
    return this.http.get("http://localhost:8085/api/v1/questions//questionbyid/"+String(id))
  }

  getQuestionByEmail(email){
    return this.http.get("http://localhost:8085/api/v1/questions/"+String(email));
  }

}
