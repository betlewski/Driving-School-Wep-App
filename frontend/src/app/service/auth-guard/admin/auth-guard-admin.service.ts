import {Injectable} from '@angular/core';
import {CanActivate, Router} from "@angular/router";
import {AuthService} from "../../auth/auth.service";
import {Observable, of} from "rxjs";
import {catchError, map} from "rxjs/operators";

/**
 * Serwis do uwierzytelniania administratora
 * podczas inicjalizacji każdego komponentu.
 */
@Injectable()
export class AuthGuardAdmin implements CanActivate {

  constructor(private router: Router,
              private authService: AuthService) {
  }

  public canActivate(): Observable<boolean> {
    if (this.authService.isAuthenticated()) {
      return this.authService.canActivateAdmin().pipe(
        map(() => {
          return true;
        }),
        catchError(() => {
          return this.notActivate();
        })
      );
    } else {
      return this.notActivate();
    }
  }

  private notActivate(): Observable<false> {
    this.router.navigate(['/login']).then();
    return of(false);
  }

}
