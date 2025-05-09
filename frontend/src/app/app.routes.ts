import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { HomeComponent } from './pages/home/home.component';
import { SettingsComponent } from './pages/home/settings/settings.component';
import { LogComponent } from './pages/home/log/log.component';
import { StatementsComponent } from './pages/home/statements/statements.component';
import { AdvicesComponent } from './pages/home/advices/advices.component';
import { RecordComponent } from './pages/home/record/record.component';
import { DashboardComponent } from './pages/home/dashboard/dashboard.component';
import { authGuard } from './shared/guards/auth.guard';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { 
        path: 'home', 
        component: HomeComponent, 
        canActivate: [authGuard],
        children: [
            // { path: 'dashboard', component: DashboardComponent },
            { path: 'record', component: RecordComponent },
            { path: 'advices', component: AdvicesComponent },
            { path: 'statements', component: StatementsComponent },
            { path: 'log', component: LogComponent },
            { path: 'settings', component: SettingsComponent },
            { path: '', redirectTo: 'advices', pathMatch: 'full' },
            { path: '**', redirectTo: 'advices', pathMatch: 'full' },
        ],
    },
    { path: '', component: WelcomeComponent },
    { path: '**', redirectTo: ''},
];
