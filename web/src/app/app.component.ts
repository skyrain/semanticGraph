import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Semantic Graph Explorer';
  searchText = '';
  basicExploreFinished = false;
  basicExploreApi = 'http://localhost:8080/basicExplore';
  broaderExploreApi = 'http://localhost:8080/broaderExplore';
  narrowerExploreApi = 'http://localhost:8080/narrowerExplore';
  basicInfo = {};
  broaderInfo = {};
  narrowerInfo = {};

  constructor(private http: HttpClient) {}

  onSearchClicked() {
    console.log("function:onSearchClicked")
    this.basicExplore(this.searchText).subscribe(
      results => {
        this.basicInfo = results[0];
        console.log("Result: " + results);
        this.basicExploreFinished = true;
      });
    this.broaderExplore(this.searchText).subscribe(
      results => {
        this.broaderInfo = results;
        console.log("Result: " + results);
    });
    this.narrowerExplore(this.searchText).subscribe(
      results => {
        this.narrowerInfo = results;
        console.log("Result: " + results);
    });
  }

  onViewMoreClicked(text:string) {
    this.basicExploreFinished = false;
    console.log("function:onViewMoreClicked");
    this.basicExplore(text).subscribe(
      results => {
        this.basicInfo = results[0];
        console.log("Result: " + results);
        this.basicExploreFinished = true;
      });
    this.broaderExplore(text).subscribe(
      results => {
        this.broaderInfo = results;
        console.log("Result: " + results);
    });
    this.narrowerExplore(text).subscribe(
      results => {
        this.narrowerInfo = results;
        console.log("Result: " + results);
    });
  }

  basicExplore(text:string): Observable<Object> {
    console.log("Basic exploring " + text + "...");
    return this.http
      .post<Object>(this.basicExploreApi, {"name":text});
  }

  broaderExplore(text:string): Observable<Object> {
    console.log("Broader exploring " + text + "...");
    return this.http
      .post<Object>(this.broaderExploreApi, {"name":text});
  }

  narrowerExplore(text:string): Observable<Object> {
    console.log("Narrower exploring " + text + "...");
    return this.http
      .post<Object>(this.narrowerExploreApi, {"name":text});
  }
}
