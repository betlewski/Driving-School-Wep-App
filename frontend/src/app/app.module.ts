import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './component/common/login/login.component';
import {RegistrationComponent} from './component/common/registration/registration.component';
import {FormsModule} from "@angular/forms";
import {CryptoJsService} from "./service/crypto-js/crypto-js.service";
import {AuthService} from "./service/auth/auth.service";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    AuthService,
    CryptoJsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
