import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Answer } from '../model/answer.model';


@Injectable({
  providedIn: 'root'
})
export class AnswerService {
  constructor(private http: HttpClient) { }
  saveAnswer(answer:Answer,questionId){
    console.log(answer);
   return  this.http.post<Answer>(`http://localhost:8085/api/v1/answers/postanswers/${questionId}`, answer); 
  }

  getAnswerById(questionId: any) {
    return this.http.get<any>(`http://localhost:8085/api/v1/answers/getanswers/${questionId}`);
  }
  upVote(answer){
    return this.http.put(`http://localhost:8085/api/v1/answers/upvote/${answer.intAnswerId}`,answer);
  }
  downVote(answer){
    return this.http.put(`http://localhost:8085/api/v1/answers/downvote/${answer.intAnswerId}`,answer);
  }
  acceptAnswer(answer){
    return this.http.put(`http://localhost:8085/api/v1/answers//acceptAnswer/${answer.intAnswerId}`,answer);
  }
  showAllComments(answer){
    return this.http.get(`http://localhost:8085/api/v1/answers/allcomments/${answer.intAnswerId}`);
  }
  comment(answer,comment){
    return this.http.put(`http://localhost:8085/api/v1/answers/comment/${answer.intAnswerId}`,comment);
  }

  getAllAnswers(emailId){
    return this.http.get("http://localhost:8085/api/v1/answers/" + emailId);
  }
  
}
