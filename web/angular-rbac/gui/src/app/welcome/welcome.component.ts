import { Component, OnInit } from "@angular/core";

import { AuthorizationService } from "../authorization.service";

@Component({
  selector: "app-welcome",
  templateUrl: "./welcome.component.html",
  styleUrls: ["./welcome.component.css"]
})
export class WelcomeComponent implements OnInit {
  constructor(private auth: AuthorizationService) {}

  ngOnInit() {}
}
