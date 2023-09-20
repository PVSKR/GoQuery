import { AfterViewInit, Component, ElementRef } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit{
  constructor(private elementRef : ElementRef){}
  ngAfterViewInit(){
    this.elementRef.nativeElement.ownerDocument.body.style.background =`linear-gradient(90deg, rgb(240, 243, 198) 0%, rgb(216, 221, 148) 100%)`;
  }
  title = 'GoQuery';
}
