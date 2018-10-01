import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {CoreModule} from './core/core.module';
import {AuthModule} from './auth/auth.module';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => AuthModule
  },
  {
    path: 'panel',
    loadChildren: () => CoreModule
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
