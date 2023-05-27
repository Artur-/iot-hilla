import type { Route } from '@vaadin/router';
import './views/main-layout';
import { appStore } from './stores/app-store';

export type ViewRoute = Route & {
  title?: string;
  icon?: string;
  children?: ViewRoute[];
};

export const views: ViewRoute[] = [
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
        // debugger;
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
