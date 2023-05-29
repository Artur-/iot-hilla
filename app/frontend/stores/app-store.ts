import { RouterLocation } from '@vaadin/router';
import { makeAutoObservable } from 'mobx';

interface FederationRoute {
  title?: string;
  path: string;
  icon: string;
  tag: string;
  importPath: string;
}
export class AppStore {
  applicationName = 'In Da House';

  // The location, relative to the base path, e.g. "hello" when viewing "/hello"
  location = '';

  currentViewTitle = '';

  federationRoutes: FederationRoute[] = [];
  constructor() {
    makeAutoObservable(this);
  }

  setLocation(location: RouterLocation) {
    if (location.route) {
      this.location = location.route.path;
    } else if (location.pathname.startsWith(location.baseUrl)) {
      this.location = location.pathname.substr(location.baseUrl.length);
    } else {
      this.location = location.pathname;
    }
    this.currentViewTitle = this.federationRoutes.find((route) => route.path === location.pathname)?.title || '';
    
  }
}

export const appStore = new AppStore();
