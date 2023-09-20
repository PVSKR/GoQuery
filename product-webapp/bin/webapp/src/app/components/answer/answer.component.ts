import { Component, OnInit } from '@angular/core';
import { validateBasis } from '@angular/flex-layout';
import { FormBuilder, Validators, } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import * as marked from 'marked';
import { Answer } from 'src/app/model/answer.model';

import { AnswerService } from 'src/app/service/answer.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { MarkdownParserService } from 'src/app/service/markdown-parser.service';
import { QuestionService } from 'src/app/service/question.service';

@Component({
  selector: 'app-answer',
  templateUrl: './answer.component.html',
  styleUrls: ['./answer.component.css']
})
export class AnswerComponent implements OnInit {
  question: any;
  questionId: any;
  answerObject: Answer = new Answer();
  compiledMarkdown: string;
  startingValue = '';
  constructor(private md: MarkdownParserService, private fb: FormBuilder, private answerService: AnswerService, private activatedroute: ActivatedRoute, private service: QuestionService, private router: Router, private authService:AuthenticationService) {
  }
  

  ngOnInit() {
    this.startingValue = this.getPlaceHolder();
    this.compiledMarkdown = this.compileMarkdown(this.startingValue);
    this.questionId = this.activatedroute.snapshot.paramMap.get("id");
    console.log(this.questionId);
    this.question = this.service.getQuestionById(this.questionId).subscribe(data => this.question = data);
    this.compiledMarkdown = this.compileMarkdown(this.startingValue);
  }

  answerForm = this.fb.group({
    answer: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(250)]]
  });
  convertedText: string = "";


  updateOutput(mdText: string) {
    this.convertedText = this.md.convert(mdText);
  }
  onSubmit() {
    this.answerObject.strAnswerStatement = this.answerForm.value.answer;
    this.answerObject.strAnswererEmail = this.authService.getEmail();
    this.answerObject.intAnswerUpvote = 0;
    this.answerObject.intAnswerDownvote = 0;
    this.answerObject.accepted = true;
    this.answerService.saveAnswer(this.answerObject, this.questionId).subscribe(Response => console.log(this.answerObject.strAnswerStatement));
    alert("Answer submitted successfully, Please Click Ok");
    console.log("answered by: " + this.authService.getToken() + " ...... " + this.authService.getEmail());
    this.answerForm.reset;
    this.router.navigate(['dashboard']);

  }

  onValueChanged(value: string) {
    this.compiledMarkdown = this.compileMarkdown(value);
    this.answerForm.get("answer").setValue(this.compiledMarkdown);
  }

  private compileMarkdown(value: string): string {
    return marked.parser(marked.lexer(value));
  }

 
  

  private getPlaceHolder() {
    return (
      '# Write Answer...!!!'
    );
  }
  outputAnswer() {
    console.log("answer:", this.compiledMarkdown);
  }

}
