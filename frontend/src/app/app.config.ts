import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import {
  provideHttpClient,
  withInterceptors,
  withInterceptorsFromDi,
} from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {
  authInterceptor,
  LogLevel,
  provideAuth,
  withAppInitializerAuthCheck,
} from 'angular-auth-oidc-client';

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideAnimationsAsync(),
    provideRouter(routes),
    provideAuth(
      {
        config: {
          authority:
            'https://login.microsoftonline.com/2ab831ab-3873-4eec-903e-159969a8b507/v2.0',
          authWellknownEndpointUrl:
            'https://login.microsoftonline.com/2ab831ab-3873-4eec-903e-159969a8b507/v2.0',
          redirectUrl: window.location.origin,
          clientId: '506294b9-701c-4b3a-935a-0694e538a3bf',
          scope:
            'openid profile offline_access email',
          responseType: 'code',
          silentRenew: true,
          maxIdTokenIatOffsetAllowedInSeconds: 600,
          issValidationOff: true,
          autoUserInfo: false,
          // silentRenewUrl: window.location.origin + '/silent-renew.html',
          useRefreshToken: true,
          logLevel: LogLevel.Debug,
          customParamsAuthRequest: {
            prompt: 'login', // login, consent
          },
        },
      },
      withAppInitializerAuthCheck()
    ),
    provideHttpClient(
      withInterceptorsFromDi(),
      withInterceptors([authInterceptor()])
    ),
  ]
};
