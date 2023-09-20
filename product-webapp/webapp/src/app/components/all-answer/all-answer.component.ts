import { analyzeAndValidateNgModules, core } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Login } from 'src/app/model/login.model';
import { AnswerService } from 'src/app/service/answer.service';
import { QuestionService } from 'src/app/service/question.service';
import { Comment } from 'src/app/model/comment.model';
import { AuthenticationService } from 'src/app/service/authentication.service';
@Component({
  selector: 'app-all-answer',
  templateUrl: './all-answer.component.html',
  styleUrls: ['./all-answer.component.css']
})
export class AllAnswerComponent implements OnInit {
  question: any;
  questionId:any;
  answers: any;
  allComments:Array<string>;
  x : string = '';
  commentObj : Comment = new Comment();
  constructor(private fb: FormBuilder,private activatedroute: ActivatedRoute, private answerService: AnswerService, private questionService:QuestionService, private authService:AuthenticationService) { }
  ngOnInit(): void {
    this.questionId= this.activatedroute.snapshot.paramMap.get("id");
    console.log("selected question:", this.questionId);
    this.question = this.questionService.getQuestionById(this.questionId).subscribe(data=> this.question=data);
    this.answerService.getAnswerById(this.questionId).subscribe(data => {
      this.answers=data;
      console.log("answer:", this.answers);
    })
  }
  commentForm = this.fb.group({
    comment: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(250)]]
  });
  upVote(answer){
    this.answerService.upVote(answer).subscribe(data=> console.log(data));
  }
  downVote(answer){
    this.answerService.downVote(answer).subscribe(data=> console.log(data));
  }
  refreshAnswer(answer){
    this.answerService.getAnswerById(this.questionId).subscribe(data => {
      this.answers=data;
    });
    location.reload();
  }
  noOfUpVote(answer){
    return answer?.intAnswerUpvote;
  }
  logOut(){
    this.authService.logOut();
  }
  acceptAnswer(answer){
    this.answerService.acceptAnswer(answer).subscribe(data=> console.log(data));
    location.reload();
  }
  compare(answer){
    if(this.question.strQuestionerEmail===this.authService.getEmail()){
      if( !answer.accepted){
      return false
      }
    }
    return true ;
  }
    
  // showComments(answer){
  //   this.answerService.showAllComments(answer).subscribe(data=> { 
  //     for(let cmnt in data){
  //       if(data[cmnt].strComment==null){
  //         continue;}
  //         else{
  //       console.log(data[cmnt].strComment);
  //     //this.allComments.push(data[cmnt].strComment);
  //     this.x+= String(data[cmnt].strComment) + "<br>";
  //         }
  //     }
  //   });
  // }
  comment(answer){
    console.log(this.commentForm.value.comment)
     this.commentObj.strComment = this.commentForm.value.comment;
     this.commentObj.strCommentedBy = this.authService.getEmail();
    this.answerService.comment(answer,this.commentObj).subscribe(data=>console.log(data));
    this.answerService.getAnswerById(this.questionId).subscribe(data => {
      this.answers=data;
    })
  }

}
