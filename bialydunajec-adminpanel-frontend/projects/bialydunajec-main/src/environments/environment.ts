// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  restApi: {
    baseUrl: 'http://localhost:3344',
    clientId: 'web-angular',
    clientSecret: 'fUMU6AngbLIkfTOAqfifaShzOEMeUQ/cxpij3iwMM30Czj..7ipdLxv6'
  },
  google: {
    reCaptcha: {
      siteKey: '6LeDQXEUAAAAAKZZBSr_C197Mlz3nr1RR_yIU48l'
    },
    maps: {
      apiKey: 'AIzaSyD9KEl1K8zlFSYbb0_sRciA213IDhUkco8'
    }
  }
};

/*
 * In development mode, for easier debugging, you can ignore zone related error
 * stack frames such as `zone.run`/`zoneDelegate.invokeTask` by importing the
 * below file. Don't forget to comment it out in production mode
 * because it will have a performance impact when errors are thrown
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
