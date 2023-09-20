import { BrowserModule } from '@angular/platform-browser';
import { NgModule,CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import { DashboardComponent } from './components/dashboard/dashboard.component';

import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AboutComponent } from './components/about/about.component';
import { LandingComponent } from './components/landing/landing.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatCardModule} from '@angular/material/card';

import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { AnswerComponent } from './components/answer/answer.component';

import {MatFormFieldModule} from '@angular/material/form-field';
import { AnswerService } from './service/answer.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { QuestionlandingComponent } from './components/questionlanding/questionlanding.component';
import { AllQuestionComponent } from './components/all-question/all-question.component';
import { AllAnswerComponent } from './components/all-answer/all-answer.component';
import { AddquestionComponent } from './components/addquestion/addquestion.component';

import { MarkdownParserService } from './service/markdown-parser.service';
import { MarkdownComponent } from './components/markdown/markdown.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    LoginComponent,
    RegisterComponent,
    AboutComponent,
    LandingComponent,
    AnswerComponent,
    QuestionlandingComponent,
    AllQuestionComponent,
    AllAnswerComponent,
    AddquestionComponent,
    MarkdownComponent,
    UserProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
    MatCardModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgMultiSelectDropDownModule.forRoot(),
   // AngularMarkdownEditorModule
  ],
  providers: [AnswerService,MarkdownParserService],
  bootstrap: [AppComponent],
  entryComponents: [DashboardComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }