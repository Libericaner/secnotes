import { Component, OnInit } from '@angular/core';
import {NoteDatasource} from '../../datasources/NoteDatasource';

@Component({
  selector: 'app-start',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.css']
})
export class StartComponent implements OnInit {

  constructor(private readonly noteDatasource: NoteDatasource) { }

  ngOnInit() {
  }

}
