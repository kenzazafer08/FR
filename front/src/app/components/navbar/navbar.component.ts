import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store, createFeatureSelector } from '@ngrx/store';
import { AuthState } from 'src/app/NGRX/auth.reducer';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
    isLoggedIn = false;
    authState$ = this.store.select(createFeatureSelector<AuthState>('auth'));
  
    constructor(
      private store: Store,
      private router: Router,
      private cdr: ChangeDetectorRef,
      private authService : AuthService
    ) {}
  
    ngOnInit(): void {
      this.authState$.subscribe(() => {
        this.cdr.detectChanges();
      });
    }
  
    login() {
      this.router.navigate(['/login']);
    }
  
    logout() {
      this.authService.logout();
    }
}
