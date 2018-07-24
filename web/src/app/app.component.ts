import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from '../../node_modules/rxjs/Observable';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Semantic Graph Explorer';
  searchText = '';
  searchFinished = false;
  url = 'http://localhost:4200';
  result = {};

  constructor(private http: HttpClient) {}

  onSearchClicked() {
    console.log("function:onSearchClicked")
    this.search(this.searchText).subscribe(
      results => {
        this.result = results;
        console.log("Result: " + results);
        this.searchFinished = true;
      });
  }

  search(text:string): Observable<Object> {
    console.log("Searching " + this.searchText + "...");
    return this.http
      .get<Object>(this.url);
  }
}
