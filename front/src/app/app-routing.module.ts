import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from './AuthGuard';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { AdminDashboardComponent } from './pages/admin-dashboard/admin-dashboard.component';
import { StoreComponent } from './pages/store/store.component';

const routes: Routes = [
  { 
    path: '',
    component: HomePageComponent
  },
  { 
    path: 'login', 
    component: LoginComponent
  },
  {
    path: 'AdminDashboard', 
    component: AdminDashboardComponent,
    canActivate: [AuthGuard],
    data: { role: 'admin' },
  },
  {
    path: 'store', 
    component: StoreComponent,
    canActivate: [AuthGuard],
    data: { role: 'client' },
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }