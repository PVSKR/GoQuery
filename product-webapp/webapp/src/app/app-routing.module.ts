import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AboutComponent } from './components/about/about.component';
import { AddquestionComponent } from './components/addquestion/addquestion.component';
import { AllAnswerComponent } from './components/all-answer/all-answer.component';
import { AllQuestionComponent } from './components/all-question/all-question.component';
import { AnswerComponent } from './components/answer/answer.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
//import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { LandingComponent } from './components/landing/landing.component';
import { LoginComponent } from './components/login/login.component';
import { MarkdownComponent } from './components/markdown/markdown.component';

import { QuestionlandingComponent } from './components/questionlanding/questionlanding.component';
import { RegisterComponent } from './components/register/register.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { EntryGuard } from './service/entry.guard';

const routes: Routes = [
  {path:'',component:LandingComponent},
  {path:'login',component:LoginComponent},
  {path:'register',component:RegisterComponent},
  {path:'about',component:AboutComponent},
  //{path:'landing',component:LandingPageComponent}
  //{path:'landing',component:LandingComponent},
  {path:'dashboard', component:DashboardComponent,canActivate:[EntryGuard]},
  {path: 'questioner' , component: UserProfileComponent},
  {path: 'answer/:id',component:AnswerComponent,canActivate:[EntryGuard]},
  {path: 'questionlanding',component:QuestionlandingComponent,canActivate:[EntryGuard]},
  {path: 'addquestion',component:AddquestionComponent,canActivate:[EntryGuard]},
  {path: 'allQuestion', component:AllQuestionComponent,canActivate:[EntryGuard]},
  {path: 'allAnswers/:id', component: AllAnswerComponent,canActivate:[EntryGuard]}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }