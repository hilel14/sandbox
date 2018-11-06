import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, of } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";

import { Credentials } from "./credentials";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" })
};

const baseUrl = "/angular-rbac-api/webresources/generic/";

@Injectable({
  providedIn: "root"
})
export class AuthorizationService {
  username: string;
  role: string;

  constructor(private router: Router, private http: HttpClient) {}

  authenticate(username: string, password: string) {
    this.getCredentials(username, password).subscribe((data: any) =>
      this.storeCredentials(data)
    );
  }

  getCredentials(username: string, password: string): Observable<any> {
    let url = baseUrl + "authenticate";
    let credentials = new Credentials();
    credentials.username = username;
    credentials.password = password;
    return (
      this.http
        .put(url, credentials, httpOptions)
        .pipe(catchError(this.handleError("authenticate", [])))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = "operation", result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  }

  storeCredentials(credentials: Credentials) {
    // store user details and jwt token in local storage to keep user logged in between page refreshes
    console.log("credentials from api: " + credentials);
    this.username = credentials.username;
    this.role = credentials.role;
    localStorage.setItem("credentials", JSON.stringify(credentials));
    //this.router.navigate(["/login"]);
  }

  hasPermission(role: string): boolean {
    if (!this.username) {
      console.log("username not set");
      if (localStorage.getItem("credentials")) {
        console.log("loading credentials from local storage");
        let credentials: Credentials = JSON.parse(
          localStorage.getItem("credentials")
        );
        console.log(credentials);
        this.username = credentials.username;
        this.role = credentials.role;
      }
    }
    return this.role == role;
  }

  logout() {
    // remove credentials from local storage to log user out
    this.username = undefined;
    this.role = undefined;
    localStorage.removeItem("credentials");
  }

  getCurrentState(): string {
    return (
      this.username +
      " " +
      this.role +
      " " +
      localStorage.getItem("credentials")
    );
  }
}
