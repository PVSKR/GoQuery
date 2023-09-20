import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Question } from 'src/app/model/question.model';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { QuestionService } from 'src/app/service/question.service';
import { SearchService } from 'src/app/service/search.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  questions:any;
  flag:boolean= false;
  searchQuery: string;
  constructor(public dialog: MatDialog,private service:QuestionService,private router: Router, private authService:AuthenticationService,private searching: SearchService) {}
  ngOnInit(){
    this.service.getQuestions().subscribe(data => {
      this.questions = data;
      console.log("questions:"+this.questions);
    })
  }

  upVote(question){
    question.intQuestionUpvote +=1;
  }
  downVote(question){
    question.intQuestionDownvote -=1;
  }
  getAllAnswers(questionID) {
    console.log("question id:", questionID);
    this.router.navigate([`/allAnswers/${questionID}`]);
  }
  search(){
    console.log("search query:", this.searchQuery);
    this.searching.search(this.searchQuery).subscribe((data) => {
      console.log("search result:", data);
    })
  }
  logOut(){
    this.authService.logOut();
  }
}
// @Component({
//   selector: 'app-addquestion',
//   templateUrl: 'src\app\components\addquestion\addquestion.component.html',
//   styleUrls: ['src\app\components\addquestion\addquestion.component.css']
// })
// export class AddquestionComponent{}
