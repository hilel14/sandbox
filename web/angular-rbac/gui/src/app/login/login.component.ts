import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";

import { AuthorizationService } from "../authorization.service";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"]
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;

  constructor(private auth: AuthorizationService, private router: Router) {}

  ngOnInit() {}

  login(): void {
    console.log("login " + this.username + " " + this.password);
    this.auth.authenticate(this.username, this.password);
  }

  logout(): void {
    this.auth.logout();
  }
}
