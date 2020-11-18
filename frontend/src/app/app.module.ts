import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './component/common/login/login.component';
import { RegistrationComponent } from './component/common/registration/registration.component';
import {FormsModule} from "@angular/forms";
import {CryptoJsService} from "./service/crypto-js.service";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule
    ],
  providers: [CryptoJsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
