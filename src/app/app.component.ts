import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PageEvent } from '@angular/material/paginator';
// import { MatAutocompletePosition } from '@angular/material/autocomplete';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'task';
  // position: MatAutocompletePosition = 'below';

  // Pagination properties
  totalItems = 50; // Replace with the total number of items
  pageSize = 10;
 
  // Data array to hold fetched items
  items: any[] = [];

  constructor(private http: HttpClient) {}

  onPageChange(event: PageEvent) {
    const offset = event.pageIndex * event.pageSize;

    this.http
      .get<any>(
        `http://localhost:8080/getp?page=${event.pageIndex}&size=${event.pageSize}`
      )
      .subscribe(data => {
        this.items = data.content;
      });
  }
}
