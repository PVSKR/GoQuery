import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { QuestionService } from 'src/app/service/question.service';
@Component({
  selector: 'app-questionlanding',
  templateUrl: './questionlanding.component.html',
  styleUrls: ['./questionlanding.component.css']
})
export class QuestionlandingComponent implements OnInit {
  questions:any;
  constructor(private service:QuestionService,private router : Router) {}
  ngOnInit(){
    this.questions = this.service.getQuestions().subscribe(data => this.questions = data);
  }
  addAnswer(questionID){
    console.log("question id:", questionID);
    this.router.navigate([`/answer/${questionID}`]);
  }
  // upVote(question){
  //   question.intQuestionUpvote +=1;
  // }
  // downVote(question){
  //   question.intQuestionDownvote -=1;
  // }
}
