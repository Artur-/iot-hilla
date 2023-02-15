import type { Route } from '@vaadin/router';
import './views/dashboard/dashboard';
import './views/main-layout';

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
];
export const routes: ViewRoute[] = [
  {
    path: '',
    component: 'main-layout',
    children: views,
  },
];
