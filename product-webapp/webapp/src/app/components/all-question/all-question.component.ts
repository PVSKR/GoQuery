import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { QuestionService } from 'src/app/service/question.service';

@Component({
  selector: 'app-all-question',
  templateUrl: './all-question.component.html',
  styleUrls: ['./all-question.component.css']
})
export class AllQuestionComponent implements OnInit {

  questions:any;

  constructor(private service:QuestionService, private router: Router) { }

  ngOnInit(): void {
    this.service.getQuestions().subscribe(data => {
      this.questions = data;
      console.log("questions:"+this.questions);
    })
  }

  getAllAnswers(questionID) {
    console.log("question id:", questionID);
    this.router.navigate([`/allAnswers/${questionID}`]);
  }

}
