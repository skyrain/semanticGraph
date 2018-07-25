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
  url = 'http://localhost:8080/graphExplore';
  results = {};
  mockResults = [
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:Wikipedia_categories_named_after_military_leaders",
       "broader1HomePage":null,
       "broader1Label":"\"Wikipedia categories named after military leaders\"@en",
       "broader2":"http://dbpedia.org/resource/Category:Military_leaders",
       "broader2HomePage":null,
       "broader2Label":"\"Military leaders\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:Wikipedia_categories_named_after_military_leaders",
       "broader1HomePage":null,
       "broader1Label":"\"Wikipedia categories named after military leaders\"@en",
       "broader2":"http://dbpedia.org/resource/Category:Wikipedia_categories_named_after_people_by_occupation",
       "broader2HomePage":null,
       "broader2Label":"\"Wikipedia categories named after people by occupation\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:Wikipedia_categories_named_after_heads_of_government",
       "broader1HomePage":null,
       "broader1Label":"\"Wikipedia categories named after heads of government\"@en",
       "broader2":"http://dbpedia.org/resource/Category:Heads_of_government",
       "broader2HomePage":null,
       "broader2Label":"\"Heads of government\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:Wikipedia_categories_named_after_heads_of_government",
       "broader1HomePage":null,
       "broader1Label":"\"Wikipedia categories named after heads of government\"@en",
       "broader2":"http://dbpedia.org/resource/Category:Wikipedia_categories_named_after_politicians",
       "broader2HomePage":null,
       "broader2Label":"\"Wikipedia categories named after politicians\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:20th-century_philosophers",
       "broader1HomePage":null,
       "broader1Label":"\"20th-century philosophers\"@en",
       "broader2":"http://dbpedia.org/resource/Category:20th-century_philosophy",
       "broader2HomePage":null,
       "broader2Label":"\"20th-century philosophy\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:20th-century_philosophers",
       "broader1HomePage":null,
       "broader1Label":"\"20th-century philosophers\"@en",
       "broader2":"http://dbpedia.org/resource/Category:Contemporary_philosophers",
       "broader2HomePage":null,
       "broader2Label":"\"Contemporary philosophers\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:20th-century_philosophers",
       "broader1HomePage":null,
       "broader1Label":"\"20th-century philosophers\"@en",
       "broader2":"http://dbpedia.org/resource/Category:20th-century_scholars",
       "broader2HomePage":null,
       "broader2Label":"\"20th-century scholars\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:Wikipedia_categories_named_after_Chinese_people",
       "broader1HomePage":null,
       "broader1Label":"\"Wikipedia categories named after Chinese people\"@en",
       "broader2":"http://dbpedia.org/resource/Category:Chinese_people",
       "broader2HomePage":null,
       "broader2Label":"\"Chinese people\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:Wikipedia_categories_named_after_Chinese_people",
       "broader1HomePage":null,
       "broader1Label":"\"Wikipedia categories named after Chinese people\"@en",
       "broader2":"http://dbpedia.org/resource/Category:Wikipedia_categories_named_after_people_by_nationality",
       "broader2HomePage":null,
       "broader2Label":"\"Wikipedia categories named after people by nationality\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:20th-century_Chinese_poets",
       "broader1HomePage":null,
       "broader1Label":"\"20th-century Chinese poets\"@en",
       "broader2":"http://dbpedia.org/resource/Category:20th-century_poets",
       "broader2HomePage":null,
       "broader2Label":"\"20th-century poets\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:20th-century_Chinese_poets",
       "broader1HomePage":null,
       "broader1Label":"\"20th-century Chinese poets\"@en",
       "broader2":"http://dbpedia.org/resource/Category:20th-century_Chinese_writers",
       "broader2HomePage":null,
       "broader2Label":"\"20th-century Chinese writers\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:20th-century_Chinese_poets",
       "broader1HomePage":null,
       "broader1Label":"\"20th-century Chinese poets\"@en",
       "broader2":"http://dbpedia.org/resource/Category:Chinese_poets_by_century",
       "broader2HomePage":null,
       "broader2Label":"\"Chinese poets by century\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:20th-century_Chinese_writers",
       "broader1HomePage":null,
       "broader1Label":"\"20th-century Chinese writers\"@en",
       "broader2":"http://dbpedia.org/resource/Category:20th-century_writers",
       "broader2HomePage":null,
       "broader2Label":"\"20th-century writers\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:20th-century_Chinese_writers",
       "broader1HomePage":null,
       "broader1Label":"\"20th-century Chinese writers\"@en",
       "broader2":"http://dbpedia.org/resource/Category:Chinese_writers_by_century",
       "broader2HomePage":null,
       "broader2Label":"\"Chinese writers by century\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:20th-century_Chinese_writers",
       "broader1HomePage":null,
       "broader1Label":"\"20th-century Chinese writers\"@en",
       "broader2":"http://dbpedia.org/resource/Category:20th-century_Chinese_people_by_occupation",
       "broader2HomePage":null,
       "broader2Label":"\"20th-century Chinese people by occupation\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:20th-century_Chinese_writers",
       "broader1HomePage":null,
       "broader1Label":"\"20th-century Chinese writers\"@en",
       "broader2":"http://dbpedia.org/resource/Category:20th-century_Chinese_literature",
       "broader2HomePage":null,
       "broader2Label":"\"20th-century Chinese literature\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:Chinese_revolutionaries",
       "broader1HomePage":null,
       "broader1Label":"\"Chinese revolutionaries\"@en",
       "broader2":"http://dbpedia.org/resource/Category:Chinese_political_people",
       "broader2HomePage":null,
       "broader2Label":"\"Chinese political people\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:Chinese_revolutionaries",
       "broader1HomePage":null,
       "broader1Label":"\"Chinese revolutionaries\"@en",
       "broader2":"http://dbpedia.org/resource/Category:Revolutionaries_by_nationality",
       "broader2HomePage":null,
       "broader2Label":"\"Revolutionaries by nationality\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:Chinese_revolutionaries",
       "broader1HomePage":null,
       "broader1Label":"\"Chinese revolutionaries\"@en",
       "broader2":"http://dbpedia.org/resource/Category:Chinese_people_by_political_orientation",
       "broader2HomePage":null,
       "broader2Label":"\"Chinese people by political orientation\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:Anti-Revisionists",
       "broader1HomePage":null,
       "broader1Label":"\"Anti-Revisionists\"@en",
       "broader2":"http://dbpedia.org/resource/Category:Anti-Revisionism",
       "broader2HomePage":null,
       "broader2Label":"\"Anti-Revisionism\"@en]"
    },
    {
       "homePage":"http://english.cpc.people.com.cn/66095/4468893.html",
       "label":"\"Mao Zedong\"@en",
       "broader1":"http://dbpedia.org/resource/Category:Anti-Revisionists",
       "broader1HomePage":null,
       "broader1Label":"\"Anti-Revisionists\"@en",
       "broader2":"http://dbpedia.org/resource/Category:Marxists",
       "broader2HomePage":null,
       "broader2Label":"\"Marxists\"@en]"
    }
 ];

  constructor(private http: HttpClient) {}

  onSearchClicked() {
    console.log("function:onSearchClicked")
    // this.search(this.searchText).subscribe(
    //   results => {
    //     //this.results = results;
    //     console.log("Result: " + this.results);
    //     this.searchFinished = true;
    //   });
    this.results = this.mockResults;
    this.searchFinished = true;
  }

  search(text:string): Observable<Object> {
    console.log("Searching " + this.searchText + "...");
    return this.http
      .post<Object>(this.url, { "name":text });
  }
}
