import { Router } from '@vaadin/router';
import { routes } from './routes';
import { appStore } from './stores/app-store';
import { DeploymentInfoEndpoint } from './generated/endpoints';
import Type from './generated/org/vaadin/artur/hillamicro/shared/MessagingConfiguration/Type';
import { Notification } from '@vaadin/notification';

export const router = new Router(document.querySelector('#outlet'));

router.setRoutes(routes);

window.addEventListener('vaadin-router-location-changed', (e) => {
  appStore.setLocation((e as CustomEvent).detail.location);
  const title = appStore.currentViewTitle;
  if (title) {
    document.title = title + ' | ' + appStore.applicationName;
  } else {
    document.title = appStore.applicationName;
  }
});

DeploymentInfoEndpoint.getDeploymentUpdates().onNext((e) => {
  if (e.type == Type.DEPLOY) {
    appStore.federationRoutes = [
      ...appStore.federationRoutes,
      {
        tag: e.applicationInfo.tag,
        icon: e.applicationInfo.icon,
        title: e.applicationInfo.title,
        importPath: e.applicationInfo.importPath,
        path: e.applicationInfo.path,
      },
    ];
  } else if (e.type == Type.UNDEPLOY) {
    appStore.federationRoutes = appStore.federationRoutes.filter((route) => route.tag != e.applicationInfo.tag);
  }
  Notification.show(`App ${e.applicationInfo.title} was ${e.type === Type.DEPLOY ? 'deployed' : 'undeployed'}`);
});
