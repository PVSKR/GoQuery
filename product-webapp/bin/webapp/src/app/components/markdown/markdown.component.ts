import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-markdown',
  templateUrl: './markdown.component.html',
  styleUrls: ['./markdown.component.css']
})
export class MarkdownComponent implements OnInit {

  @Input() compiled: string;
  @Input() placeHolder: string;

  @Output() valueChanged = new EventEmitter<string>();

  ngOnInit(): void {
  }

  onValueChange(e) {
    const body = e.target.value;

    if (!body) {
      // reset to initial state
      return this.valueChanged.emit(this.placeHolder);
    } else {
      this.valueChanged.emit(body);
    }
  }
}
