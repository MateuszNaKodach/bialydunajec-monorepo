import {NgModule} from '@angular/core';
import {Routes, RouterModule, PreloadAllModules} from '@angular/router';
import {CoreModule} from './core/core.module';
import {AuthModule} from './auth/auth.module';
import {AuthGuard} from './auth/service/auth.guard';
import {NotAuthGuard} from './auth/service/not-auth.guard';

const routes: Routes = [
  {
    path: '',
    loadChildren: './auth/auth.module#AuthModule',
    canActivate: [NotAuthGuard]
  },
  {
    path: 'panel',
    loadChildren: './core/core.module#CoreModule',
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
