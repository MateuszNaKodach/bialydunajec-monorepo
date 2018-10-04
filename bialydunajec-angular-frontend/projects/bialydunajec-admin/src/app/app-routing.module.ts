import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {CoreModule} from './core/core.module';
import {AuthModule} from './auth/auth.module';
import {AuthGuard} from './auth/service/auth.guard';
import {NotAuthGuard} from './auth/service/not-auth.guard';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => AuthModule,
    canActivate: [NotAuthGuard]
  },
  {
    path: 'panel',
    loadChildren: () => CoreModule,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
