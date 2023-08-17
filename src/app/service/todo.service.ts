import { Injectable } from '@angular/core';
import { HttpClient ,HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class TodoService {
  api_url = 'http://localhost:8080';
  constructor(private http: HttpClient) {}


  getPaginatedTasks(page: number, pageSize: number) {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', pageSize.toString());

    return this.http.get<any>(`${this.api_url}/getp`, { params }).pipe(
      map((res: any) => {
        return res;
      })
    );
  }


  addData(data: any) {
    return this.http.post<any>(`${this.api_url}/add`, data).pipe(
      map((res: any) => {
        return res;
      })
    );
  }

  getTask() {
    return this.http.get<any>(`${this.api_url}/get`).pipe(
      map((res: any) => {
        return res;
      })
    );
  }
  updateTask(data: any, id: any) {
    return this.http.put<any>(`${this.api_url}/update/${id}`, data).pipe(
      map((res: any) => {
        return res;
      })
    );
  }
  deleteTask(id: any) {
    console.log('iiiii=====>', id);
    return this.http.delete<any>(`${this.api_url}/delete/${id}`).pipe(
      map((res: any) => {
        return res;
      })
    );
  }


 // Pagination properties
 totalItems = 100; // Replace with the total number of items
 pageSize = 10;
 pageSizeOptions: number[] = [5, 10, 25, 100];

 // Data array to hold fetched items
 items: any[] = [];


 // Handle page change event
 onPageChange(event: any) {
  
   const offset = event.pageIndex * event.pageSize;

   // Make an API call to fetch data for the selected page
   this.http.get<any>(`http://localhost:8080/getp`).subscribe(data => {
    // console.log("hey bddy light weight");
     this.items = data;
   });
 }

}
