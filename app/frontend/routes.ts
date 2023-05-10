import type { Route } from '@vaadin/router';
import './views/dashboard/dashboard';
import './views/main-layout';
import { appStore } from './stores/app-store';

export type ViewRoute = Route & {
  title?: string;
  icon?: string;
  children?: ViewRoute[];
};

export const views: ViewRoute[] = [
  // place routes below (more info https://hilla.dev/docs/routing)
  {
    path: '',
    component: 'iot-dashboard',
    icon: 'la la-file',
    title: 'Dashboard',
    action: async (_context, _command) => {
      await import('./views/dashboard/dashboard.js');
      return;
    },
  },
  {
    path: '(.*)',
    action: async (_context, _command) => {
      const pathname = _context.pathname;
      const federationRoute = appStore.federationRoutes.find((route) => route.path === pathname);
      if (federationRoute) {
        const module = await import(federationRoute.importPath);
        await module.init((window as any)?.__federation_shared__?.default || {});
        module.get('.' + pathname);
        return _command.component(federationRoute.tag);
      } else {
        debugger;
      }
      return undefined;
    },
  },
];
export const routes: ViewRoute[] = [
  {
    path: '',
    component: 'main-layout',
    children: views,
  },
];
