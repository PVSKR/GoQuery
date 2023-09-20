import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  
  constructor(private http: HttpClient) { 
    
  }
  search(query){
    console.log("query in service:", query);
    return this.http.get(`http://localhost:8090/api/v1/nlp/filter/${query}?type=NN`);
  }

  // getQuestions(){
  //   return this.http.get("http://localhost:8085/api/v1/questions/getquestions");
  // }
}
