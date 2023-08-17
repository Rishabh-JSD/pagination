import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TodoService } from '../service/todo.service';
import { TaskData } from '../task.model';
import { PageEvent } from '@angular/material/paginator';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.css']
})
export class TodoComponent {

  taskModelObj: TaskData = new TaskData;
  allTaskData: any;

  showAdd!: boolean;
  showUpd!: boolean;
  userData: any


 
  constructor(private service: TodoService, 
    private fb: FormBuilder, 
    private router: Router,
    private http: HttpClient
    ) {
  }


  taskform!: FormGroup;
  ngOnInit() {
    this.taskform = this.fb.group({
      task_name: ['', [Validators.required, Validators.pattern('[A-Za-z ]*')]],
      task_description: ['', [Validators.required, Validators.pattern('[A-Za-z ]*')]],
      task_status: ['', [Validators.required]]
    })
    this.getAllData()

  }


  clickAddTask() {
    this.taskform.reset();
    this.showAdd = true;
    this.showUpd = false;
  }
  
  addTask() {
    this.taskModelObj.task_name = this.taskform.value.task_name;
    this.taskModelObj.task_description = this.taskform.value.task_description;
    this.taskModelObj.task_status = this.taskform.value.task_status;

    this.service.addData(this.taskModelObj).subscribe(res => {
      console.log(res);
      alert("task added successfully")
      this.taskform.reset();
      this.getAllData();
    });
  }

   // Add pagination properties
   currentPage = 0;
   pageSize = 4;
   totalItems = 0;
   totalPages = 10;
   pageData: any[] = [];
 
   // BehaviorSubject for triggering data updates
   dataUpdate = new BehaviorSubject<boolean>(false);
 
  getAllData() {
    this.service.getPaginatedTasks(this.currentPage, this.pageSize).subscribe((res: any) => {
      this.allTaskData = res.content; // Update the task data
      this.totalItems = res.totalElements;
      this.totalPages = res.totalPages;
      this.pageData = this.allTaskData;
      this.dataUpdate.next(true); // Notify data update
    });
  }
  

  deleteData(data: any) {
    console.log("kkkkkkkkkkkkkkkkkkkkkkk", data)
    this.service.deleteTask(data.id).subscribe(res => {
      console.log("deleeeeeeeeeee==", res)
      alert("Task Deleted");
      this.getAllData();
    })
    location.reload();
  }

  onEditTask(data: any) {
    this.showAdd = false;
    this.showUpd = true;
  
    this.taskModelObj.id = data.id
    this.taskform.controls['task_name'].setValue(data.task_name);
    this.taskform.controls['task_description'].setValue(data.task_description);
    this.taskform.controls['task_status'].setValue(data.task_status);
  }

  updateTaskData() {
    this.taskModelObj.task_name = this.taskform.value.task_name;
    this.taskModelObj.task_description = this.taskform.value.task_description;
    this.taskModelObj.task_status = this.taskform.value.task_status;

    this.service.updateTask(this.taskModelObj, this.taskModelObj.id).subscribe(res => {
      alert('task updated successfully');
      this.taskform.reset();
      this.getAllData();
    })
  }
  pageSizeOptions: number[] = [4, 8, 16, 32, 64];

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getAllData();
  }
}
