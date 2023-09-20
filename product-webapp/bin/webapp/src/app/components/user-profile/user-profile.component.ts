import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AnswerService } from 'src/app/service/answer.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { QuestionService } from 'src/app/service/question.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  questionList: any;
  answersList: any;
  emailId:any;

  constructor(private router:Router, private quesService:QuestionService, private answerService:AnswerService ,private authService:AuthenticationService) { }

  ngOnInit(): void {
    this.emailId = this.authService.getEmail();
    console.log(this.emailId);
    this.questionList = this.quesService.getQuestionByEmail(this.emailId).subscribe(res => {
      this.questionList = res;
    })

    this.answersList = this.answerService.getAllAnswers(this.emailId).subscribe(res => {
      this.answersList = res;
    })
  }
  getAllAnswers(questionID) {
    console.log("question id:", questionID);
    this.router.navigate([`/allAnswers/${questionID}`]);
  }

}
