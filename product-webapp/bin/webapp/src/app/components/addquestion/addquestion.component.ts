import { HttpClient} from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Question } from 'src/app/model/question.model';
import { AddquestionService } from 'src/app/service/addquestion.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { MarkdownparserService } from 'src/app/service/markdownparser.service';
import * as marked from 'marked';

@Component({
  selector: 'app-addquestion',
  templateUrl: './addquestion.component.html',
  styleUrls: ['./addquestion.component.css']
})
export class AddquestionComponent implements OnInit {
  dropdownList:any;
  dropdownSettings:any;
  public addQuestionform !: FormGroup;
  public questionObj = new Question();

  compiledMarkdown: string;
  startingValue = '';


  constructor(private fromBuilder:FormBuilder, private router:Router, private questionserviceApi:AddquestionService,private md: MarkdownparserService, private authService:AuthenticationService) { }

  ngOnInit(){
    this.startingValue = this.getPlaceHolder();
    this.compiledMarkdown = this.compileMarkdown(this.startingValue);
    this.dropdownList = this.getData();
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All'
    };
    this.addQuestionform = this.fromBuilder.group({
      strQuestionTitle:['', Validators.required],
      interests:['', Validators.required],
    })
    
  }
  convertedText: string = "";


  updateOutput(mdText: string) {
    this.convertedText = this.md.convert(mdText);
  }
  addQuestion(){
    console.log(this.addQuestionform.value);
    this.questionObj.arrQuestionTags = this.addQuestionform.value.interests;
    this.questionObj.strQuestionTitle = this.addQuestionform.value.strQuestionTitle;
    this.questionObj.strQuestionerEmail = this.authService.getEmail();
    this.questionserviceApi.addQuestion(this.questionObj).subscribe(res => {

    });
    alert("Question Added Successfully!! Click Ok for Question Dashboard.")
    this.router.navigate(['dashboard']);
  }

  getData() : Array<any>{
    return [
      "Java", "HTML", "CSS", "Spring", "Others"
    ];
  }

  onValueChanged(value: string) {
    this.compiledMarkdown = this.compileMarkdown(value);
    this.addQuestionform.get("strQuestionTitle").setValue(this.compiledMarkdown);
  }

  private compileMarkdown(value: string): string {
    return marked.parser(marked.lexer(value));
  }

  private getPlaceHolder() {
    return (
      '# Ask your question here...!!!'
    );
  }

  outputQuestion() {
    console.log("question:", this.compiledMarkdown);
  }

}
